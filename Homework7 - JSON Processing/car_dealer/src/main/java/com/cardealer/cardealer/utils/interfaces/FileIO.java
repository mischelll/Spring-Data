package com.cardealer.cardealer.utils.interfaces;

import java.io.IOException;

public interface FileIO {
    String readFile(String filePath) throws IOException;

    void write(String content, String filePath) throws IOException;

}