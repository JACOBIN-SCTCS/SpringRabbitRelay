package com.lpsc.gov.app1.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;

@Entity
public class TestFile {

    @Id
    @Column(name = "id")
    private int id;

}
