package projectzero.utils;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

public class LogUtils {

    /**
     * @param classForLogging - class which will logged
     * @return Logger for current class
     */
    public static Logger getLogger(Class classForLogging) {
        return Logger.getLogger(classForLogging);
    }

    public static void configLogger(String pathToProperties) {
        PropertyConfigurator.configure(pathToProperties);
    }
}
