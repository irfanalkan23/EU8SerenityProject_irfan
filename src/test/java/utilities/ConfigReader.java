package utilities;


import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

//this is not the regular way of Configuration Properties!
//here, you don's see "serenity.properties" file "load"ing any properties, as we learned before.
//this class is unique, not something you gonna write.
//this allows you use Serenity properties (sharing serenity.properties with Serenity and your own),
//you can put some custom properties (custom key values, ex. spartan.editor.username), in order to
//read them, you need this custom ConfigReader class
public class ConfigReader {

    private static EnvironmentVariables environmentVariables ;
    static {
        environmentVariables =
                SystemEnvironmentVariables.createEnvironmentVariables();
    }

    public static String getProperty(String propertyName){

        return EnvironmentSpecificConfiguration
                .from(environmentVariables)
                .getProperty(propertyName);

    }

}
