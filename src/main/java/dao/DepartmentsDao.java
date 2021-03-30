package dao;

import models.DepartmentNews;
import models.Departments;
import models.Users;

import java.util.List;

public interface DepartmentsDao {
    //create
    void addDepartments(Departments departments);
    void addUsersToDepartments(Users users, Departments departments);

    //read
    List<Departments> getAll();
    List<Users> getAllUsersForADepartments(int id);
    Departments findById(int id);
    List<DepartmentNews> getDepartmentNews(int id);

    //delete
    void deleteById(int id);
    void clearAll();
}
