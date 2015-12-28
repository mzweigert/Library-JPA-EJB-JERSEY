package com.library;

import com.library.domain.Book;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface BookDAO
{

    List<Book> getAllBooks();
    Book getBookById(Book book);
    Book getBookByIdWithAuthors(Book book);
    Book getBookByIdWithHirings(Book book);
    List<Book> getBookByTitle(String title);
    Book updateBook(Book book);
    void deleteBook(Book book);
    Book addBook(Book book);


}
