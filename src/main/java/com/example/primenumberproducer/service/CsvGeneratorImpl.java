package com.example.primenumberproducer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;

@Slf4j
@Service
public class CsvGeneratorImpl implements CsvGenerator {

    private static final String JAVA_TMP_DIR = "java.io.tmpdir";
    private static final String CSV_SUFFIX = ".csv";

    @Override
    public void generate(String numbers) {
        String tempDir = System.getProperty(JAVA_TMP_DIR);
        String csvFileName = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
        File csvFile = new File(tempDir + File.separator + csvFileName + CSV_SUFFIX);
        try (PrintWriter pw = new PrintWriter(csvFile)) {
            pw.println(numbers);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        log.info("Csv file created: {}", csvFile.getAbsolutePath());
    }
}
