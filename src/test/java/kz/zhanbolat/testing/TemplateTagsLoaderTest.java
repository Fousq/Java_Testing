package kz.zhanbolat.testing;

import kz.zhanbolat.testing.impl.TemplateTagsLoaderImpl;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TemplateTagsLoaderTest {
    private TemplateTagsLoader templateTagsLoader;

    @Test
    public void shouldThrowIllegalArgumentException_whenPassNotExistsFile() {
        templateTagsLoader = new TemplateTagsLoaderImpl("./notexists");
        assertThrows(IllegalArgumentException.class, () -> templateTagsLoader.loadTagValueMap());
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenPassDirectory() throws URISyntaxException {
        File directory = new File(getClass().getClassLoader().getResource("loadingTests").toURI());
        templateTagsLoader = new TemplateTagsLoaderImpl(directory.toPath());
        assertThrows(IllegalArgumentException.class, () -> templateTagsLoader.loadTagValueMap());
    }

    @Test
    public void shouldThrowIllegalStateException_whenPassNotValidFile() throws URISyntaxException {
        final File file = new File(getClass().getClassLoader().getResource("loadingTests/testFail.txt").toURI());
        templateTagsLoader = new TemplateTagsLoaderImpl(file.toPath());
        assertThrows(IllegalStateException.class, () -> templateTagsLoader.loadTagValueMap());
    }

    @Test
    public void shouldReturnMap_whenPassValidFile() throws URISyntaxException {
        final File file = new File(getClass().getClassLoader().getResource("loadingTests/testPass.txt").toURI());
        templateTagsLoader = new TemplateTagsLoaderImpl(file.toPath());
        final Map<String, String> tagValueMap = templateTagsLoader.loadTagValueMap();

        assertTrue(tagValueMap.containsKey("value"));
        assertTrue(tagValueMap.containsValue("tag"));
    }
}
