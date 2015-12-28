package com.library;


import com.library.domain.Reader;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface ReaderDAO
{

    List<Reader> getAllReaders();
    Reader getReaderById(Reader reader);
    List<Reader> getReadersBySurname(String surname);
    Reader getReaderByIdWithHirings(Reader reader);
    Reader updateReader(Reader reader);
    void deleteReader(Reader reader);
    Reader addReader(Reader reader);

}
