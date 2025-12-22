package com.lpsc.gov.app1.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BTable")
public class B {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "customidgenerator", strategy = "com.lpsc.gov.app1.generators.CustomIdGenerator")
    @GeneratedValue(generator = "customidgenerator")
    private long id;

    @Column(name = "bstring")
    private String bstring;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "aitem")
    private A aEntry;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBstring() {
        return bstring;
    }

    public void setBstring(String bstring) {
        this.bstring = bstring;
    }

    public A getaEntry() {
        return aEntry;
    }

    public void setaEntry(A aEntry) {
        this.aEntry = aEntry;
    }

}
