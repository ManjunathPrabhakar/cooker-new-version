package cookerMavenPlugin.featureFactory;

import io.cucumber.messages.Messages.GherkinDocument.Feature.Background;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Step;

import java.util.List;

/**
 * @author Manjunath-PC
 * @created 24/04/2020
 * @project cooker-new-version
 */
public class BackgroundUtils {

    /*
    BACKGROUND WILL BE UNDER FEATURE or RULE
    BACKGROUND CAN HAVE KEYWORD, NAME, DESCRIPTION, STEPS
    BACKGROUND CANNOT HAVE TAGS, EXAMPLES
     */

    private Background background;
    private StringBuilder backgroundResult = new StringBuilder();
    private String backgroundStepData = null;
    private StepUtils stepUtils = null;

    public BackgroundUtils(Background prBackground) {
        this.background = prBackground;
    }

    public String getBackgroundData() {
        try {
            String backgroundKeyword = this.background.getKeyword();
            String backgroundName = this.background.getName() == null ? "" : this.background.getName();
            String backgroundDescription = this.background.getDescription() == null ? "" : this.background.getDescription();
            List<Step> backgroundStepsList = this.background.getStepsList();

            this.backgroundResult.append(backgroundKeyword).append(": ").append(backgroundName);
            this.backgroundResult.append(System.getProperty("line.separator"));
            this.backgroundResult.append(backgroundDescription);
            this.backgroundResult.append(System.getProperty("line.separator"));

            for (Step backgroundStep : backgroundStepsList) {
                this.stepUtils = new StepUtils(backgroundStep);
                this.backgroundStepData = stepUtils.getStepData();
                this.backgroundResult.append(this.backgroundStepData);
            }

            this.backgroundResult.append(System.getProperty("line.separator")); //Append New Line
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(this.backgroundResult);
    }
}

/*

Return==

Background: NAME (if NotNull)
\n
DESCRIPTION (if NotNull)
\n
STEPDATA (from StepUtils)
\n

 */
