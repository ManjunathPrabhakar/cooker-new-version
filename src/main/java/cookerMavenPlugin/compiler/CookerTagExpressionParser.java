/*
 * Copyright (c) 2020.  Manjunath Prabhakar manjunath189@gmail.com
 */

package cookerMavenPlugin.compiler;

import io.cucumber.tagexpressions.Expression;
import io.cucumber.tagexpressions.TagExpressionParser;

import java.util.List;

/**
 * @author Manjunath-PC
 * @created 11/09/2020
 * @project cooker-new-version
 */
public class CookerTagExpressionParser {

    public boolean tagParser(String userTags, List<String> scenarioOrFeatureTags) {

        Expression expression = TagExpressionParser.parse(userTags);
        boolean res = expression.evaluate(scenarioOrFeatureTags);

        return res;
    }
}
