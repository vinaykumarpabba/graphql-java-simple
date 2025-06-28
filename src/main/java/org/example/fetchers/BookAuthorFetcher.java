package org.example.fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingEnvironmentImpl;
import org.example.model.Author;
import org.example.model.Book;

import java.util.Map;

public class BookAuthorFetcher implements DataFetcher<Author> {
    DataFetcher<Author> authorByIdFetcher;

    public BookAuthorFetcher(DataFetcher<Author> authorByIdFetcher) {
        this.authorByIdFetcher = authorByIdFetcher;
    }

    @Override
    public Author get(DataFetchingEnvironment environment) throws Exception {
        Book book = environment.getSource();
        String authorId = book.getAuthorId();

        return fetchAuthorById(authorId);
    }

    private Author fetchAuthorById(String authorId) throws Exception {
        DataFetchingEnvironment newEnv = DataFetchingEnvironmentImpl
                                            .newDataFetchingEnvironment()
                                            .arguments(Map.of("id", authorId))
                                            .build();

        return authorByIdFetcher.get(newEnv);
    }
}
