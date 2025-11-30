package com.lpsc.gov.app1.services;

import com.lpsc.gov.app1.pojo.Books;

public interface BookServiceI {
    Books saveBook(Books book);

    Books findBookById(long id);
}
