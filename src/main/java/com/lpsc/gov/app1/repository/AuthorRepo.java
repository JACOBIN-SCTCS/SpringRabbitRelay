package com.lpsc.gov.app1.repository;

import com.lpsc.gov.app1.pojo.Author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {

}
