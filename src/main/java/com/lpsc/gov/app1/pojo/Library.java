package com.lpsc.gov.app1.pojo;

import java.util.List;
import java.util.Set;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "library_")
public class Library {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "customidgenerator")
    @GenericGenerator(name = "customidgenerator", strategy = "com.lpsc.gov.app1.generators.CustomIdGenerator")
    private Long id;

    @Column(name = "libraryname")
    private String libraryName;

    @Column(name = "active")
    private int active;

    @Column(name = "state")
    private String migrationState;

    @ManyToMany
    @JoinTable(name = "library__books", joinColumns = { @JoinColumn(name = "library_id") }, inverseJoinColumns = {
            @JoinColumn(name = "books_id") })
    @JsonManagedReference
    private Set<Books> books;

    public Library() {
        ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getMigrationState() {
        return migrationState;
    }

    public void setMigrationState(String migrationState) {
        this.migrationState = migrationState;
    }

    public Set<Books> getBooks() {
        return books;
    }

    public void setBooks(Set<Books> books) {
        this.books = books;
    }

}
