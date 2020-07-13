package backYard;

import cookerMavenPlugin.featureFactory.*;
import io.cucumber.messages.Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manjunath-PC
 * @created 25/04/2020
 * @project cooker-new-version
 */
public class test {

    public static void main(String[] args) {
        List<String> ls = new ArrayList<>();

        ls.add("one");
        ls.add("two");
        ls.add("three");

        List<String> toCheck = new ArrayList<>();
        toCheck.add("one");
        toCheck.add("four");

        if(ls.containsAll(toCheck)){
            System.out.println("CONTAINS ALL");
        }

      if(ls.retainAll(toCheck)){

      }
    }

    public static List<String> parseRuleAndGetFeatureFilesList
            (Messages.GherkinDocument.Feature feature,
             Messages.GherkinDocument.Feature.FeatureChild.Rule ruleFromFeature,
             List<String> userTags) {
        List<String> ruleFeatureFiles = new ArrayList<>();

        Messages.GherkinDocument.Feature.Background ruleBackground = null;
        String ruleBGString = null;

        List<Messages.GherkinDocument.Feature.FeatureChild.RuleChild> ruleChildList = ruleFromFeature.getChildrenList();

        for (Messages.GherkinDocument.Feature.FeatureChild.RuleChild rr : ruleChildList) {

            //Initilize String Builder to Store the sceanrio/scenariooutline/background to create Feature File
            StringBuilder ruleToFile = new StringBuilder();


            if (rr.hasBackground()) {
                ruleBackground = rr.getBackground();
                BackgroundUtils backgroundUtils = new BackgroundUtils(ruleBackground);
                String bgData = backgroundUtils.getBackgroundData();
                ruleBGString = bgData;

            }
            if (rr.hasScenario()) {
                Messages.GherkinDocument.Feature.Scenario ruleScenario = rr.getScenario();

                ScenarioUtils scenarioUtils = new ScenarioUtils(ruleScenario);

                if (scenarioUtils.getScenarioTagsList().containsAll(userTags)) {

                    ruleToFile.append(feature.getKeyword().trim()).append(": ").append(feature.getName());
                    ruleToFile.append(System.getProperty("line.separator"));
                    ruleToFile.append(feature.getDescription());
                    ruleToFile.append(System.getProperty("line.separator"));
                    ruleToFile.append("#Language : ").append(feature.getLanguage());
                    ruleToFile.append(System.getProperty("line.separator"));

                    ruleToFile.append(ruleFromFeature.getKeyword().trim()).append(": ").append(ruleFromFeature.getName());
                    ruleToFile.append(System.getProperty("line.separator"));
                    ruleToFile.append(ruleFromFeature.getDescription());
                    ruleToFile.append(System.getProperty("line.separator"));
                    ruleToFile.append(System.getProperty("line.separator"));

                    String scenarioData = scenarioUtils.getScenarioData();

                    if (ruleBackground == null) {
                        ruleToFile.append(scenarioData);
                    } else {
                        ruleToFile.append(ruleBGString);
                        ruleToFile.append(scenarioData);
                    }
                    ruleFeatureFiles.add(ruleToFile.toString());

                } else {

                    StringBuilder scenarioData = new StringBuilder();
                    boolean found = false;

                    List<Messages.GherkinDocument.Feature.Scenario.Examples> examplesList = ruleScenario.getExamplesList();

                    for (Messages.GherkinDocument.Feature.Scenario.Examples exx : examplesList) {

                        ExampleUtils exampleUtils = new ExampleUtils(exx);

                        if (exampleUtils.getExamplesTagsList().containsAll(userTags)) {
                            found = true;

                            //Sceanrio Data
                            String scenarioKeyword = ruleScenario.getKeyword();
                            String scenarioName = ruleScenario.getName();
                            String scenarioDescription = ruleScenario.getDescription();
                            List<Messages.GherkinDocument.Feature.Tag> scenarioTagsList = ruleScenario.getTagsList();
                            List<Messages.GherkinDocument.Feature.Step> scenarioStepsList = ruleScenario.getStepsList();

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
                        if (ruleBackground == null && found) {
                            ruleToFile.append(scenarioData);
                        } else {
                            ruleToFile.append(feature.getKeyword().trim()).append(": ").append(feature.getName());
                            ruleToFile.append(System.getProperty("line.separator"));
                            ruleToFile.append(feature.getDescription());
                            ruleToFile.append(System.getProperty("line.separator"));
                            ruleToFile.append("#Language : ").append(feature.getLanguage());
                            ruleToFile.append(System.getProperty("line.separator"));

                            ruleToFile.append(ruleFromFeature.getKeyword().trim()).append(": ").append(ruleFromFeature.getName());
                            ruleToFile.append(System.getProperty("line.separator"));
                            ruleToFile.append(ruleFromFeature.getDescription());
                            ruleToFile.append(System.getProperty("line.separator"));
                            ruleToFile.append(System.getProperty("line.separator"));
                            ruleToFile.append(ruleBGString);
                            ruleToFile.append(scenarioData);
                        }


                        ruleFeatureFiles.add(ruleToFile.toString());
                    }


                }//END OF SCENARIO ELSE to read examples


            }//End of If feature children is scenario
        }

        return ruleFeatureFiles;
    }
}
