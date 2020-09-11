/*
 * Copyright (c) 2020.  Manjunath Prabhakar manjunath189@gmail.com
 */

package cookerMavenPlugin.loggers;


import org.apache.maven.plugin.logging.Log;

public class MojoLogger {
    private static Log logger;

    /**
     * Return the Logger Object
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @return Object of Maven Plugin Log
     */
    public static Log getLogger() {
        return logger;
    }

    /**
     * Set the Logger object
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @param log Object of Maven Plugin Log
     */
    public static void setLogger(Log log) {
        logger = log;
    }


}