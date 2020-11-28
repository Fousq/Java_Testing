package kz.zhanbolat.testing.impl;

import kz.zhanbolat.testing.TemplateExecutor;
import kz.zhanbolat.testing.TemplateGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileTemplateExecutor implements TemplateExecutor {
    private static final Logger logger = LogManager.getLogger(FileTemplateExecutor.class);
    private Path readFilePath;
    private Path writeFilePath;
    private TemplateGenerator templateGenerator;

    public FileTemplateExecutor(Path readFilePath, Path writeFilePath, TemplateGenerator templateGenerator) {
        this.readFilePath = readFilePath;
        this.writeFilePath = writeFilePath;
        this.templateGenerator = templateGenerator;
    }

    @Override
    public void execute() {
        if (Files.notExists(readFilePath)) {
            throw new IllegalStateException("Read file doesn't exist. Paths: " + readFilePath);
        }
        if (Files.notExists(writeFilePath)) {
            throw new IllegalStateException("Write file doesn't exist. Paths: " + writeFilePath);
        }
        StringBuilder builder = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(readFilePath.toFile())) {
            int c;
            while((c = fileInputStream.read()) != -1) {
                builder.append((char) c);
            }
        } catch (IOException e) {
            logger.error("Cannot read from file: " + readFilePath + ". Cause: " + e);
        }
        String content = templateGenerator.generateContent(builder.toString());
        try (FileOutputStream outputStream = new FileOutputStream(writeFilePath.toFile())) {
            outputStream.write(content.getBytes());
        } catch (IOException e) {
            logger.error("Cannot write to file: " + writeFilePath + ". Cause: " + e);
        }
    }
}
