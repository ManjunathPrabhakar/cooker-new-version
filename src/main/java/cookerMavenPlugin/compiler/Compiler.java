package cookerMavenPlugin.compiler;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import cookerMavenPlugin.featureFactory.*;
import io.cucumber.messages.IdGenerator;
import io.cucumber.messages.Messages;
import io.cucumber.messages.Messages.GherkinDocument;
import io.cucumber.messages.Messages.GherkinDocument.Feature;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Background;
import io.cucumber.messages.Messages.GherkinDocument.Feature.FeatureChild;
import io.cucumber.messages.Messages.GherkinDocument.Feature.FeatureChild.Rule;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario.Examples;

import java.util.List;

/**
 * Compiler Class
 */

public class Compiler {

    // ---- FEATURE DATA ----------
    private FeatureUtils featureUtils = null;
    private String globalFeatureTags = null;
    private String globalFeatureHeaderData = null;

    // ----- BACKGROUND DATA -------
    private BackgroundUtils backgroundUtils = null;
    private Background featureBackground = null;
    private String featureBackgroundData = null;

    // ----- SCENARIO DATA -------
    //private ScenarioUtils scenarioUtils = null;

    private final IdGenerator idGenerator;

    private String userTags = new String();
    // private List<String> toCreateFiles = new ArrayList<>();
    private Multimap<String, String> multimapToCreateFiles = ArrayListMultimap.create();

    private CookerTagExpressionParser cookerTagExpressionParser;

    public Compiler(IdGenerator idGenerator, String userTags) {
        this.idGenerator = idGenerator;
        this.userTags = userTags;
        this.cookerTagExpressionParser = new CookerTagExpressionParser();
    }

    /**
     * Main method to compile feature file to get details as perUserTag
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     *
     * @param gherkinDocument gherkinDocument
     * @return List of Files to create
     */
    public Multimap<String, String> compile(GherkinDocument gherkinDocument) {
        //Get Feature from Ghrekin Document
        Feature feature = gherkinDocument.getFeature();

        //If feature is null then return
        if (feature == null) {
            return multimapToCreateFiles;
        }

        //Create object of featureUtils from the feature
        featureUtils = new FeatureUtils(feature);

        //If Feature Level Tags COntains the UserTags, then add the feature file data to the List of files to generate

        if (cookerTagExpressionParser.tagParser(userTags,featureUtils.getFeatureTagsList())) {
            String featureData = featureUtils.getFeatureData();
            multimapToCreateFiles.put(featureUtils.getFeatureName(), featureData);
            return multimapToCreateFiles;
        }
        //Else Compile FeatureFile and parse
        else {
            compileFeature(feature);
        }

        //Return the List of Files to Generate
        return multimapToCreateFiles;
    }

    /**
     * This Method is used to compile the feature file
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     *
     * @param feature Object of Feature
     */
    private void compileFeature(Feature feature) {
        //List to hold the children under the feature
        List<FeatureChild> featureChildList = feature.getChildrenList();

        //Create an Object of FeatureUtils
        featureUtils = new FeatureUtils(feature);
        //get the List of Feature Level Tags from the feature
        List<String> featureTags = featureUtils.getFeatureTagsList();

        //Store the All Feature Level Tags in a String
        for (String tagName : featureTags) {
            globalFeatureTags =
                    (globalFeatureTags == null ? "" : globalFeatureTags) + tagName;
        }
        //Store Feature Header Data Only -> Feature Keyword, Name, Description, Language
        globalFeatureHeaderData = featureUtils.getFeatureHeaderWithoutTags();

        //Loop through Each child in the Feature
        for (FeatureChild featureChild : featureChildList) {

            //If Child has Background
            if (featureChild.hasBackground()) {
                //Get the Background Object from the Feature Child
                featureBackground = featureChild.getBackground();
                //Compile the Background Object
                compileBackground(featureBackground);
            }
            //Else if Child has Rule
            else if (featureChild.hasRule()) {
                //Get the Rule Object from the Feature Child
                Rule featureRule = featureChild.getRule();
                //Compile the Rule Object
                compileRule(featureRule);
            }
            //Else if Child has Scenario
            else if (featureChild.hasScenario()) {
                //Get the Scenario Object from the Feature Child
                Scenario featureScenario = featureChild.getScenario();
                //Compile the Scenario Object
                compileScenario(featureScenario);
            }
        }
    }

    /**
     * This Method is used to Compile the Background Object from the Feature and get the Background Data into a String
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     *
     * @param featureBackground Background Object from the Feature
     */
    private void compileBackground(Background featureBackground) {
        //Create Object of background Utils
        backgroundUtils = new BackgroundUtils(featureBackground);
        //Get Background Data and Store it in the String
        featureBackgroundData = backgroundUtils.getBackgroundData();
    }

    /**
     * This Method is used to Compile the Rule Object from the Feature
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     *
     * @param featureRule Rule Object from Feature
     */
    private void compileRule(Rule featureRule) {
        //Create an Object of Rule cookerMavenPlugin.compiler.Compiler with -> UserTags, FeatureTags, FeatureHeaderData, FeatureBackgroundData, Rule Object
        RuleCompiler ruleCompiler = new RuleCompiler(userTags, featureUtils.getFeatureName(), globalFeatureTags,
                globalFeatureHeaderData, featureBackgroundData, featureRule);
        //Get the List of Files to be Created from he object of Rule
        Multimap<String, String> ruleDataa = (ruleCompiler.compileRule());

        multimapToCreateFiles.putAll(ruleDataa);
    }

