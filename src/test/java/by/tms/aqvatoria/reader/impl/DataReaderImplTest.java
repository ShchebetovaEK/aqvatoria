package by.tms.aqvatoria.reader.impl;

import by.tms.aqvatoria.exception.AqvatoriaThreadException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataReaderImplTest {

    private static final String text = """
            0 10 8
            1 11 12
            """;

    @Test
    void readLines() throws AqvatoriaThreadException {
        DataReaderImpl dataReader = new DataReaderImpl();
        List<String> lines = dataReader.readLines("hhh");
        assertTrue(lines.isEmpty());

        lines = dataReader.readLines("data/ship.txt");
        StringBuilder builder = new StringBuilder();
        for (String lire : lines){
            builder.append(lire);
            builder.append("\n");
        }

        assertEquals(text, builder.toString());

    }
}