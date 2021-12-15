package by.tms.aqvatoria.parser;

import by.tms.aqvatoria.entity.Ship;
import by.tms.aqvatoria.exception.AqvatoriaThreadException;

import java.util.ArrayList;
import java.util.List;

public class ShipParser {
    private static String splitter = " ";

    public List<Ship> parseLines(List<String> lines) throws AqvatoriaThreadException {
        if (lines == null || lines.isEmpty()) {
            throw new AqvatoriaThreadException("Nothing to parse, list lines isEmpty");
        }
        List<Ship> ships = new ArrayList<>();
        for (String line : lines) {
            String[] data = line.split(splitter);
            int maxCapacity = Integer.parseInt(data[0]);
            int currentCapacity = Integer.parseInt(data[1]);
            Ship ship = new Ship(maxCapacity, currentCapacity);
            ships.add(ship);
        }
        return ships;
    }
}


