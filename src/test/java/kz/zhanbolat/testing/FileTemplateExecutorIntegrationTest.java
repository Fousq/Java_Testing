package kz.zhanbolat.testing;

import kz.zhanbolat.testing.impl.FileTemplateExecutor;
import kz.zhanbolat.testing.impl.TemplateGeneratorImpl;
import kz.zhanbolat.testing.impl.TemplateParserImpl;
import kz.zhanbolat.testing.impl.TemplateValueValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnabledIfSystemProperty(named = "mode", matches = "integration")
public class FileTemplateExecutorIntegrationTest extends AbstractTemplateExecutorTest {
    private FileTemplateExecutor executor;
    private TemplateParser templateParser;

    @BeforeEach
    public void init() {
        templateParser = new TemplateParserImpl(new TemplateValueValidatorImpl());
    }

    @Test
    public void shouldWriteGeneratedContent(@TempDir Path tempDir) throws IOException, URISyntaxException {
        Path writeFilePath = tempDir.resolve("test.txt");
        Files.createFile(writeFilePath);
        Map<String, String> valueTagMap = new HashMap<>();
        valueTagMap.put("value", "tag");
        Path readFilePath = Paths.get(getClass().getClassLoader().getResource("templates/templateTest.txt").toURI());

        executor = new FileTemplateExecutor(readFilePath, writeFilePath, new TemplateGeneratorImpl(valueTagMap, templateParser));

        executor.execute();

        assertEquals("Some text: tag", readFromFile(writeFilePath.toFile()));
    }
}
