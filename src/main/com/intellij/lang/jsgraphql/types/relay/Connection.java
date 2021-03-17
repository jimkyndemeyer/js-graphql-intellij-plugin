package com.intellij.lang.jsgraphql.types.relay;

import com.intellij.lang.jsgraphql.types.PublicApi;

import java.util.List;

/**
 * This represents a connection in Relay, which is a list of {@link com.intellij.lang.jsgraphql.types.relay.Edge edge}s
 * as well as a {@link com.intellij.lang.jsgraphql.types.relay.PageInfo pageInfo} that describes the pagination of that list.
 *
 * See <a href="https://facebook.github.io/relay/graphql/connections.htm">https://facebook.github.io/relay/graphql/connections.htm</a>
 */
@PublicApi
public interface Connection<T> {

    /**
     * @return a list of {@link com.intellij.lang.jsgraphql.types.relay.Edge}s that are really a node of data and its cursor
     */
    List<Edge<T>> getEdges();

    /**
     * @return {@link com.intellij.lang.jsgraphql.types.relay.PageInfo} pagination data about about that list of edges
     */
    PageInfo getPageInfo();

}