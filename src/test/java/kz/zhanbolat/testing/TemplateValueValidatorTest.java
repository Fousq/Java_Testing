package kz.zhanbolat.testing;

import kz.zhanbolat.testing.impl.TemplateValueValidatorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TemplateValueValidatorTest {
    private static TemplateValueValidator templateValueValidator;

    @BeforeAll
    public static void init() {
        templateValueValidator = new TemplateValueValidatorImpl();
    }

    @Test
    public void shouldReturnTrue_whenPassValidValue() {
        assertTrue(templateValueValidator.isValid("value"));
        assertTrue(templateValueValidator.isValid("template.value"));
        assertTrue(templateValueValidator.isValid("template_value"));
        assertTrue(templateValueValidator.isValid("templateValue"));
    }

    @Test
    public void shouldReturnFalse_whenPassNotValidValue() {
        assertFalse(templateValueValidator.isValid("value?"));
        assertFalse(templateValueValidator.isValid("value!"));
        assertFalse(templateValueValidator.isValid("value#"));
        assertFalse(templateValueValidator.isValid("value~"));
        assertFalse(templateValueValidator.isValid("value."));
        assertFalse(templateValueValidator.isValid("value,"));
        assertFalse(templateValueValidator.isValid("value@"));

        assertFalse(templateValueValidator.isValid("?value"));
        assertFalse(templateValueValidator.isValid("!value"));
        assertFalse(templateValueValidator.isValid("#value"));
        assertFalse(templateValueValidator.isValid("~value"));
        assertFalse(templateValueValidator.isValid(",value"));
        assertFalse(templateValueValidator.isValid(".value"));
        assertFalse(templateValueValidator.isValid("@value"));

        assertFalse(templateValueValidator.isValid("?value?"));
        assertFalse(templateValueValidator.isValid("!value!"));
        assertFalse(templateValueValidator.isValid("#value#"));
        assertFalse(templateValueValidator.isValid("~value~"));
        assertFalse(templateValueValidator.isValid(",value,"));
        assertFalse(templateValueValidator.isValid(".value."));
        assertFalse(templateValueValidator.isValid("@value@"));

        assertFalse(templateValueValidator.isValid("template.value?"));
        assertFalse(templateValueValidator.isValid("template.value!"));
        assertFalse(templateValueValidator.isValid("template.value#"));
        assertFalse(templateValueValidator.isValid("template.value~"));
        assertFalse(templateValueValidator.isValid("template.value,"));
        assertFalse(templateValueValidator.isValid("template.value."));
        assertFalse(templateValueValidator.isValid("template.value@"));

        assertFalse(templateValueValidator.isValid("?template.value"));
        assertFalse(templateValueValidator.isValid("!template.value"));
        assertFalse(templateValueValidator.isValid("#template.value"));
        assertFalse(templateValueValidator.isValid("~template.value"));
        assertFalse(templateValueValidator.isValid(",template.value"));
        assertFalse(templateValueValidator.isValid(".template.value"));
        assertFalse(templateValueValidator.isValid("@template.value"));

        assertFalse(templateValueValidator.isValid("?template.value?"));
        assertFalse(templateValueValidator.isValid("!template.value!"));
        assertFalse(templateValueValidator.isValid("#template.value#"));
        assertFalse(templateValueValidator.isValid("~template.value~"));
        assertFalse(templateValueValidator.isValid(",template.value,"));
        assertFalse(templateValueValidator.isValid(".template.value."));
        assertFalse(templateValueValidator.isValid("@template.value@"));
    }
}
