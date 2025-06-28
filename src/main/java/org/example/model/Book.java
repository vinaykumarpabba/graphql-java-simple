package org.example.model;

public class Book {
    private String id;
    private String name;
    private int pageCount;
    private String authorId;

    public Book(String id, String name, int pageCount, String authorId) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.authorId = authorId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getAuthorId() {
        return authorId;
    }
}
