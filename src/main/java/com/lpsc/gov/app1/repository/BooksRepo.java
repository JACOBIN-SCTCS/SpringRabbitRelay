package com.lpsc.gov.app1.repository;

import com.lpsc.gov.app1.pojo.Books;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepo extends JpaRepository<Books, Long> {

}
