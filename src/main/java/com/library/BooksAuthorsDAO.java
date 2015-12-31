package com.library;

import com.library.domain.BooksAuthors;

import javax.ejb.Remote;
import java.util.List;


public interface BooksAuthorsDAO
{
    List<BooksAuthors> getAllBooksAuthors();
    BooksAuthors getBooksAuthorsById(BooksAuthors booksAuthors);
    BooksAuthors updateBooksAuthors(BooksAuthors booksAuthors);
    void deleteBooksAuthors(BooksAuthors booksAuthors);
    BooksAuthors addBooksAuthors(BooksAuthors booksAuthors);
}
