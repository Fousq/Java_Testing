package kz.zhanbolat.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

abstract class AbstractTemplateExecutorTest {

    protected String readFromFile(File file) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(file))   {
            int c;
            while((c = fileInputStream.read()) != -1) {
                builder.append((char) c);
            }
        }
        return builder.toString();
    }
}
