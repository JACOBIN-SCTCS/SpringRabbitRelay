package com.lpsc.gov.app1.pojo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "TableA")
public class A {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "customidgenerator", strategy = "com.lpsc.gov.app1.generators.CustomIdGenerator")
    @GeneratedValue(generator = "customidgenerator")
    private long id;

    @Column(name = "title")
    private String title;

    @OneToMany(orphanRemoval = true, mappedBy = "aEntry", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private List<B> bEntries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<B> getbEntries() {
        return bEntries;
    }

    public void setbEntries(List<B> bEntries) {
        this.bEntries = bEntries;
    }

}
