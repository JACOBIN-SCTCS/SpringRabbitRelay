
package com.lpsc.gov.app1.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpsc.gov.app1.dto.DTOHelper;
import com.lpsc.gov.app1.dto.LibraryDTO;
import com.lpsc.gov.app1.dto.TransferDTO;
import com.lpsc.gov.app1.generics.GlobalVariables;
import com.lpsc.gov.app1.pojo.Author;
import com.lpsc.gov.app1.pojo.Books;
import com.lpsc.gov.app1.pojo.Library;
import com.lpsc.gov.app1.rabbitmq.RabbitMQProducer;
import com.lpsc.gov.app1.services.AuthorService;
import com.lpsc.gov.app1.services.BookService;
import com.lpsc.gov.app1.services.LibraryService;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private Environment env;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    private String getRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    @GetMapping("/populateRandomLibrary")
    public String populateRandomLibrary() {

        Random random = new Random();
        int author_count = random.nextInt(1, 4);
        int book_count = random.nextInt(1, 5);

        String networkType = env.getProperty("server.networktype");

        List<Author> authors = new ArrayList<Author>();

        List<Books> books = new ArrayList<>();

        for (int i = 0; i < author_count; ++i) {
            Author author = new Author();
            author.setActive(1);
            author.setName(getRandomString(7));

            author = authorService.saveAuthor(author);
            System.out.println("Generated ID=" + author.getId());
            authors.add(author);
        }
        for (int i = 0; i < book_count; ++i) {

            Books book = new Books();
            book.setActive(1);
            book.setBookName(getRandomString(20));
            book.setLibraries(null);

            int random_idx = random.nextInt(author_count);
            if (authors.size() <= 0) {
                book.setAuthor(null);
            } else {
                book.setAuthor(authors.get(random_idx));
            }
            book = bookService.saveBook(book);
            books.add(book);
        }

        Library library = new Library();
        library.setActive(1);
        library.setLibraryName(getRandomString(15));

        if (networkType.equals("INTRANET")) {
            library.setMigrationState(GlobalVariables.INTRANET_ACTION);
        } else {
            library.setMigrationState(GlobalVariables.INTERNET_ACTION);
        }
        library.setBooks(books);
        libraryService.saveLibrary(library);
        return "Added some random data";

    }

    @RequestMapping(value = "/getLibrarySerialized", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLibraryData(@RequestParam(name = "id") int id) {
        ObjectMapper mapper = new ObjectMapper();

        // Author author = authorService.findAuthorById(id);

        // Books book = bookService.findBookById(id);
        Library library = libraryService.getLibraryById(id);

        Hibernate.initialize(library);

        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(library);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;

    }

    @RequestMapping(value = "/migrateLibrary", method = RequestMethod.GET)
    public String migrateLibrary(@RequestParam(name = "id") int id) {

        ObjectMapper mapper = new ObjectMapper();
        Library library = libraryService.getLibraryById(id);

        Hibernate.initialize(library);

        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(library);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Map<String, Object> params = new HashMap<>();
        params.put("library", jsonString);

        rabbitMQProducer.sendRPCPayload("LibraryService", "{}");
        return "Migration to library successfully called";
    }

    private void testinginterfaces(TransferDTO transferDTO) {
        System.out.println(transferDTO.convertToMessage());
        System.out.println(transferDTO.getDTOType());
    }

    @RequestMapping(value = "/testlibendpoint", method = RequestMethod.GET)
    public @ResponseBody String testEndpoint() {
        ObjectMapper mapper = new ObjectMapper();
        Author author = new Author();
        author.setActive(1);
        author.setName("James");
        author.setId(3);

        List<Books> books = new ArrayList<Books>();
        Books book = new Books();
        book.setBookName("Test Title");
        book.setActive(1);
        book.setId(1);
        book.setAuthor(author);
        book.setLibraries(null);
        books.add(book);

        Books book2 = new Books();
        book2.setBookName("Test Title 2");
        book2.setActive(1);
        book2.setId(2);
        book2.setAuthor(author);
        book2.setLibraries(null);
        books.add(book2);

        Library library = new Library();
        library.setActive(1);
        library.setLibraryName("JRD Tata Library");
        library.setId(1l);
        library.setMigrationState(GlobalVariables.INTRANET_COMPLETED);

        library.setBooks(books);

        List<Library> libraries = new ArrayList<>();
        libraries.add(library);

        String resultString = "";
        try {
            resultString = mapper.writeValueAsString(library);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Library lib = libraryService.getLibraryById(1);
        try {
            resultString = mapper.writeValueAsString(lib);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Library libdeserialzied = null;
        try {
            libdeserialzied = mapper.readValue(resultString, Library.class);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" + libdeserialzied.getLibraryName());

        } catch (Exception e) {
            e.printStackTrace();
        }

        LibraryDTO dto = new LibraryDTO();

        testinginterfaces(dto);
        TransferDTO transferDTO = DTOHelper.deserializeMessage(dto.convertToMessage());

        return resultString;
    }

    @RequestMapping(value = "/libmqtest", method = RequestMethod.GET)
    public @ResponseBody String libmqtest() {

        Library library = libraryService.getLibraryById(1);
        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.library = library;

        String messagePayload = libraryDTO.convertToMessage();

        rabbitMQProducer.sendRPCPayload(libraryDTO.getDTOType(), messagePayload);

        return "LibMQTestPage";
    }

}
