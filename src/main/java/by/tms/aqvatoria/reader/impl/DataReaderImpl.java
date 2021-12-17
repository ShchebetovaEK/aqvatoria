package by.tms.aqvatoria.reader.impl;

import by.tms.aqvatoria.exception.AqvatoriaThreadException;
import by.tms.aqvatoria.reader.DataReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
        List<String> lines = new ArrayList<>();
        URL fileUrl = DataReaderImpl.class
                .getClassLoader()
                .getResource(fileName);
        try {
            if (fileUrl == null) {
                logger.error("fileUrl is null");
            }
            File file = new File(fileUrl.getFile());
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while (reader.ready()) {
                lines.add(reader.readLine());
            }
            logger.info("File {},read successfully", fileName);
        } catch (IOException e) {
            logger.error("IOException impossible read file : {}", e.getMessage());
        } catch (NullPointerException e) {
            logger.error("NullPointerException impossible read file: {}", e.getMessage());
        }

        return lines;
    }
}