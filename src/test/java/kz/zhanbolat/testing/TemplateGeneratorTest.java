package kz.zhanbolat.testing;

import kz.zhanbolat.testing.impl.TemplateGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemplateGeneratorTest {
    private TemplateGenerator templateGenerator;
    private TemplateParser templateParser;
    private Map<String, String> map;

    @BeforeEach
    public void init() {
        templateParser = mock(TemplateParser.class);
        map = new HashMap<>();
    }

    @Test
    public void shouldThrowIllegalStateException_whenValueIsUnknown() {
        templateGenerator = new TemplateGeneratorImpl(map, templateParser);

        when(templateParser.parse(anyString())).thenReturn(Collections.singletonList("value"));

        assertThrows(IllegalStateException.class, () -> templateGenerator.generateContent("Some text: #{value}"));
    }

    @Test
    public void shouldReturnContent() {
        map.put("value", "tag");
        templateGenerator = new TemplateGeneratorImpl(map, templateParser);

        when(templateParser.parse(anyString())).thenReturn(Collections.singletonList("value"));

        assertEquals("Some text: tag", templateGenerator.generateContent("Some text: #{value}"));
    }
}
