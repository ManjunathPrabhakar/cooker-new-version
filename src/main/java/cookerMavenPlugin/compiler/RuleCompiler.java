/*
 * Copyright (c) 2020.  Manjunath Prabhakar manjunath189@gmail.com
 */

package cookerMavenPlugin.compiler;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import cookerMavenPlugin.featureFactory.*;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Background;
import io.cucumber.messages.Messages.GherkinDocument.Feature.FeatureChild.Rule;
import io.cucumber.messages.Messages.GherkinDocument.Feature.FeatureChild.RuleChild;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario.Examples;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Step;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Tag;

import java.util.List;

/**
 * @author Manjunath Prabhakar
 * @created 26/04/2020
 * @project cooker-new-version
 */
public class RuleCompiler {

    // private List<String> toCreateFiles = new ArrayList<>();
    private final Multimap<String, String> multimapToCreateFiles = ArrayListMultimap.create();

    private String ruleHeaderData = null;

    // ------- BACKGROUND
    private Background ruleBackground = null;
    private String rulebackgroundData = null;

    //----- SCENARIO
    private ScenarioUtils scenarioUtils = null;

    // ------- INITILIZED IN CONSTRUCTOR
    private String featureName = null;
    private String featureUserTags = null;
    private String featureTags = null;
    private String featureHeader = null;
    private String featurebackground = null;
    private Rule ruleFromFeature = null;

    private final CookerTagExpressionParser cookerTagExpressionParser;

    public RuleCompiler(String userTags,
                        String featureName, String globalFeatureTags,
                        String globalFeatureHeaderData,
                        String featureBackground,
                        Rule featureRule) {
        this.featureUserTags = userTags;
        this.featureName = featureName;
        this.featureTags = globalFeatureTags;
        this.featureHeader = globalFeatureHeaderData;
        this.featurebackground = featureBackground;
        this.ruleFromFeature = featureRule;
        this.ruleHeaderData = new RuleUtils(this.ruleFromFeature).getRuleHeader();
        this.cookerTagExpressionParser = new CookerTagExpressionParser();
    }

    /**
     * This Main Method Compile's the Rule in the Feature
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @return List of Files to Be Created
     */
    public Multimap<String, String> compileRule() {
        //Get the Children under the Rule
        List<RuleChild>
                ruleFromFeatureChildrenList = ruleFromFeature.getChildrenList();

        //Loop through each child of Rule
        for (RuleChild ruleChild : ruleFromFeatureChildrenList) {
            //If Rule Child has Background
            if (ruleChild.hasBackground()) {
                //Init Background Object
                ruleBackground = ruleChild.getBackground();
                //Compile the Background in the Rule
                compileRuleBackground(ruleBackground);
            }
            //Else if Rule Child has Scenario
            else if (ruleChild.hasScenario()) {
                //Create an Object of Scenario from the Child
                Scenario ruleScenario =
                        ruleChild.getScenario();
                //Compile the Scenario in the Rule
                compileRuleScenario(ruleScenario);
            }
        }

        //Return the List of Files to be Created
        return multimapToCreateFiles;
    }

    /**
     * This Method Compile the Background found under the Rule
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @param ruleBackground an Object of Background under the Rule
     */
    private void compileRuleBackground(Background ruleBackground) {
        //Create Object of backgroundUtils and get Background Data
        BackgroundUtils backgroundUtils = new BackgroundUtils(ruleBackground);
        rulebackgroundData = backgroundUtils.getBackgroundData();
    }

