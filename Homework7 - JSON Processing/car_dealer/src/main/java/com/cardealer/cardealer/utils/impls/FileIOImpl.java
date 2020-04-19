package com.cardealer.cardealer.utils.impls;

import com.cardealer.cardealer.utils.interfaces.FileIO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class FileIOImpl implements FileIO {
    @Override
    public String readFile(String filePath) throws IOException {

        return Files.readAllLines(Paths.get(filePath))
                .stream()
                .filter(e -> !e.isEmpty())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public void write(String content, String filePath) throws IOException {
        Files.write(Paths.get(filePath), Collections.singleton(content), StandardCharsets.UTF_8);
    }
}
