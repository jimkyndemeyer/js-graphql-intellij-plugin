package com.intellij.lang.jsgraphql.validation;

import com.intellij.lang.jsgraphql.GraphQLBaseTestCase;
import org.jetbrains.annotations.NotNull;

public class GraphQLOperationsValidationTest extends GraphQLBaseTestCase {
    @Override
    protected @NotNull String getBasePath() {
        return "/validation/operations";
    }

    public void testRepeatableDirectives() {
        doHighlightingTest();
    }

    public void testWrongTypes() {
        doHighlightingTest();
    }

    public void testDuplicates() {
        doHighlightingTest();
    }

}