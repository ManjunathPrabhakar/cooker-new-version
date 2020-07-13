package cookerplugin;


import org.apache.maven.plugin.logging.Log;

public class MojoLogger {
    private static Log logger;

    /**
     * Set the Logger object
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     *
     * @param log Object of Maven Plugin Log
     */
    public static void setLogger(Log log) {
        logger = log;
    }

    /**
     * Return the Logger Object
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     *
     * @return Object of Maven Plugin Log
     */
    public static Log getLogger() {
        return logger;
    }


}