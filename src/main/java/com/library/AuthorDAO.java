package com.library;

import com.library.domain.Author;

import java.util.List;


public interface AuthorDAO
{
    List<Author> getAllAuthors();
    Author getAuthorById(Author author);
    Author getAuthorByIdWithBooksAuthors(Author author);
    List<Author> getAuthorBySurname(String surname);
    Author updateAuthor(Author author);
    void deleteAuthor(Author author);
    Author addAuthor(Author author);
}
