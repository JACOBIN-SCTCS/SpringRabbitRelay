package com.lpsc.gov.app1.services;

import com.lpsc.gov.app1.pojo.Author;
import com.lpsc.gov.app1.repository.AuthorRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AuthorService")
public class AuthorService implements AuthorServiceI {

    @Autowired
    private AuthorRepo authorRepo;

    @Override
    @Transactional()
    public Author saveAuthor(Author author) {
        return authorRepo.saveAndFlush(author);
    }

    @Override
    public Author findAuthorById(long id) {
        return authorRepo.findById(id).get();
    }

}
