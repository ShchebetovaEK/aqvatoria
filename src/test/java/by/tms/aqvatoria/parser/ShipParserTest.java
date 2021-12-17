package by.tms.aqvatoria.parser;

import by.tms.aqvatoria.entity.Ship;
import by.tms.aqvatoria.exception.AqvatoriaThreadException;
import by.tms.aqvatoria.reader.impl.DataReaderImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipParserTest {

    @Test
    void parseLines() throws AqvatoriaThreadException {
        Ship ship1 = new Ship(0,10,8);
        Ship ship2 = new Ship(1,11,12);

        DataReaderImpl dataReader = new DataReaderImpl();
        ShipParser shipParser = new ShipParser();

        List<String> ships =  dataReader.readLines("data/ship.txt");
        List<Ship> shipList = shipParser.parseLines(ships);
        assertEquals(ship1,shipList.get(0));
        assertEquals(ship2,shipList.get(1));

    }
}