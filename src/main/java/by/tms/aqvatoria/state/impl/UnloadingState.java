package by.tms.aqvatoria.state.impl;

import by.tms.aqvatoria.entity.Ship;
import by.tms.aqvatoria.state.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnloadingState implements ShipState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void next(Ship ship) {
        ship.setShipState(new LoadingState());

    }

    @Override
    public void prev(Ship ship) {
        ship.setShipState(new ArrivingState());
    }

    @Override
    public void printState(Ship ship) {
        logger.info("ship unloading");

    }
}
