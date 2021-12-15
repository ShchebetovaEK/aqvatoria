package by.tms.aqvatoria.reader;

import by.tms.aqvatoria.exception.AqvatoriaThreadException;

import java.util.List;

public interface DataReader {
    List<String> readLines(String fileName) throws AqvatoriaThreadException;
}
