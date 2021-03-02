package com.intellij.lang.jsgraphql.types.schema;


import com.intellij.lang.jsgraphql.types.PublicApi;

/**
 * Input types represent those set of types that are allowed to be accepted as graphql mutation input, as opposed
 * to {@link com.intellij.lang.jsgraphql.types.schema.GraphQLOutputType}s which can only be used as graphql response output.
 */
@PublicApi
public interface GraphQLInputType extends GraphQLType {
}
