package com.lpsc.gov.app1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lpsc.gov.app1.pojo.TestTable;
import com.lpsc.gov.app1.repository.TestTableRepo;

@Service("TestTableService")
public class TestTableService implements TestTableServiceI{

    @Autowired
    private TestTableRepo testTableRepo;

    @Override
    public TestTable saveTable(TestTable table) {
        return testTableRepo.save(table);
    }

    @Override
    public List<TestTable> getTables() {
        return testTableRepo.findAll();
    }

    @Override
    public TestTable getTestById(long id) {
       return testTableRepo.getById(id);
    }
        
}
