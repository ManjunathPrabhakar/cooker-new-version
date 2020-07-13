package cookerMavenPlugin.featureFactory;

import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario.Examples;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Step;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Tag;

import java.util.ArrayList;
import java.util.List;

public class ScenarioUtils {
    private Scenario scenario = null;
    private StringBuilder scenarioResult = new StringBuilder();

    public ScenarioUtils(Scenario prScenario) {
        this.scenario = prScenario;
    }

    public String getScenarioData() {

        try {
            String scenarioKeyword = this.scenario.getKeyword();
            String scenarioName = this.scenario.getName() == null ? "" : this.scenario.getName();
            String scenarioDescription = this.scenario.getDescription() == null ? "" : this.scenario.getDescription();
            List<Tag> scenarioTagsList = this.scenario.getTagsList();
            List<Step> scenarioStepsList = this.scenario.getStepsList();
            List<Examples> scenarioExamplesList = this.scenario.getExamplesList();

            for (Tag tag : scenarioTagsList) {
                TagUtils tagUtils = new TagUtils(tag);
                String tagData = tagUtils.getTagData();
                this.scenarioResult.append(tagData);
            }

            this.scenarioResult.append(scenarioKeyword).append(": ").append(scenarioName);
            this.scenarioResult.append(System.getProperty("line.separator"));
            this.scenarioResult.append(scenarioDescription == null ? "" : scenarioDescription);
            this.scenarioResult.append(System.getProperty("line.separator"));


            for (Step s : scenarioStepsList) {
                StepUtils stepUtils = new StepUtils(s);
                String stepData = stepUtils.getStepData();
                this.scenarioResult.append(stepData);
            }


            for (Examples e : scenarioExamplesList) {
                ExampleUtils exampleUtils = new ExampleUtils(e);
                String exampleData = exampleUtils.getExamplesData();
                this.scenarioResult.append(exampleData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return String.valueOf(this.scenarioResult);
    }

    public List<String> getScenarioTagsList() {
        List<String> res = new ArrayList<String>();
        try {
            for (Tag tag : this.scenario.getTagsList()) {
                TagUtils tagUtils = new TagUtils(tag);
                res.add(tagUtils.getTagData().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
