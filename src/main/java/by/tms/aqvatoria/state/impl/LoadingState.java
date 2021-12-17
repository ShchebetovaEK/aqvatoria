package by.tms.aqvatoria.state.impl;

import by.tms.aqvatoria.entity.Ship;
import by.tms.aqvatoria.state.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadingState implements ShipState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void next(Ship ship) {
        logger.warn("ship don't have a next state");

    }

    @Override
    public void prev(Ship ship) {
        ship.setShipState(new UnloadingState());

    }

    @Override
    public void printState(Ship ship) {
        logger.info("ship loading");

    }
}
