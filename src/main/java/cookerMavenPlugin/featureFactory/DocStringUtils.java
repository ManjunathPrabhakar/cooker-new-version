package cookerMavenPlugin.featureFactory;

import io.cucumber.messages.Messages.GherkinDocument.Feature.Step.DocString;

/**
 * @author Manjunath-PC
 * @created 24/04/2020
 * @project cooker-new-version
 */
public class DocStringUtils {

    /*
       DOCSTRING WILL BE UNDER STEP
     */

    private DocString docString = null;
    private StringBuilder docStringResult = new StringBuilder();

    public DocStringUtils(DocString prdocString) {
        this.docString = prdocString;
    }

    public String getDocStringData() {
        try {
            String docStringDemiliter = this.docString.getDelimiter() == null ? "\"\"\"" : this.docString.getDelimiter();
            String docStringMediaType = this.docString.getMediaType() == null ? "" : this.docString.getMediaType();
            String docStringContent = this.docString.getContent() == null ? "" : this.docString.getContent();

            this.docStringResult.append(docStringDemiliter).append(docStringMediaType);
            this.docStringResult.append(System.getProperty("line.separator"));
            this.docStringResult.append(docStringContent);
            this.docStringResult.append(System.getProperty("line.separator"));
            this.docStringResult.append(docStringDemiliter);
            this.docStringResult.append(System.getProperty("line.separator"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(this.docStringResult);

    }


}
/*

RETURN ===

""" MEDIATYPE (If NOTNULL)
\n
CONTENT (If NOTNULL)
\n
"""

 */
