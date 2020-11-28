package kz.zhanbolat.testing.impl;

import kz.zhanbolat.testing.TemplateValueValidator;

import java.util.regex.Pattern;

/**
 *
 */
public class TemplateValueValidatorImpl implements TemplateValueValidator {
    private static final Pattern pattern = Pattern.compile("(\\?|!|~|#|,|^(\\.)|(\\.)$|@)");

    /**
     * Validate the value from template. The value is trimmed on validation.
     * Value is treated as valid if it matches the formats: #{subject}, #{subject.subject},
     * #{subjectSubject}, #{subject_subject}.
     * @param value - the value from template
     * @return true if value is valid, false if not
     */
    @Override
    public boolean isValid(String value) {
        return !pattern.matcher(value).find();
    }
}
