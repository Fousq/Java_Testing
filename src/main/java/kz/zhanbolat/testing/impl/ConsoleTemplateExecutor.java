package kz.zhanbolat.testing.impl;

import kz.zhanbolat.testing.TemplateExecutor;
import kz.zhanbolat.testing.TemplateGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleTemplateExecutor implements TemplateExecutor {
    private static final Logger logger = LogManager.getLogger(ConsoleTemplateExecutor.class);
    private TemplateGenerator templateGenerator;

    public ConsoleTemplateExecutor(TemplateGenerator templateGenerator) {
        this.templateGenerator = templateGenerator;
    }

    @Override
    public void execute() {
        String template = "";
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            template = bufferedReader.readLine();
        } catch (IOException e) {
            logger.error("Error on reading from console. Cause: " + e);
        }
        System.out.println(templateGenerator.generateContent(template));
    }
}
