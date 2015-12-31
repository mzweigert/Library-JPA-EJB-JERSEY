package com.library;


import com.library.domain.Book;
import com.library.domain.Hiring;
import com.library.domain.Reader;

import java.util.List;

public interface HiringDAO
{

    List<Hiring> getAllHirings();
    Hiring getHiringById(Hiring hiring);
    List<Hiring> getHiringsByIdReader(Reader reader);
    List<Hiring> getHiringsByIdBook(Book book);
    Hiring updateHiring(Hiring hiring);
    void deleteHiring(Hiring hiring);
    Hiring addHiring(Hiring hiring);

}
