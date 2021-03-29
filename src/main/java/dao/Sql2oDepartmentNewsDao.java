package dao;

import models.DepartmentNews;
import models.Departments;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentNewsDao implements DepartmentsNewsDao{
    private final Sql2o sql2o;
    public Sql2oDepartmentNewsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public void addDepartmentNews(DepartmentNews departmentNews) {
        try(Connection con=sql2o.open()) {
            String sql="INSERT INTO department_news  (title, writtenBy, content, createdat, users_id, departments_id) VALUES (:tittle, :writtenBy, :content, :createdat, :users_id,:departments_id)";
            int id= (int) con.createQuery(sql,true)
                    .bind(departmentNews)
                    .executeUpdate()
                    .getKey();
            departmentNews.setId(id);
        }catch (Sql2oException e){
            System.out.println(e);
        }
    }

    @Override
    public void addUsersToDepartmentNews(Users users, DepartmentNews departmentNews) {
        String sql="INSERT INTO users_departmentnews (users_id, departmentnews_id) VALUES (:users_id,:departmentnews_id_id)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("users_id", Users.getId())
                    .addParameter("departmentnews_id", DepartmentNews.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void addDepartmentToDepartmentNews(Departments departments, DepartmentNews departmentNews) {
        String sql="INSERT INTO departments_departmentnews (departments_id, departmentnews_id) VALUES (:departments_id,:departmentnews_id_id)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departments_id", Departments.getId())
                    .addParameter("departmentnews_id", DepartmentNews.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<DepartmentNews> getAll() {
        try(Connection con=sql2o.open()) {
            String sql="SELECT * FROM department_news";
            return con.createQuery(sql,true)
                    .executeAndFetch(DepartmentNews.class);
        }
    }

    @Override
    public List<DepartmentNews> getAllDepartmentNewsByDepartment(int departments_id) {
        List<DepartmentNews> departmentNews = new ArrayList();
        String joinQuery = "SELECT departmentnews_id FROM departments_departmentnews WHERE departments_id = :departments_id";
        try (Connection con = sql2o.open()) {
            List<Integer> allDepartmentNewsIds = con.createQuery(joinQuery)
                    .addParameter("departments_id", departments_id)
                    .executeAndFetch(Integer.class);
            for (Integer DepartmentNewsId : allDepartmentNewsIds){
                String departmentNewsQuery = "SELECT * FROM departmentnews WHERE id = :departmentnews_id";
                departmentNews.add(
                        con.createQuery(departmentNewsQuery)
                                .addParameter("departmentnews_id", DepartmentNewsId)
                                .executeAndFetchFirst(DepartmentNews.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departmentNews;
    }

    @Override
    public List<DepartmentNews> getAllDepartmentNewsByUsers(int users_id) {
        List<DepartmentNews> departmentNews = new ArrayList();
        String joinQuery = "SELECT departmentnews_id FROM users_departmentnews WHERE users_id = :users_id";

        try (Connection con = sql2o.open()) {
            List<Integer> allDepartmentNewsIds = con.createQuery(joinQuery)
                    .addParameter("departments_id", users_id)
                    .executeAndFetch(Integer.class);
            for (Integer DepartmentNewsId : allDepartmentNewsIds){
                String departmentNewsQuery = "SELECT * FROM departmentnews WHERE id = :departmentnews_id";
                departmentNews.add(
                        con.createQuery(departmentNewsQuery)
                                .addParameter("departmentnews_id", DepartmentNewsId)
                                .executeAndFetchFirst(DepartmentNews.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departmentNews;
    }

    @Override
    public DepartmentNews findById(int id) {
        try(Connection con=sql2o.open()) {
            String sql="SELECT * FROM department_news WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(DepartmentNews.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from department_news WHERE id=:id";
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
            String sql="DELETE FROM departments";
            String sqlNews="DELETE FROM department_news";
            String sqlUsersDepartments="DELETE FROM users_departments";
            con.createQuery(sql).executeUpdate();
            con.createQuery(sqlUsersDepartments).executeUpdate();
            con.createQuery(sqlNews).executeUpdate();
        }catch (Sql2oException e){
            System.out.println(e);
        }
    }
}
