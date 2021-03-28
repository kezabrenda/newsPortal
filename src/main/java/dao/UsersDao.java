package dao;

import models.Departments;
import models.Users;

import java.util.List;

public interface UsersDao {
    //create
    void  add(Users user);

    //read
    List<Users> getAll();
    Users findById(int id);
    List<Departments> getAllUserDepartments(int user_id);

    //delete
    void deleteById(int id);
    void clearAll();
}
