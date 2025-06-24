package org.example.fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class HelloFetcher implements DataFetcher<String> {

    @Override
    public String get(DataFetchingEnvironment environment) {
        return "Hello, World!";
    }
}
