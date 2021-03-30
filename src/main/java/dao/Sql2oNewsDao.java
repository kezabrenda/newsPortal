package dao;

import models.DepartmentNews;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.SQLException;
import java.util.List;

public class Sql2oNewsDao implements NewsDao {
    private final Sql2o sql2o;
    public Sql2oNewsDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void addNews(News news) {
        String sql = "INSERT INTO news (title, writtenby, content, type, users_id, departments_id, createdat) VALUES (:title, :writtenby, :content, :type, :users_id, :departments_id, :createdat)";
        try (Connection con = sql2o.open()) {
            con.getJdbcConnection().setAutoCommit(false);
            int id = (int) con.createQuery(sql, true)
                    .bind(news)
                    .executeUpdate()
                    .getKey();
            con.commit();
            news.setId(id);
        } catch (Sql2oException | SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addDepartmentNews(DepartmentNews department_news) {
        try(Connection con=sql2o.open()) {
            con.getJdbcConnection().setAutoCommit(false);
            String sql="INSERT INTO news  (title, writtenby, content, type, users_id, departments_id, createdat) VALUES (:title, :writtenby, :content, :type, :users_id, :departments_id, :createdat)";
            int id= (int) con.createQuery(sql,true)
                    .bind(department_news)
                    .executeUpdate()
                    .getKey();
            con.commit();
            department_news.setId(id);
        }catch (Sql2oException | SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public List<News> getAll() {
        try(Connection con=sql2o.open()) {
            return con.createQuery("SELECT * FROM news")
                    .executeAndFetch(News.class);
        }
    }

    @Override
    public News findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(News.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from news WHERE id=:id";
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
            con.getJdbcConnection().setAutoCommit(false);
            String sql="DELETE FROM departments";
            String sqlNews="DELETE FROM news";
            String sqlUsersDepartments="DELETE FROM users_departments";
            con.createQuery(sql).executeUpdate();
            con.createQuery(sqlUsersDepartments).executeUpdate();
            con.createQuery(sqlNews).executeUpdate();
            con.commit();
        }catch (Sql2oException | SQLException e){
            System.out.println(e);
        }
    }
}
