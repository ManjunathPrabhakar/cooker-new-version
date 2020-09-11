/*
 * Copyright (c) 2020.  Manjunath Prabhakar manjunath189@gmail.com
 */

package cookerMavenPlugin;

import cookerMavenPlugin.annotations.initCucumber;

/**
 * @author Manjunath Prabhakar
 * @created 15/08/2020
 * @project custom-annotations
 */
public class Perfrom {

    static boolean perfomedHuh = false;

    public static void now() throws Exception {
        if (!perfomedHuh) {
            String className = new Exception().getStackTrace()[1].getClassName();
            Class<?> c = Class.forName(className);
            initCucumber annotation = c.getAnnotation(initCucumber.class);

            String propertiesFile = annotation.propertiesPath();
            boolean enabled = annotation.enabled();
            if (enabled) {
                CookerTrigger.cookWithoutMaven(propertiesFile);
            }
            perfomedHuh = true;
        }

    }

//    public static void main(String[] args) {
//        Map<String, Object> stringObjectMap = FileUtils.readYmlFile(new File("data.yml"));
//
//        String s = System.getProperty("user.dir") + (String) stringObjectMap.get("templatePath");
//        System.out.println(s);
//    }
}