    /**
     * This Method is used to Compile Scenario Object from the Rule
     * If the Scenario/Sceanrio Outline Level Tags has the same Tags specified by user then load the whole scenario/Sceanrio Outline to create a file
     * Else Get the List of Examples of from the Sceanrio Outline,
     * for each Examples of the Sceanrio Outline call compileScenarioWithExample with Scenario Object & Examples Object
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @param ruleScenario an Object of Scenario under the Rule
     */
    private void compileRuleScenario(Scenario ruleScenario) {
        //Initilize SceanrioUtils
        scenarioUtils = new ScenarioUtils(ruleScenario);
        //StringBuilder Object to Store the Scenario Data
        StringBuilder ruleScenarioToFile = new StringBuilder();

        //Check if Scenario has the Tags Specified by the User

        if (cookerTagExpressionParser.tagParser(featureUserTags, scenarioUtils.getScenarioTagsList())) {

            ruleScenarioToFile.append(featureTags == null ? "" : featureTags);
            ruleScenarioToFile.append(System.getProperty("line.separator"));
            ruleScenarioToFile.append(featureHeader);
            ruleScenarioToFile.append(System.getProperty("line.separator"));

            //get ScenarioData from ScenarioUtils Object
            String scenarioData = scenarioUtils.getScenarioData();

            //Check if the feature has background and pefrom below
            if (featurebackground == null) {

                if (ruleBackground == null) {
                    //If feature & rule doesn't have background  then add featureTags, featureHeader, ruleHeader & scenarioData
                    ruleScenarioToFile.append(ruleHeaderData);
                    ruleScenarioToFile.append(scenarioData);
                } else {
                    //If feature & rule doesn't have background  then add featureTags, featureHeader, ruleHeader, ruleBackfround & scenarioData
                    ruleScenarioToFile.append(ruleHeaderData);
                    ruleScenarioToFile.append(System.getProperty("line.separator"));
                    ruleScenarioToFile.append(rulebackgroundData);
                    ruleScenarioToFile.append(scenarioData);
                }

            } else {

                if (ruleBackground == null) {
                    //If feature has background & rule doesn't have background  then add featureTags, featureHeader, featurebackground, ruleHeader & scenarioData
                    ruleScenarioToFile.append(featurebackground);
                    ruleScenarioToFile.append(ruleHeaderData);
                    ruleScenarioToFile.append(System.getProperty("line.separator"));
                    ruleScenarioToFile.append(scenarioData);
                } else {
                    //If feature & rule have background  then add featureTags, featureHeader, featureBackground, ruleHeader, ruleBackfround & scenarioData
                    ruleScenarioToFile.append(featurebackground);
                    ruleScenarioToFile.append(ruleHeaderData);
                    ruleScenarioToFile.append(System.getProperty("line.separator"));
                    ruleScenarioToFile.append(rulebackgroundData);
                    ruleScenarioToFile.append(scenarioData);
                }
            }
            //Append the scenario data to file to CREATE FILE
            multimapToCreateFiles.put(featureName, ruleScenarioToFile.toString());
        }
        //If the Scenario Level tags doesnt have the Tags Specified by User, Then
        //Check if Scenario has Examples to Determine if its a Sceanrio Outline/Scenario Template
        else {
            //Create a List of Examples Object to Store the Examples of the Current Scenario
            List<Examples> examplesList = ruleScenario.getExamplesList();
            //For Each Examples under the Scenario, Compile the Scenario
            for (Examples examples : examplesList) {
                compileRuleScenarioWithExample(ruleScenario, examples);
            }
        }

    }

