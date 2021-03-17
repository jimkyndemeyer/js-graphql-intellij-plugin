package com.intellij.lang.jsgraphql.types.execution.batched;

import com.intellij.lang.jsgraphql.types.Internal;
import com.intellij.lang.jsgraphql.types.execution.ExecutionStepInfo;
import com.intellij.lang.jsgraphql.types.execution.MergedField;
import com.intellij.lang.jsgraphql.types.schema.GraphQLObjectType;

import java.util.List;
import java.util.Map;

@Deprecated
@Internal
class ExecutionNode {

    private final GraphQLObjectType type;
    private final ExecutionStepInfo executionStepInfo;
    private final Map<String, MergedField> fields;
    private final List<MapOrList> parentResults;
    private final List<Object> sources;

    public ExecutionNode(GraphQLObjectType type,
                         ExecutionStepInfo executionStepInfo,
                         Map<String, MergedField> fields,
                         List<MapOrList> parentResults,
                         List<Object> sources) {
        this.type = type;
        this.executionStepInfo = executionStepInfo;
        this.fields = fields;
        this.parentResults = parentResults;
        this.sources = sources;
    }

    public GraphQLObjectType getType() {
        return type;
    }

    public ExecutionStepInfo getExecutionStepInfo() {
        return executionStepInfo;
    }

    public Map<String, MergedField> getFields() {
        return fields;
    }

    public List<MapOrList> getParentResults() {
        return parentResults;
    }

    public List<Object> getSources() {
        return sources;
    }
}