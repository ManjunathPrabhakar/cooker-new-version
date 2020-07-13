package backYard;

import cookerMavenPlugin.fileFactory.FileUtils;
import cookerMavenPlugin.fileGenFactory.FeatureFileGenerator;
import cookerMavenPlugin.fileGenFactory.RandomNumberGenerator;
import cookerMavenPlugin.featureFactory.*;
import io.cucumber.gherkin.GherkinDocumentBuilder;
import io.cucumber.gherkin.Parser;
import io.cucumber.gherkin.TokenMatcher;
import io.cucumber.messages.IdGenerator;
import io.cucumber.messages.Messages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Trigger {

    private static List<String> toCreateFiles = new ArrayList<>();

    private static List<String> userTags = new ArrayList<String>();
    private static IdGenerator idGenerator = new IdGenerator.Incrementing();

    public static void main(String[] args) throws Exception {

        // userTags.add("@lock");
        userTags.add("@someFeature");

//        List<String> paths = singletonList("testdata/backYard.test.feature");
//        boolean includeSource = false;
//        boolean includeAst = true;
//        boolean includePickles = false;
//        List<Object> envelopes = Gherkin.fromPaths(paths, includeSource, includeAst, includePickles, idGenerator).collect(Collectors.toList());
        // Get the AST
        //Messages.GherkinDocument gherkinDocument = ((Messages.Envelope) envelopes.get(0)).getGherkinDocument();
//////////////////////////////////////////////////////////////////////////////////////////

        Messages.GherkinDocument.Feature.Background featureBackground = null;
        String BG = null;

        Parser<Messages.GherkinDocument.Builder> parser = new Parser<>(new GherkinDocumentBuilder(idGenerator));
        TokenMatcher matcher = new TokenMatcher();
        Messages.GherkinDocument gherkinDocument = null;


        //Initilize FileUtils Class with exiting feature files directory
        FileUtils featurecontent = new FileUtils("testdata");
        //Get all *.feature files from existing feature files directory
        List<File> featurefiles = featurecontent.getFiles(".feature");

        if (featurefiles.size() > 0) {

            //For each feature file
            for (File featurefile : featurefiles) {

                System.out.println(featurefile.getName());
                String featureFileContent = FileUtils.readAndGetFileContent(featurefile.getPath());

                //Check if Feature File is Empty
                if (featureFileContent.equals("")) {
                    //Display the feature file is empty and go to next Feature File
                    System.err.println(featurefile.getName() + " is Empty!");
                    continue;
                }

                gherkinDocument = parser.parse(featureFileContent).build();

                // Get the Feature node of the AST
                Messages.GherkinDocument.Feature feature = gherkinDocument.getFeature();

//                cookerMavenPlugin.compiler.Compiler compiler = new cookerMavenPlugin.compiler.Compiler(idGenerator,userTags);
//                List<String> file = compiler.compile(gherkinDocument);
//                for(int i = 0; i < file.size();i++){
//                    System.out.println("FILE " + (i + 1) + "\n" + file.get(i));
//                }
//
//                for (String s : file) {
//                    FeatureFileGenerator.genFeatureFile(s, "testdataGenerated/" + RandomNumberGenerator.genRandomNumber());
//                }

               // System.exit(0);
                FeatureUtils featureUtils = new FeatureUtils(feature);

                //If Feature Level Tags COntains the UserTags, then Print the Feature File and Exit
                if (featureUtils.getFeatureTagsList().containsAll(userTags)) {
                    String featureData = featureUtils.getFeatureData();
                    toCreateFiles.add(featureData);
                }
                //Else, Parse Feature and check its Children
                else {


                    //For each children in the Feature check if it has background, Rule, Scenario
                    for (Messages.GherkinDocument.Feature.FeatureChild featureChild : feature.getChildrenList()) {

                        //Initilize String Builder to Store the sceanrio/scenariooutline/background to create Feature File
                        StringBuilder toFile = new StringBuilder();

                        //If Child has Background
                        if (featureChild.hasBackground()) {
                            featureBackground = featureChild.getBackground();
                            BackgroundUtils backgroundUtils = new BackgroundUtils(featureBackground);
                            String bgData = backgroundUtils.getBackgroundData();
                            BG = bgData;
                        }

                        //If Child has Rule
                        if (featureChild.hasRule()) {
                            Messages.GherkinDocument.Feature.FeatureChild.Rule r = featureChild.getRule();

                            List<String> rulesFile = test.parseRuleAndGetFeatureFilesList(feature, r, userTags);
                            toCreateFiles.addAll(rulesFile);
                            //System.err.println("Currently Not Supporting Rule / Example Keyword");

                        }

                        //If Child has Scenario
                        if (featureChild.hasScenario()) {
                            Messages.GherkinDocument.Feature.Scenario sc = featureChild.getScenario();

                            ScenarioUtils scenarioUtils = new ScenarioUtils(sc);

                            if (scenarioUtils.getScenarioTagsList().containsAll(userTags)) {

                                toFile.append(feature.getKeyword().trim()).append(": ").append(feature.getName());
                                toFile.append(System.getProperty("line.separator"));
                                toFile.append(feature.getDescription());
                                toFile.append(System.getProperty("line.separator"));
                                toFile.append("#Language : ").append(feature.getLanguage());
                                toFile.append(System.getProperty("line.separator"));

                                String scenarioData = scenarioUtils.getScenarioData();

                                if (featureBackground == null) {
                                    toFile.append(scenarioData);
                                } else {
                                    toFile.append(BG);
                                    toFile.append(scenarioData);
                                }
                                toCreateFiles.add(toFile.toString());

                            } else {

                                StringBuilder scenarioData = new StringBuilder();
                                boolean found = false;

                                List<Messages.GherkinDocument.Feature.Scenario.Examples> examplesList = sc.getExamplesList();

                                for (Messages.GherkinDocument.Feature.Scenario.Examples exx : examplesList) {

                                    ExampleUtils exampleUtils = new ExampleUtils(exx);

                                    if (exampleUtils.getExamplesTagsList().containsAll(userTags)) {
                                        found = true;

                                        //Sceanrio Data
                                        String scenarioKeyword = sc.getKeyword();
                                        String scenarioName = sc.getName();
                                        String scenarioDescription = sc.getDescription();
                                        List<Messages.GherkinDocument.Feature.Tag> scenarioTagsList = sc.getTagsList();
                                        List<Messages.GherkinDocument.Feature.Step> scenarioStepsList = sc.getStepsList();

                                        for (Messages.GherkinDocument.Feature.Tag tag : scenarioTagsList) {
                                            TagUtils tagUtils = new TagUtils(tag);
                                            String tagData = tagUtils.getTagData();
                                            scenarioData.append(tagData);
                                        }
                                        scenarioData.append(System.getProperty("line.separator"));
                                        scenarioData.append(scenarioKeyword).append(": ").append(scenarioName);
                                        scenarioData.append(System.getProperty("line.separator"));
                                        scenarioData.append(scenarioDescription == null ? "" : scenarioDescription);
                                        scenarioData.append(System.getProperty("line.separator"));


                                        for (Messages.GherkinDocument.Feature.Step s : scenarioStepsList) {

                                            StepUtils stepUtils = new StepUtils(s);
                                            String stepData = stepUtils.getStepData();
                                            scenarioData.append(stepData);

                                        }


                                        //Exmaples Data
                                        String exampleData = exampleUtils.getExamplesData();
                                        scenarioData.append(exampleData);
                                        //scenarioData.append(exampleData);
                                    }
                                }

                                if (found) {
                                    if (featureBackground == null && found) {
                                        toFile.append(scenarioData);
                                    } else {
                                        toFile.append(feature.getKeyword().trim()).append(": ").append(feature.getName());
                                        toFile.append(System.getProperty("line.separator"));
                                        toFile.append(feature.getDescription());
                                        toFile.append(System.getProperty("line.separator"));
                                        toFile.append("#Language : ").append(feature.getLanguage());
                                        toFile.append(System.getProperty("line.separator"));
                                        toFile.append(BG);
                                        toFile.append(scenarioData);
                                    }


                                    toCreateFiles.add(toFile.toString());
                                }


                            }//END OF SCENARIO ELSE to read examples


                        }//End of If feature children is scenario

                    } //End of For loop for children of feature
                    //////////////////////////

                } //End of Else Part to check feature Children


            } //End of For Each Feature File
        }

        for (String s : toCreateFiles) {
            FeatureFileGenerator.genFeatureFile(s, "testdataGenerated/" + RandomNumberGenerator.getRandomString());
        }


    }


}
