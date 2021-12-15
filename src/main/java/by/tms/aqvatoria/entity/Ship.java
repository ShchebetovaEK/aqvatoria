package by.tms.aqvatoria.entity;


import by.tms.aqvatoria.exception.AqvatoriaThreadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class Ship implements Runnable {

    private static final Logger logger = LogManager.getLogger();
    private UUID id;
    private Berth berth;
    private Portus portus;
    private int maxCapacity;
    private int currentCapacity;

    public Ship(int maxCapacity, int currentCapacity) {
        this.id = UUID.randomUUID();
        portus = Portus.getInstance();
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
    }

    public UUID getId() {
        return id;
    }

    public Berth getBerth() {
        return berth;
    }

    public Portus getPortus() {
        return portus;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
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

    private void arriveAtBerth() throws AqvatoriaThreadException {
        berth = portus.getFreeBerth();
        logger.info("ship {}, berth id {}", id, berth.getBerthId());
    }

    private void leaveBerth() {
        portus.leaveBerth(berth);
        berth = null;

    }

    private void loadContainers() throws AqvatoriaThreadException {
        if (berth != null) {
            berth.loadContainers(this);
        }
    }

    private void unloadContainers() throws AqvatoriaThreadException {
        if (berth != null) {
            berth.unloadContainers(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (maxCapacity != ship.maxCapacity) return false;
        if (currentCapacity != ship.currentCapacity) return false;
        if (!id.equals(ship.id)) return false;
        if (!berth.equals(ship.berth)) return false;
        return portus.equals(ship.portus);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + berth.hashCode();
        result = 31 * result + portus.hashCode();
        result = 31 * result + maxCapacity;
        result = 31 * result + currentCapacity;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ship{");
        sb.append("id=").append(id);
        sb.append(", berth=").append(berth);
        sb.append(", portus=").append(portus);
        sb.append(", maxCapacity=").append(maxCapacity);
        sb.append(", currentCapacity=").append(currentCapacity);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void run() {
        try {
            arriveAtBerth();
            unloadContainers();
            loadContainers();
            leaveBerth();
        } catch (AqvatoriaThreadException e) {
            logger.error("Failed to service the ship");
        }
    }
}

