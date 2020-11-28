package kz.zhanbolat.testing.impl;

import kz.zhanbolat.testing.TemplateParser;
import kz.zhanbolat.testing.TemplateValueValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse the template to find the values.
 */
public class TemplateParserImpl implements TemplateParser {
    private static final String EMPTY_STRING = "";
    private static final Pattern pattern = Pattern.compile("#\\{(\\w+\\.?)+}");
    private TemplateValueValidator templateValueValidator;

    public TemplateParserImpl(TemplateValueValidator templateValueValidator) {
        this.templateValueValidator = templateValueValidator;
    }

    /**
     *  Parse the template to find the values .Values format: #{subject}, #{subject.subject},
     *  #{subjectSubject}, #{subject_subject}. If word does not match the format, it will be skipped.
     *  If value has spaces, tabs, or/and new lines, then it will be treated as not matched.
     * @param template - the template
     * @return list of values with string to be trimmed, if none of values was found, return empty list
     * @throws IllegalArgumentException if template is null or empty string
     */
    @Override
    public List<String> parse(String template) {
        if (Objects.isNull(template)) {
            throw new IllegalArgumentException("Null value cannot be passed.");
        }
        if (Objects.equals(template.trim(), EMPTY_STRING)) {
            throw new IllegalArgumentException("Empty string cannot be passed.");
        }
        List<String> parsedValues = new ArrayList<>();
        Matcher matcher = pattern.matcher(template);
        while(matcher.find()) {
            String matchedStr = matcher.group();
            String parsedValue = matchedStr.substring(2, matchedStr.length() - 1).trim();
            if (templateValueValidator.isValid(parsedValue)) {
                parsedValues.add(parsedValue);
            }
        }
        return parsedValues;
    }
}
