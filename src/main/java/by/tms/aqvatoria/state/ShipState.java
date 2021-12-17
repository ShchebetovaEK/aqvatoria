package by.tms.aqvatoria.state;

import by.tms.aqvatoria.entity.Ship;

public interface ShipState {
    void next(Ship ship);

    void prev(Ship ship);

    void printState(Ship ship);
}
