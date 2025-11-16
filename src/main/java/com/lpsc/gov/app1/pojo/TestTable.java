package com.lpsc.gov.app1.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Entity
@Table(name = "testtable")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TestTable {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(generator = "testtable-generator")
    @GenericGenerator(name = "testtable-generator", strategy = "com.lpsc.gov.app1.generators.CustomIdGenerator")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    public TestTable() {
        ;
    }

    public TestTable(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "TestTable [id=" + id + ", name=" + name + ", code=" + code + "]";
    }

}
