package com.lpsc.gov.app1.services;

import java.util.List;

import com.lpsc.gov.app1.pojo.TestTable;

public interface TestTableServiceI {
    TestTable saveTable(TestTable table);
    List<TestTable> getTables();
    TestTable getTestById(long id);
    
}
