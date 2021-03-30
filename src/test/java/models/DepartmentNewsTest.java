package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentNewsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getDepartment_id() {
        DepartmentNews testDepartmentNews = setupDepartmentNews();
        assertEquals(1, testDepartmentNews.getDepartment_id());
    }

    private DepartmentNews setupDepartmentNews() {
        return new DepartmentNews("Work Life", "Brenda","Enjoyyyyy",1,1);
    }

    @Test
    public void setDepartment_id() {
        DepartmentNews testDepartmentNews = setupDepartmentNews();
        testDepartmentNews.setDepartment_id(10);
        assertNotEquals(1, testDepartmentNews.getDepartment_id());
    }

}