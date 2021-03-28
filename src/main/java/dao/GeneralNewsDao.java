package dao;

import models.GeneralNews;
import models.Users;

import java.util.List;

public interface GeneralNewsDao {
    //create
    void addGeneralNews(GeneralNews generalnews);
    void addUserToGeneralNews(Users user, GeneralNews generalNews);

    //read
    List<GeneralNews> getAll();
    List<GeneralNews> getAllGeneralNewsByUser(int employee_id);
    GeneralNews findById(int id);

    //delete
    void deleteById(int id);
    void clearAll();
}
