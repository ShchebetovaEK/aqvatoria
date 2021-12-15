package by.tms.aqvatoria.reader.impl;

import by.tms.aqvatoria.exception.AqvatoriaThreadException;
import by.tms.aqvatoria.reader.DataReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataReaderImpl implements DataReader {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<String> readLines(String fileName) throws AqvatoriaThreadException {
        if (fileName == null || fileName.isEmpty()) {
            throw new AqvatoriaThreadException("filename null or don't exist");

        }
        List<String> lines = new ArrayList<>();

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URI resource = classLoader.getResource(fileName).toURI();
            Path path = Paths.get(resource);
            Files.lines(path, Charset.defaultCharset()).forEach(lines::add);
        } catch (IOException | URISyntaxException e) {
           logger.error("Fail to read {}",fileName);
            throw new AqvatoriaThreadException("Failed to read file : {}, {}" + fileName, e);
        }
        logger.info("File {},read successfully",fileName);
        return lines;
    }
}
