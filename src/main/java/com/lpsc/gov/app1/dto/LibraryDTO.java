package com.lpsc.gov.app1.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpsc.gov.app1.generics.GlobalVariables;
import com.lpsc.gov.app1.pojo.Author;
import com.lpsc.gov.app1.pojo.Books;
import com.lpsc.gov.app1.pojo.Library;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class LibraryDTO extends TransferDTO {

    private Library library;

    public LibraryDTO() {
        super(GlobalVariables.LIBRARY_DTO);
        ;
    }

    @Override
    public String convertToMessage() {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{}";
        try {
            jsonString = mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    public String testmethod() {
        System.out.println("Implementztion of Library DTO called");
        return "";
    }

    @Override
    public boolean saveData(Session session) {

        Transaction txn = session.beginTransaction();

        Set<Books> books = library.getBooks();
        for (Books book : books) {
            // Books book = books.get(i);
            Author author = book.getAuthor();
            boolean authorExists = false;

            authorExists = (session.get(Author.class, author.getId()) != null);
            if (authorExists) {
                session.merge(author);
            } else {
                session.save(author);
            }

            boolean bookExists = false;
            bookExists = (session.get(Books.class, book.getId()) != null);
            if (bookExists) {
                session.merge(book);
            } else {
                session.save(book);
            }

        }

        txn.commit();

        return true;
    }

    @Override
    public String getDBMessage() {
        // return this.convertToMessage();
        return "";
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

}
