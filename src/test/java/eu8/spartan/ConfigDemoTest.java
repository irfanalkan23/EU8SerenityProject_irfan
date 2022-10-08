package eu8.spartan;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utilities.ConfigReader;

@Disabled   //I don't want to run this when running BookitEnvTest with Maven "verity"
public class ConfigDemoTest {
    @Test
    public void test1(){
        System.out.println(ConfigReader.getProperty("serenity.project.name"));
        System.out.println(ConfigReader.getProperty("spartan.editor.username"));


    }
}
