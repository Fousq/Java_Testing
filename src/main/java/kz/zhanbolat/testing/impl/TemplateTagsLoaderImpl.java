package kz.zhanbolat.testing.impl;

import kz.zhanbolat.testing.TemplateTagsLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateTagsLoaderImpl implements TemplateTagsLoader {
    private static final Logger logger = LogManager.getLogger(TemplateTagsLoaderImpl.class);
    private Path filePath;

    public TemplateTagsLoaderImpl(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public TemplateTagsLoaderImpl(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Map<String, String> loadTagValueMap() {
        if (Files.notExists(filePath)) {
            throw new IllegalArgumentException("File doesn't exist.");
        }
        if (Files.isDirectory(filePath)) {
            throw new IllegalArgumentException("The path points to directory, not a file.");
        }
        Map<String, String> map = new HashMap<>();
        try (FileInputStream fileInputStream = new FileInputStream(filePath.toFile());
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            if (lines.stream().anyMatch(line -> line.split("=").length != 2)) {
                throw new IllegalStateException("The content is not valid.");
            }
            lines.forEach(line -> {
                String[] splittedLine = line.split("=");
                map.put(splittedLine[0], splittedLine[1]);
            });
        } catch (IOException e) {
            logger.error("Cannot read from file: " + filePath + ". Cause: " + e);
        }
        return map;
    }
}
