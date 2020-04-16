package com.softuni.springdataintroexe.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class FileReaderImpl implements FileReader {

    @Override
    public String[] readFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader bufferedReader =
                new BufferedReader(new java.io.FileReader(file));

        String line;
        Set<String> result = new LinkedHashSet<String>();

        while ((line = bufferedReader.readLine()) != null) {
            if (!"".equals(line)) {
                result.add(line);
            }
        }
        return result.toArray(String[]::new);
    }
}
