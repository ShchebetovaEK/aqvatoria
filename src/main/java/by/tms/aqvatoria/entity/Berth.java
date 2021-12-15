package by.tms.aqvatoria.entity;

import by.tms.aqvatoria.exception.AqvatoriaThreadException;

import java.util.UUID;

public class Berth {
    private UUID berthId;

    public Berth(UUID berthId) {
        this.berthId = berthId;
    }

    public UUID getBerthId() {
        return berthId;
    }

    public void loadContainers(Ship ship) throws AqvatoriaThreadException {
        ContainerStorage storage = ContainerStorage.getInstance();
        storage.loadContainers(ship);
    }

    public void unloadContainers(Ship ship) throws AqvatoriaThreadException{
        ContainerStorage storage = ContainerStorage.getInstance();
        storage.unloadContainers(ship);
    }
}
