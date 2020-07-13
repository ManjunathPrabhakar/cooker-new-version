package cookerMavenPlugin.featureFactory;

import io.cucumber.messages.Messages.GherkinDocument.Feature.Tag;

/**
 * @author Manjunath-PC
 * @created 24/04/2020
 * @project cooker-new-version
 */
public class TagUtils {
    private Tag tag = null;
    private StringBuilder tagResult = new StringBuilder();

    public TagUtils(Tag prTag) {
        this.tag = prTag;
    }

    public String getTagData() {
        try {
            String tagName = this.tag.getName();

            this.tagResult.append(tagName);
            this.tagResult.append(System.getProperty("line.separator")); //Append New Line
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(this.tagResult);
    }
}
