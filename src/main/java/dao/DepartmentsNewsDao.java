package dao;

import models.DepartmentNews;
import models.Departments;
import models.Users;

import java.util.List;

public interface DepartmentsNewsDao {
    //create
    void addDepartmentNews(DepartmentNews departmentNews);
    void addUsersToDepartmentNews(Users users, DepartmentNews departmentNews);
    void addDepartmentToDepartmentNews(Departments departments, DepartmentNews departmentNews);

    //read
    List<DepartmentNews> getAll();
    List<DepartmentNews> getAllDepartmentNewsByDepartment(int departments_id);
    List<DepartmentNews> getAllDepartmentNewsByUsers(int users_id);
    DepartmentNews findById(int id);

    //delete
    void deleteById(int id);
    void clearAll();
}
