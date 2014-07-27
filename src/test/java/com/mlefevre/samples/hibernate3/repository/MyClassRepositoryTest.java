package com.mlefevre.samples.hibernate3.repository;

import com.mlefevre.samples.hibernate3.entity.MyClass;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context-test.xml"})
public class MyClassRepositoryTest extends BaseDataSet {

    @Autowired
    private MyClassRepository myClassRepository;

    @Before
    public void setUp() {
        try {
            this.addDataSet("myclass-dataset.xml");
            this.loadDataSets();

        } catch (DataSetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void findAllLatestVersionsTest_Success() {
        List<MyClass> results = this.myClassRepository.findAllLatestVersions();

        assertNotNull(results);
        assertEquals(2, results.size());

        for(MyClass myClass : results) {
            System.out.println(myClass.toString());
        }
    }


    @After
    public void tearDown() {
        try {
            this.cleanDataBase();

        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