    /**
     * This Method is used to compile the Scenario Object that has Examples a.k.a Scenario Outline
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @param ruleScenarioWithSelectedExample Scenario Object that has Examples
     * @param scenarioExamples                Examples of the Scenario
     */
    private void compileRuleScenarioWithExample(Scenario ruleScenarioWithSelectedExample,
                                                Examples scenarioExamples) {
        //Create an StringBuilder object to store the ScenarioOutline Details
        StringBuilder scenarioWithExampleToFile = new StringBuilder();
        //Create an StringBuilder object to store the Scenario Data
        StringBuilder scenarioData = new StringBuilder();

        //Flag to determine if the user tag is found in Examples Level Tags, Current FALSE
        boolean found = false;

        //Create an Object of ExampleUtils
        ExampleUtils exampleUtils = new ExampleUtils(scenarioExamples);

        //Check if Examples Level Tags has the Tags Specified by the User
        if (cookerTagExpressionParser.tagParser(featureUserTags, exampleUtils.getExamplesTagsList())) {

            //Markk the Flag to TRUE
            found = true;

            //Variables Declaration to Get Scenario Header Data
            String scenarioKeyword = ruleScenarioWithSelectedExample.getKeyword();
            String scenarioName = ruleScenarioWithSelectedExample.getName();
            String scenarioDescription = ruleScenarioWithSelectedExample.getDescription();
            List<Tag> scenarioTagsList = ruleScenarioWithSelectedExample.getTagsList();
            List<Step> scenarioStepsList = ruleScenarioWithSelectedExample.getStepsList();

            //Get the Scenario Level Tags
            for (Tag tag : scenarioTagsList) {
                //Create object of TagUtils and Get Tags
                TagUtils tagUtils = new TagUtils(tag);
                String tagData = tagUtils.getTagData();
                //Append the Tags to ScenarioData Object
                scenarioData.append(tagData);
            }
            //Append the Scenario Header Data to ScenarioData Obejct
            scenarioData.append(scenarioKeyword).append(": ").append(scenarioName);
            scenarioData.append(System.getProperty("line.separator"));
            scenarioData.append(scenarioDescription == null ? "" : scenarioDescription);
            scenarioData.append(System.getProperty("line.separator"));

            //For each step in the Scenario append the step data(Step Data including it's DataTable / DocString)
            for (Step s : scenarioStepsList) {

                //Create and Object of StepUtils and get the Step Data and Append to ScenarioData Object
                StepUtils stepUtils = new StepUtils(s);
                String stepData = stepUtils.getStepData();
                scenarioData.append(stepData);

            }


            //Get the Examples Data and Append to SceanrioData Object
            String exampleData = exampleUtils.getExamplesData();
            scenarioData.append(exampleData);
            //scenarioData.append(exampleData);
        }

        //If usertags is found in Examples
        if (found) {

            scenarioWithExampleToFile.append(featureTags == null ? "" : featureTags);
            scenarioWithExampleToFile.append(System.getProperty("line.separator"));
            scenarioWithExampleToFile.append(featureHeader);
            scenarioWithExampleToFile.append(System.getProperty("line.separator"));

            //Check if the feature has background and pefrom below
            if (featurebackground == null) {

                if (ruleBackground == null) {
                    //If feature & rule doesn't have background  then add featureTags, featureHeader, ruleHeader & scenarioData
                    scenarioWithExampleToFile.append(ruleHeaderData);
                    scenarioWithExampleToFile.append(System.getProperty("line.separator"));
                    scenarioWithExampleToFile.append(scenarioData);
                } else {
                    //If feature & rule doesn't have background  then add featureTags, featureHeader, ruleHeader, ruleBackfround & scenarioData
                    scenarioWithExampleToFile.append(ruleHeaderData);
                    scenarioWithExampleToFile.append(System.getProperty("line.separator"));
                    scenarioWithExampleToFile.append(rulebackgroundData);
                    scenarioWithExampleToFile.append(scenarioData);
                }

            } else {

                if (ruleBackground == null) {
                    //If feature has background & rule doesn't have background  then add featureTags, featureHeader, featurebackground, ruleHeader & scenarioData
                    scenarioWithExampleToFile.append(featurebackground);
                    scenarioWithExampleToFile.append(ruleHeaderData);
                    scenarioWithExampleToFile.append(System.getProperty("line.separator"));
                    scenarioWithExampleToFile.append(scenarioData);
                } else {
                    //If feature & rule have background  then add featureTags, featureHeader, featureBackground, ruleHeader, ruleBackfround & scenarioData
                    scenarioWithExampleToFile.append(featurebackground);
                    scenarioWithExampleToFile.append(ruleHeaderData);
                    scenarioWithExampleToFile.append(System.getProperty("line.separator"));
                    scenarioWithExampleToFile.append(rulebackgroundData);
                    scenarioWithExampleToFile.append(scenarioData);
                }
            }
            //Add the sceanrioOutline to create a file
            multimapToCreateFiles.put(featureName, scenarioWithExampleToFile.toString());
        }
    }

}


