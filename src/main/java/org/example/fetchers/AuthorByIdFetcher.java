package org.example.fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.example.model.Author;

import java.util.Arrays;
import java.util.List;

public class AuthorByIdFetcher implements DataFetcher<Author> {

    private final List<Author> authors = Arrays.asList(
            new Author("1", "Joanne", "Rowling"),
            new Author("2", "Herman", "Melville"),
            new Author("3", "F. Scott", "Fitzgerald"),
            new Author("4", "Harper", "Lee")
    );

    @Override
    public Author get(DataFetchingEnvironment environment) {
        String authorId = environment.getArgument("id");

        return authors.stream()
                .filter(author -> author.getId().equals(authorId))
                .findFirst()
                .orElse(null);
    }

}
