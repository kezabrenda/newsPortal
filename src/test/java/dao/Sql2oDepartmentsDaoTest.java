package dao;

import models.Departments;
import models.Users;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oDepartmentsDaoTest {
    private static Connection conn;
    private static Sql2oDepartmentsDao departmentsDao;
    private static Sql2oUsersDao usersDao;
    private static Sql2oNewsDao newsDao;
    private static Sql2oGeneralNewsDao generalNewsDao;
    private static Sql2oDepartmentNewsDao departmentNewsDao;

    @BeforeClass
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "keza", "icecream123");
        departmentsDao = new Sql2oDepartmentsDao(sql2o);
        usersDao = new Sql2oUsersDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        generalNewsDao = new Sql2oGeneralNewsDao(sql2o);
        departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        departmentsDao.clearAll();
        usersDao.clearAll();
        newsDao.clearAll();
        generalNewsDao.clearAll();
        departmentNewsDao.clearAll();
    }
    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addDepartments() {
        Departments testDepartment = setupDepartments();
        int departmentId = testDepartment.getId();
        departmentsDao.addDepartments(testDepartment);
        assertNotEquals(departmentId, testDepartment.getId());
    }


    @Test
    public void addUsersToDepartments() {
        Users testUsers = setUsers();
        Departments testDepartment = setupDepartments();
        int departmentId = testDepartment.getId();
        departmentsDao.addUsersToDepartments(testUsers,testDepartment);
        assertNotEquals(departmentId, testDepartment.getId());
    }

    @Test
    public void noDepartmentsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentsDao.getAll().size());
    }

    private Departments setupDepartments() {
        return new Departments("HR","deals with employees");
    }
    private Users setUsers() {
        return new Users("Brenda","CEO");
    }

}