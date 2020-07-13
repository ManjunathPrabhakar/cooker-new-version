package cookerMavenPlugin.featureFactory;

import io.cucumber.messages.Messages.GherkinDocument.Feature.Background;
import io.cucumber.messages.Messages.GherkinDocument.Feature.FeatureChild.Rule;
import io.cucumber.messages.Messages.GherkinDocument.Feature.FeatureChild.RuleChild;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario;

import java.util.List;

/**
 * @author Manjunath-PC
 * @created 24/04/2020
 * @project cooker-new-version
 */
public class RuleUtils {

    /*
    RULE CAN BE A DIRECT CHILD OF A FEATURE
    RULE HAS KEYWORD, NAME, DESCRIPTION
    RULE CAN HAVE BACKGROUND / SCENARIO / SCENARIO OUTLINE
     */

    private Rule rule = null;
    private StringBuilder ruleResult = new StringBuilder();

    public RuleUtils(Rule prRule) {
        this.rule = prRule;
    }

    public String getRuleData() {


        try {

            List<RuleChild> sc = this.rule.getChildrenList();

            this.ruleResult.append(getRuleHeader());
            this.ruleResult.append(System.getProperty("line.separator"));


            for (RuleChild rr : sc) {
                if (rr.hasBackground()) {
                    Background bg = rr.getBackground();
                    BackgroundUtils backgroundUtils = new BackgroundUtils(bg);
                    String bgData = backgroundUtils.getBackgroundData();
                    this.ruleResult.append(bgData);
                    this.ruleResult.append(System.getProperty("line.separator"));

                }
                if (rr.hasScenario()) {
                    Scenario scn = rr.getScenario();
                    ScenarioUtils scenarioUtils = new ScenarioUtils(scn);
                    String scenarioData = scenarioUtils.getScenarioData();
                    this.ruleResult.append(scenarioData);
                    this.ruleResult.append(System.getProperty("line.separator"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(this.ruleResult);
    }

    public String getRuleHeader() {
        StringBuilder ruleHeaderResult = new StringBuilder();

        String ruleKeyword = this.rule.getKeyword();
        String ruleName = this.rule.getName() == null ? "" : this.rule.getName();
        String ruleDescription = this.rule.getDescription() == null ? "" : this.rule.getDescription();

        ruleHeaderResult.append(ruleKeyword).append(": ").append(ruleName);
        ruleHeaderResult.append(System.getProperty("line.separator"));
        ruleHeaderResult.append(ruleDescription);
        ruleHeaderResult.append(System.getProperty("line.separator"));

        return String.valueOf(ruleHeaderResult);
    }
}
/*

Return ===

RULEKEYWORD: RULENAME(If notNull)
\n
RULEDESCRIPTION
\n
\n
BACKGROUND (IF ANY)
\n
SCENARIO (IF ANY)
\n

 */