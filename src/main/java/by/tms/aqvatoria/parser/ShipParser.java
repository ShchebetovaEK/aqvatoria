package by.tms.aqvatoria.parser;

import by.tms.aqvatoria.entity.Ship;
import by.tms.aqvatoria.exception.AqvatoriaThreadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ShipParser {
    private static final Logger logger = LogManager.getLogger();
    private static String REGEX_SHIP = "\\d+\\s\\d+\\s\\d+";

    public List<Ship> parseLines(List<String> lines) throws AqvatoriaThreadException {
        List<Ship> ships = new ArrayList<>();
        if (lines == null) {
            logger.error("Nothing to parse, list lines null");
            return ships;
        }

        for (String line : lines) {
            if (line.matches(REGEX_SHIP)) {
                String[] data = line.split("\\s");
                int id = Integer.parseInt(data[0]);
                int maxCapacity = Integer.parseInt(data[1]);
                int currentCapacity = Integer.parseInt(data[2]);
                Ship ship = new Ship(id,maxCapacity, currentCapacity);
                ships.add(ship);
            }
        }
        return ships;
    }
}



