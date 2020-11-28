package kz.zhanbolat.testing;

import kz.zhanbolat.testing.impl.FileTemplateExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@EnabledIfSystemProperty(named = "env", matches = "slow-tests-run")
public class FileTemplateExecutorTest extends AbstractTemplateExecutorTest {
    private FileTemplateExecutor fileTemplateExecutor;
    private TemplateGenerator templateGenerator;

    @BeforeEach
    public void init() {
        templateGenerator = mock(TemplateGenerator.class);
    }

    @Test
    public void shouldWriteContentCorrectly(@TempDir Path tempDir) throws URISyntaxException, IOException {
        Path file = tempDir.resolve("test.txt");
        Files.createFile(file);
        Path readFilePath = Paths.get(getClass().getClassLoader().getResource("templates/templateTest.txt").toURI());

        when(templateGenerator.generateContent(anyString())).thenReturn("Some text: tag");

        fileTemplateExecutor = new FileTemplateExecutor(readFilePath, file, templateGenerator);

        fileTemplateExecutor.execute();

        assertEquals("Some text: tag", readFromFile(file.toFile()));
    }

    @Test
    public void shouldWriteContent_whenEmptyTemplateProvided(@TempDir Path tempDir) throws URISyntaxException, IOException {
        Path file = tempDir.resolve("test.txt");
        Files.createFile(file);
        Path readFilePath = Paths.get(getClass().getClassLoader().getResource("templates/emptyTemplateTest.txt").toURI());

        when(templateGenerator.generateContent(anyString())).thenReturn("Some text: text");

        fileTemplateExecutor = new FileTemplateExecutor(readFilePath, file, templateGenerator);

        fileTemplateExecutor.execute();

        assertEquals("Some text: text", readFromFile(file.toFile()));
    }


}
