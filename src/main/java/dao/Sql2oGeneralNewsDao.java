package dao;

import models.GeneralNews;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oGeneralNewsDao implements GeneralNewsDao {
    private final Sql2o sql2o;
    public Sql2oGeneralNewsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void addGeneralNews(GeneralNews generalnews) {
        String sql = "INSERT INTO general_news (title, writtenby, content, createdat, users_id) VALUES (:title, :writtenby, :content, :createdat, :users_id)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(generalnews)
                    .executeUpdate()
                    .getKey();
            generalnews.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addUserToGeneralNews(Users user, GeneralNews generalNews) {
        String sql="INSERT INTO users_generalnews (users_id, generalnews_id) VALUES (:users_id, :generalnews_id)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("users_id", Users.getId())
                    .addParameter("generalnews_id", GeneralNews.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<GeneralNews> getAll() {
        try(Connection con=sql2o.open()) {
            String sql="SELECT * FROM general_news";
            return con.createQuery(sql,true)
                    .executeAndFetch(GeneralNews.class);
        }
    }

    @Override
    public List<GeneralNews> getAllGeneralNewsByUser(int users_id) {
        List<GeneralNews> generalNews = new ArrayList<>();
        String joinQuery = "SELECT generalnews_id FROM users_generalnews WHERE users_id = :users_id";

        try (Connection con = sql2o.open()) {
            List<Integer> allGeneralNewsIds = con.createQuery(joinQuery)
                    .addParameter("departments_id", users_id)
                    .executeAndFetch(Integer.class);
            for (Integer GeneralNewsId : allGeneralNewsIds){
                String generalNewsQuery = "SELECT * FROM general_news WHERE id = :generalnews_id";
                generalNews.add(
                        con.createQuery(generalNewsQuery)
                                .addParameter("generalnews_id", GeneralNewsId)
                                .executeAndFetchFirst(GeneralNews.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return generalNews;
    }

    @Override
    public GeneralNews findById(int id) {
        try(Connection con=sql2o.open()) {
            String sql="SELECT * FROM general_news WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(GeneralNews.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from general_news WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        try (Connection con=sql2o.open()){
            String sqlNews="DELETE FROM general_news";
            String sqlUsersDepartments="DELETE FROM users_departments";
            con.createQuery(sqlUsersDepartments).executeUpdate();
            con.createQuery(sqlNews).executeUpdate();
        }catch (Sql2oException e){
            System.out.println(e);
        }
    }
}
