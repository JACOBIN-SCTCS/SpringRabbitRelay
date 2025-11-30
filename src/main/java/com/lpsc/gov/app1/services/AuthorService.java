package com.lpsc.gov.app1.services;

import com.lpsc.gov.app1.pojo.Author;
import com.lpsc.gov.app1.repository.AuthorRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AuthorService")
public class AuthorService implements AuthorServiceI {

    @Autowired
    private AuthorRepo authorRepo;

    @Override
    public Author saveAuthor(Author author) {
        return authorRepo.save(author);
    }

}
