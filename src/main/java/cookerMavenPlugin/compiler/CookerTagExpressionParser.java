/*
 * Copyright (c) 2020.  Manjunath Prabhakar manjunath189@gmail.com
 */

package cookerMavenPlugin.compiler;

import io.cucumber.tagexpressions.Expression;
import io.cucumber.tagexpressions.TagExpressionParser;

import java.util.List;

/**
 * @author Manjunath Prabhakar
 * @created 11/09/2020
 * @project cooker-new-version
 */
public class CookerTagExpressionParser {

    /**
     *
     */
    public CookerTagExpressionParser() {

    }

    /**
     * @param userTags              Tags user need to include / exclude to do execution
     * @param scenarioOrFeatureTags Feature/Scenario/Example Tags to validate against
     * @return boolean based on evaluation
     */
    public boolean tagParser(String userTags, List<String> scenarioOrFeatureTags) {
        Expression expression = TagExpressionParser.parse(userTags);
        return expression.evaluate(scenarioOrFeatureTags);
    }
}
