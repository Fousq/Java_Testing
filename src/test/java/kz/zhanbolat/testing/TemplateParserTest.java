package kz.zhanbolat.testing;

import kz.zhanbolat.testing.impl.TemplateParserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemplateParserTest {
    private TemplateParser templateParser;
    private TemplateValueValidator templateValueValidator;
    private List<String> templates;
    private List<List<String>> results;

    @BeforeEach
    public void initParameters() {
        templates = new ArrayList<>();
        results = new ArrayList<>();
        templateValueValidator = mock(TemplateValueValidator.class);
        templateParser = new TemplateParserImpl(templateValueValidator);
    }

    @Test
    public void shouldReturnValuesList_whenPassTemplate() {
        templates.add("Some text: #{value}");
        templates.add("Some #{text}: #{value}");

        when(templateValueValidator.isValid(anyString())).thenReturn(Boolean.TRUE);

        results = templates.stream().map(template -> templateParser.parse(template)).collect(Collectors.toList());

        results.forEach(result -> assertFalse(result.isEmpty()));
    }

    @Test
    public void shouldThrowException_whenPassEmptyOrNullTemplate() {
        assertThrows(IllegalArgumentException.class, () -> templateParser.parse(""));
        assertThrows(IllegalArgumentException.class, () -> templateParser.parse("           "));
        assertThrows(IllegalArgumentException.class, () -> templateParser.parse("\t\t"));
        assertThrows(IllegalArgumentException.class, () -> templateParser.parse("\n\n"));
        assertThrows(IllegalArgumentException.class, () -> templateParser.parse("\t\n"));
        assertThrows(IllegalArgumentException.class, () -> templateParser.parse(null));
    }

    @Test
    public void shouldReturnEmptyList_whenPassNoValuesTemplate() {
        List<String> values = templateParser.parse("Some text: value");

        assertTrue(values.isEmpty());
    }

    @Test
    public void shouldReturnEmptyList_whenPassNotValidTemplate() {
        templates.add("Some text: #{value");
        templates.add("Some text: value}");
        results = templates.stream().map(template -> templateParser.parse(template)).collect(Collectors.toList());

        results.forEach(result -> assertTrue(result.isEmpty()));
    }

    @Test
    public void shouldReturnEmptyList_whenPassSpaceDividedValuesTemplate() {
        templates.add("Some text: #{template value}");
        templates.add("Some text: #{template\tvalue}");
        templates.add("Some text: #{template\nvalue}");

        when(templateValueValidator.isValid(anyString())).thenReturn(Boolean.FALSE);

        results = templates.stream().map(template -> templateParser.parse(template)).collect(Collectors.toList());

        results.forEach(result -> assertTrue(result.isEmpty()));
    }

    @Test
    public void shouldReturnValuesList_whenPassTwoWordValuesTemplate() {
        templates.add("Some text: #{template_value}");
        templates.add("Some text: #{template.value}");
        templates.add("Some text: #{templateValue}");

        when(templateValueValidator.isValid(anyString())).thenReturn(Boolean.TRUE);

        results = templates.stream().map(template -> templateParser.parse(template)).collect(Collectors.toList());

        results.forEach(result -> assertFalse(result.isEmpty()));
    }

    @Test
    public void shouldReturnValuesList_whenPassTemplateWithSpecialCharactersAtTheEnd() {
        String expectedValue = "value";
        templates.add("Some text: #{value},");
        templates.add("Some text: #{value}.");
        templates.add("Some text: #{value};");
        templates.add("Some #{value}: text");
        templates.add("Some text: #{value}?");
        templates.add("Some text: #{value}!");

        when(templateValueValidator.isValid(anyString())).thenReturn(Boolean.TRUE);

        results = templates.stream().map(template -> templateParser.parse(template)).collect(Collectors.toList());

        results.forEach(result -> assertEquals(expectedValue, result.get(0)));
    }

    @Test
    public void shouldReturnEmptyList_whenPassNotValidValueWithSpecialCharacters() {
        templates.add("Some text: #{template,value}");
        templates.add("Some text: #{template!value}");
        templates.add("Some text: #{template?value}");
        templates.add("Some text: #{template#value}");
        templates.add("Some text: #{,value}");
        templates.add("Some text: #{!value}");
        templates.add("Some text: #{?value}");
        templates.add("Some text: #{#value}");
        templates.add("Some text: #{value,}");
        templates.add("Some text: #{value!}");
        templates.add("Some text: #{value?}");
        templates.add("Some text: #{value#}");

        when(templateValueValidator.isValid(anyString())).thenReturn(Boolean.FALSE);

        results = templates.stream().map(template -> templateParser.parse(template)).collect(Collectors.toList());

        results.forEach(result -> assertTrue(result.isEmpty()));
    }

    @Test
    public void shouldReturnEmptyList_whenPassTemplateWithNotTrimmedValue() {
        templates.add("Some text: #{ value }");
        templates.add("Some text: #{ value}");
        templates.add("Some text: #{value }");

        when(templateValueValidator.isValid(anyString())).thenReturn(Boolean.FALSE);

        results = templates.stream().map(template -> templateParser.parse(template)).collect(Collectors.toList());

        results.forEach(result -> assertTrue(result.isEmpty()));
    }

    @Test
    public void shouldReturnEmptyList_whenPassTemplateWithNotTrimmedValueAndContainsSpecialCharacters() {
        templates.add("Some text: #{ !.value#? }");
        templates.add("Some text: #{ #!value}");
        templates.add("Some text: #{value.! }");

        when(templateValueValidator.isValid(anyString())).thenReturn(Boolean.FALSE);

        results = templates.stream().map(template -> templateParser.parse(template)).collect(Collectors.toList());

        results.forEach(result -> assertTrue(result.isEmpty()));
    }
}
