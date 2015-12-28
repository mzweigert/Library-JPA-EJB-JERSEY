package com.library;

import com.library.domain.Author;
import com.library.domain.Book;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface AuthorDAO
{
    List<Author> getAllAuthors();
    Author getAuthorById(Author author);
    Author getAuthorByIdWithBooks(Author author);
    List<Author> getAuthorBySurname(String surname);
    List<Book> getAuthorBooks(Author author);
    Author updateAuthor(Author author);
    void deleteAuthor(Author author);
    Author addAuthor(Author author);
}
