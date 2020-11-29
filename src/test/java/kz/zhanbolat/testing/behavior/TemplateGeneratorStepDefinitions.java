package kz.zhanbolat.testing.behavior;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kz.zhanbolat.testing.TemplateGenerator;
import kz.zhanbolat.testing.TemplateParser;
import kz.zhanbolat.testing.impl.TemplateGeneratorImpl;
import kz.zhanbolat.testing.impl.TemplateParserImpl;
import kz.zhanbolat.testing.impl.TemplateValueValidatorImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateGeneratorStepDefinitions {
    private TemplateGenerator templateGenerator;
    private String template;
    private String content;

    @Given("Provide template: {string}")
    public void provideTemplate(String template) {
        TemplateParser templateParser = new TemplateParserImpl(new TemplateValueValidatorImpl());
        Map<String, String> map = new HashMap<>();
        map.put("value", "tag");
        map.put("text", "text");
        map.put("template.value", "tag");
        templateGenerator = new TemplateGeneratorImpl(map, templateParser);
        this.template = template;
    }

    @When("Generating of content is required")
    public void generateContent() {
        content = templateGenerator.generateContent(template);
    }


    @Then("Content must be {string}")
    public void contentMustBe(String content) {
        assertEquals(content, this.content);
    }
}
