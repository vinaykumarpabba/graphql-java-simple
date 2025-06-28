package org.example.fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.example.model.Book;

import java.util.List;

public class BookByIdFetcher implements DataFetcher<Book> {

    private final List<Book> books = List.of(
        new Book("1", "The Great Gatsby", 180, "1"),
        new Book("2", "To Kill a Mockingbird", 200, "2"),
        new Book("3", "1984", 300, "3"),
        new Book("4", "Pride and Prejudice", 250, "4"),
        new Book("5", "Moby Dick", 600, "5")
    );

    @Override
    public Book get(DataFetchingEnvironment environment) {
        String bookId = environment.getArgument("id");

        return books.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst()
                .orElse(null);
    }

}
