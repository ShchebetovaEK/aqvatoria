package by.tms.aqvatoria.entity;

import by.tms.aqvatoria.exception.AqvatoriaThreadException;
import by.tms.aqvatoria.state.ShipState;
import by.tms.aqvatoria.state.impl.ArrivingState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ship implements Runnable {

    private static final Logger logger = LogManager.getLogger();
    private int id;
    private int maxCapacity;
    private int currentCapacity;
    private ShipState shipState = new ArrivingState();

    public Ship(int id, int maxCapacity, int currentCapacity) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
    }

    public int getId() {
        return id;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setShipState(ShipState shipState) {
        this.shipState = shipState;
    }

    public boolean ableToAdd() {
        return currentCapacity < maxCapacity;
    }

    public boolean ableToDelete() {
        return currentCapacity > 0;
    }

    public boolean addContainers() {
        boolean isAdded = true;
        if (currentCapacity < maxCapacity) {
            currentCapacity++;
        } else {
            isAdded = false;
        }
        return isAdded;
    }

    public boolean deleteContainers() {
        boolean isDeleted = true;
        if (currentCapacity > 0) {
            currentCapacity--;
        } else {
            isDeleted = false;
        }
        return isDeleted;
    }

    public void nextState() {
        shipState.next(this);
    }

    public void prevState() {
        shipState.prev(this);
    }

    public void printState() {
        shipState.printState(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (id != ship.id) return false;
        if (maxCapacity != ship.maxCapacity) return false;
        return currentCapacity == ship.currentCapacity;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + maxCapacity;
        result = 31 * result + currentCapacity;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ship{");
        sb.append("id=").append(id);
        sb.append(", maxCapacity=").append(maxCapacity);
        sb.append(", currentCapacity=").append(currentCapacity);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void run() {
        try {
            Berth berth = Portus.getInstance().getFreeBerth();
            if (berth != null) {
                nextState();
                berth.unloadContainers(this);
                nextState();
                berth.loadContainers(this);
            }
            Portus.getInstance().leaveBerth(berth);
            berth = null;
        } catch (AqvatoriaThreadException e) {
            logger.error("Failed to service the ship");
        }
    }
}