    /**
     * This Method is used to Compile Scenario Object from the Feature File
     * If the Scenario/Sceanrio Outline Level Tags has the same Tags specified by user then load the whole scenario/Sceanrio Outline to create a file
     * Else Get the List of Examples of from the Sceanrio Outline,
     * for each Examples of the Sceanrio Outline call compileScenarioWithExample with Scenario Object & Examples Object
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     *
     * @param featureScenario Scenario Object from Feature
     */
    private void compileScenario(Scenario featureScenario) {
        //Create and Object of Scenario Utils
        ScenarioUtils scenarioUtils = new ScenarioUtils(featureScenario);
        //Create an Object of StringBuilder to Store the Scenario Details
        StringBuilder sceanrioToFile = new StringBuilder();

        //Check if Scenario Level Tags has the User Tags
        if (cookerTagExpressionParser.tagParser(userTags,scenarioUtils.getScenarioTagsList())) {

            //Append FeatureTags & Feature Header to StringBuilder Object
            sceanrioToFile.append(globalFeatureTags == null ? "" : globalFeatureTags);
            sceanrioToFile.append(System.getProperty("line.separator"));
            sceanrioToFile.append(globalFeatureHeaderData);
            sceanrioToFile.append(System.getProperty("line.separator"));

            //Get the ScenarioData from scenarioutils Object
            String scenarioData = scenarioUtils.getScenarioData();

            //Check the feature background
            if (featureBackground == null) {
                //If Feature doesn't have background then append scenario data
                sceanrioToFile.append(scenarioData);
            }
            //Else
            else {
                //If feature has background then append Feature Background Data & ScenarioData
                sceanrioToFile.append(featureBackgroundData);
                sceanrioToFile.append(scenarioData);
            }

            //Append the scenario data to file to CREATE FILE
            multimapToCreateFiles.put(featureUtils.getFeatureName(), sceanrioToFile.toString());
        }
        //If the Scenario Level tags doesnt have the Tags Specified by User, Then
        //Check if Scenario has Examples to Determine if its a Sceanrio Outline/Scenario Template
        //Coz Examples also might have Tags, If it same as userTags then Sceanrio + that matching example
        else {
            //Create a List of Examples Object to Store the Examples of the Current Scenario
            List<Examples> examplesList = featureScenario.getExamplesList();
            //For Each Examples under the Scenario, Compile the Scenario
            for (Examples examples : examplesList) {
                compileScenarioWithExample(featureScenario, examples);
            }
        }

    }

    /**
     * This Method is used to compile the Scenario Object that has Examples a.k.a Scenario Outline
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     *
     * @param featureScenarioWithSelectedExample Scenario Object that has Examples
     * @param scenarioExamples                   Examples of the Scenario
     */
    private void compileScenarioWithExample(Scenario featureScenarioWithSelectedExample, Examples scenarioExamples) {
        //Create an StringBuilder object to store the ScenarioOutline Details
        StringBuilder scenarioWithExampleToFile = new StringBuilder();
        //Create an StringBuilder object to store the Scenario Data
        StringBuilder scenarioData = new StringBuilder();

        //Flag to determine if the user tag is found in Examples Level Tags, Current FALSE
        boolean found = false;

        //Create an Object of ExampleUtils
        ExampleUtils exampleUtils = new ExampleUtils(scenarioExamples);

        //Check if Examples Level Tags has the Tags Specified by the User
        if (cookerTagExpressionParser.tagParser(userTags,exampleUtils.getExamplesTagsList())) {

            //Markk the Flag to TRUE
            found = true;

            //Variables Declaration to Get Scenario Header Data
            String scenarioKeyword = featureScenarioWithSelectedExample.getKeyword();
            String scenarioName = featureScenarioWithSelectedExample.getName();
            String scenarioDescription = featureScenarioWithSelectedExample.getDescription();
            List<Messages.GherkinDocument.Feature.Tag> scenarioTagsList = featureScenarioWithSelectedExample.getTagsList();
            List<Messages.GherkinDocument.Feature.Step> scenarioStepsList = featureScenarioWithSelectedExample.getStepsList();


            //Get the Scenario Level Tags
            for (Messages.GherkinDocument.Feature.Tag tag : scenarioTagsList) {
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
            for (Messages.GherkinDocument.Feature.Step s : scenarioStepsList) {

                //Create and Object of StepUtils and get the Step Data and Append to ScenarioData Object
                StepUtils stepUtils = new StepUtils(s);
                String stepData = stepUtils.getStepData();
                scenarioData.append(stepData);

            }
            //Get the Examples Data and Append to SceanrioData Object
            String exampleData = exampleUtils.getExamplesData();
            scenarioData.append(exampleData);
        }

        //If usertags is found in Examples
        if (found) {
            //If feature has no background
            if (featureBackground == null && found) {
                //Append FeatureTags, FeatureHeader and Sceanrio Data
                scenarioWithExampleToFile.append(globalFeatureTags == null ? "" : globalFeatureTags);
                scenarioWithExampleToFile.append(globalFeatureHeaderData);
                scenarioWithExampleToFile.append(scenarioData);
            } else {
                //Append FeatureTags, FeatureHeader , Background and Sceanrio Data
                scenarioWithExampleToFile.append(globalFeatureTags == null ? "" : globalFeatureTags);
                scenarioWithExampleToFile.append(globalFeatureHeaderData);
                scenarioWithExampleToFile.append(featureBackgroundData);
                scenarioWithExampleToFile.append(scenarioData);
            }

            //Add the sceanrioOutline to create a file
            multimapToCreateFiles.put(featureUtils.getFeatureName(), scenarioWithExampleToFile.toString());
        }
    }


}