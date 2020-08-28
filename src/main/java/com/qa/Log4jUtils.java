package com.qa;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jUtils {

    public static Logger getLogger(Class<? extends Object> clazz){
        PropertyConfigurator.configure("C:/Users/weicao/Desktop/git_project/-/res/log4j.properties");
        Logger logger = Logger.getLogger(clazz);
        return logger;
    }

}
