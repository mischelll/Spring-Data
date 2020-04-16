package com.softuni.springdataintroexe.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileReader {
    String[] readFileContent(String filePath) throws IOException;
}
