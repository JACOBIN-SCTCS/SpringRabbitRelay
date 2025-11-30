package com.lpsc.gov.app1.services;

import com.lpsc.gov.app1.pojo.Books;
import com.lpsc.gov.app1.repository.BooksRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BookService")
public class BookService implements BookServiceI {

    @Autowired
    private BooksRepo booksRepo;

    @Override
    public Books saveBook(Books book) {
        return booksRepo.saveAndFlush(book);
    }

    @Override
    public Books findBookById(long id) {
        return booksRepo.findById(id).get();
    }

}
