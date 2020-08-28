package com.qa.tests;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jUtils {

    public static Logger getLogger(Class<? extends Object> clazz){
        PropertyConfigurator.configure("res/log4j.properties");
        Logger logger = Logger.getLogger(clazz);
        return logger;
    }

}
