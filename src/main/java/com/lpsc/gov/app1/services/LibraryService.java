package com.lpsc.gov.app1.services;

import com.lpsc.gov.app1.pojo.Library;
import com.lpsc.gov.app1.repository.LibraryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("LibraryService")
public class LibraryService implements LibraryServiceI {

    @Autowired
    private LibraryRepo libraryRepo;

    @Override
    public Library saveLibrary(Library library) {

        // TODO Auto-generated method stub
        return libraryRepo.save(library);
    }

}
