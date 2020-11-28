package kz.zhanbolat.testing.impl;

import kz.zhanbolat.testing.TemplateGenerator;
import kz.zhanbolat.testing.TemplateParser;

import java.util.List;
import java.util.Map;

public class TemplateGeneratorImpl implements TemplateGenerator {
    private Map<String, String> valueTagMap;
    private TemplateParser templateParser;

    public TemplateGeneratorImpl(Map<String, String> valueTagMap, TemplateParser templateParser) {
        this.valueTagMap = valueTagMap;
        this.templateParser = templateParser;
    }

    @Override
    public String generateContent(String template) {
        List<String> values = templateParser.parse(template);
        for (String value : values) {
            if (!valueTagMap.containsKey(value)) {
                throw new IllegalStateException("Unknown value to find the pair.");
            }
            template = template.replace("#{" + value + "}", valueTagMap.get(value));
        }
        return template;
    }
}
