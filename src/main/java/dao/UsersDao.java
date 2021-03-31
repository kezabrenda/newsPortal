package dao;

import models.Departments;
import models.Users;

import java.util.List;
import java.util.Map;

public interface UsersDao {
    //create
    void add(Users users);

    //read
    List<Users> getAll();
    Users findById(int id);
    List<Departments> getAllUserDepartments(int users_id);

    //delete
    void deleteById(int id);
    void clearAll();

}
