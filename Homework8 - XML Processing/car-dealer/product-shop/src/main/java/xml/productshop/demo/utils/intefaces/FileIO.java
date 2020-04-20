package xml.productshop.demo.utils.intefaces;

import java.io.IOException;

public interface FileIO {
    String readFile(String filePath) throws IOException;

    void write(String content, String filePath) throws IOException;

}
