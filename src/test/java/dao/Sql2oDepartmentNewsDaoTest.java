package dao;

import models.DepartmentNews;
import models.Departments;
import models.News;
import models.Users;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oDepartmentNewsDaoTest {
    private static Connection conn;
    private static Sql2oDepartmentsDao departmentsDao;
    private static Sql2oUsersDao usersDao;
    private static Sql2oNewsDao newsDao;
    private static Sql2oGeneralNewsDao generalNewsDao;
    private static Sql2oDepartmentNewsDao departmentNewsDao;

    @BeforeClass
    public static void setUp() throws Exception {
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
    public void addDepartmentNews() {
        Users users=setUpNewUsers();
        usersDao.add(users);
        Departments departments=setUpDepartments();
        departmentsDao.addDepartments(departments);
        DepartmentNews departmentNews =new DepartmentNews("Work Life", "Brenda","Enjoyyyyy",1,1);
        newsDao.addDepartmentNews(departmentNews);
        assertEquals(users.getId(),newsDao.findById(departmentNews.getId()).getUsers_id());
        assertEquals(departmentNews.getDepartment_id(),newsDao.findById(departmentNews.getId()).getDepartment_id());
    }

    @Test
    public void getAll() {
        Users users=setUpNewUsers();
        usersDao.add(users);
        Departments departments=setUpDepartments();
        departmentsDao.addDepartments(departments);
        DepartmentNews departmentNews =new DepartmentNews("Work Life", "Brenda", "Enjoyyyyy", 1,1);
        newsDao.addDepartmentNews(departmentNews);
        News news=new News("Work Life", "Brenda", "Enjoyyyyy", 1,1);
        newsDao.addNews(news);
        assertEquals(2,newsDao.getAll().size());
    }

    private Departments setUpDepartments() {
        return new Departments("HR","deals with employees");
    }

    private Users setUpNewUsers() {
        return new Users("Brenda","CEO");
    }
}