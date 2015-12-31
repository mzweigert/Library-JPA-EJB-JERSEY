package com.library;

import com.library.domain.Book;

import java.util.List;


public interface BookDAO
{

    List<Book> getAllBooks();
    Book getBookById(Book book);
    Book getBookByIdWithBooksAuthors(Book book);
    Book getBookByIdWithHirings(Book book);
    List<Book> getBookByTitle(String title);
    Book updateBook(Book book);
    void deleteBook(Book book);
    Book addBook(Book book);


}
