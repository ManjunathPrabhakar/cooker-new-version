/*
 * Copyright (c) 2020.  Manjunath Prabhakar manjunath189@gmail.com
 */

package executor;

import cookerMavenPlugin.CookerTrigger;
import cookerMavenPlugin.kitchen.Ingredients;

import java.util.HashMap;

/**
 * @author Manjunath-PC
 * @created 11/09/2020
 * @project cooker-new-version
 */
public class TestClassOnly {

    public static void main(String[] args) throws Exception {
        Ingredients.setUserTag("@E2E and @tag1");
        Ingredients.setTrFullTempPath(System.getProperty("user.dir") + "\\src\\test\\resources\\templates\\TestRunnerTemplate.template");
        Ingredients.setfExiFullPath(System.getProperty("user.dir") + "\\src\\test\\features");
        Ingredients.setStepDefPackage("StepDefs");
        Ingredients.setFgFullGenPath(System.getProperty("user.dir") + "\\src\\test\\resources\\generated\\featureFiles\\");
        Ingredients.setTrFullGenPath(System.getProperty("user.dir") + "\\src\\test\\java\\generated\\testRunners\\");
        Ingredients.setCustomPlaceHolders(new HashMap<>());

        CookerTrigger.cookFiles();
    }
}
