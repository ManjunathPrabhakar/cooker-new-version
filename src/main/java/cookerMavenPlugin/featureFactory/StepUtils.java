package cookerMavenPlugin.featureFactory;

import io.cucumber.messages.Messages.GherkinDocument.Feature.Step;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Step.DataTable;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Step.DocString;

/**
 * @author Manjunath-PC
 * @created 24/04/2020
 * @project cooker-new-version
 */
public class StepUtils {
    private Step step = null;
    private StringBuilder stepData = new StringBuilder();

    public StepUtils(Step prStep) {
        this.step = prStep;
    }

    public String getStepData() {

        try {
            String stepKeyword = this.step.getKeyword();
            String stepText = this.step.getText();

            this.stepData.append(stepKeyword.trim()).append(" ").append(stepText);
            this.stepData.append(System.getProperty("line.separator")); // Append New Line


            if (this.step.hasDataTable()) {
                DataTable dt = this.step.getDataTable();

                DataTableUtils dataTableUtils = new DataTableUtils(dt);
                String dataTableData = dataTableUtils.getDataTableData();
                this.stepData.append(dataTableData);

            }
            if (this.step.hasDocString()) {
                DocString ds = this.step.getDocString();

                DocStringUtils docStringUtils = new DocStringUtils(ds);
                String docStringData = docStringUtils.getDocStringData();
                this.stepData.append(docStringData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(this.stepData);
    }
}
