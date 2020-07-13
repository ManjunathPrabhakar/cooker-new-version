package cookerMavenPlugin.featureFactory;


import io.cucumber.messages.Messages.GherkinDocument.Feature;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Background;
import io.cucumber.messages.Messages.GherkinDocument.Feature.FeatureChild;
import io.cucumber.messages.Messages.GherkinDocument.Feature.FeatureChild.Rule;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Tag;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Manjunath-PC
 * @created 24/04/2020
 * @project cooker-new-version
 */
public class FeatureUtils {

    /*
       Feature is a Whole
       Feature can have Background/Rule/Scenario as Child
       A Single Feature File has a Single Feature in it & can have single Background
     */

    private Feature feature = null;
    private StringBuilder featureResult = new StringBuilder();

    public FeatureUtils(Feature prFeature) {
        this.feature = prFeature;
    }

    public String getFeatureData() {


        List<Tag> featureTagsList = this.feature.getTagsList();

        for (Tag tag : featureTagsList) {
            TagUtils tagUtils = new TagUtils(tag);
            String tagData = tagUtils.getTagData();
            this.featureResult.append(tagData);
        }

        this.featureResult.append(getFeatureHeaderWithoutTags());

        List<FeatureChild> featureChildrenList = this.feature.getChildrenList();

        for (FeatureChild featureChild : featureChildrenList) {

            if (featureChild.hasBackground()) {
                Background bg = featureChild.getBackground();
                BackgroundUtils backgroundUtils = new BackgroundUtils(bg);
                String bgData = backgroundUtils.getBackgroundData();
                this.featureResult.append(bgData);
            }

            if (featureChild.hasRule()) {
                Rule r = featureChild.getRule();
                RuleUtils ruleUtils = new RuleUtils(r);
                String ruleData = ruleUtils.getRuleData();
                this.featureResult.append(ruleData);
            }

            if (featureChild.hasScenario()) {
                Scenario sc = featureChild.getScenario();
                ScenarioUtils scenarioUtils = new ScenarioUtils(sc);
                String scenarioData = scenarioUtils.getScenarioData();
                this.featureResult.append(scenarioData);
            }

            this.featureResult.append(System.getProperty("line.separator"));
        }


        return String.valueOf(this.featureResult);
    }

    public List<String> getFeatureTagsList() {
        List<String> res = new ArrayList<String>();

        try {
            for (Tag tag : this.feature.getTagsList()) {
                TagUtils tagUtils = new TagUtils(tag);
                res.add(tagUtils.getTagData().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public String getFeatureHeaderWithoutTags() {
        StringBuilder featureHeaderWOTagsResult = new StringBuilder();

        String featureKeyword = this.feature.getKeyword();
        String featureName = this.feature.getName() == null ? "" : this.feature.getName();
        String featureDescription = this.feature.getDescription() == null ? "" : this.feature.getDescription();
        String featureLanguage = this.feature.getLanguage() == null ? "" : this.feature.getLanguage();

        featureHeaderWOTagsResult.append(featureKeyword.trim()).append(": ").append(featureName);
        featureHeaderWOTagsResult.append(System.getProperty("line.separator"));
        featureHeaderWOTagsResult.append(featureDescription);
        featureHeaderWOTagsResult.append(System.getProperty("line.separator"));
        featureHeaderWOTagsResult.append("#Language : ").append(featureLanguage);
        featureHeaderWOTagsResult.append(System.getProperty("line.separator"));

        return String.valueOf(featureHeaderWOTagsResult);
    }
}

/*
Return ===

TAGSDATA
\n
FEATUREKEYWORD: FEATURENAME
\n
FEATUREDESCRITPION
\n
#Language : FEATURELANGAUAGE
\n
BACKGROUNDDATA (If Present)
RULEDATA (If Present)
SCENARIODATA (If Present)
\n

 */
