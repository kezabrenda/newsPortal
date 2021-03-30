package dao;

import models.DepartmentNews;
import models.Departments;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentsDao implements DepartmentsDao {
    private final Sql2o sql2o;

    public Sql2oDepartmentsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void addDepartments(Departments departments) {
        try(Connection con=sql2o.open()) {
            con.getJdbcConnection().setAutoCommit(false);
            String sql="INSERT INTO departments (name,description,size) VALUES (:name,:description,:size)";
            int id=(int) con.createQuery(sql,true)
                    .bind(departments)
                    .executeUpdate()
                    .getKey();
            con.commit();
            departments.setId(id);
        }catch (Sql2oException | SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public void addUsersToDepartments(Users users, Departments departments) {
        try(Connection con=sql2o.open()) {
            String sql="INSERT INTO users_departments (users_id,departments_id) VALUES (:users_id,:departments_id)";
            con.createQuery(sql)
                    .addParameter("users_id",users.getId())
                    .addParameter("departments_id",departments.getId())
                    .executeUpdate();
            String sizeQuery="SELECT users_id FROM users_departments";
            List<Integer> size=con.createQuery(sizeQuery)
                    .executeAndFetch(Integer.class);
            String updateDepartmentSize="UPDATE departments SET size=:size WHERE id=:id";
            con.createQuery(updateDepartmentSize).addParameter("id",departments.getId())
                    .addParameter("size",size.size())
                    .executeUpdate();
        }catch (Sql2oException e){
            System.out.println(e);
        }
    }

    @Override
    public List<Departments> getAll() {
        try (Connection con=sql2o.open()){
            String sql= "SELECT * FROM departments";
            return con.createQuery(sql)
                    .executeAndFetch(Departments.class);

        }
    }

    @Override
    public List<Users> getAllUsersForADepartments(int departments_id) {
        List<Users> users=new ArrayList<>();
        try (Connection con=sql2o.open()){
            String sql= "SELECT user_id FROM users_departments WHERE departments_id=:departments_id";
            List<Integer> users_ids=con.createQuery(sql)
                    .addParameter("departments_id",departments_id)
                    .executeAndFetch(Integer.class);

            for(Integer id : users_ids){
                String usersResults="SELECT * FROM Users WHERE id=:id";
                users.add(con.createQuery(usersResults)
                        .addParameter("id",id)
                        .executeAndFetchFirst(Users.class));

            }

            return users;
        }
    }

    @Override
    public Departments findById(int id) {
        try (Connection con=sql2o.open()){
            String sql= "SELECT * FROM departments WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Departments.class);

        }
    }

    @Override
    public List<DepartmentNews> getDepartmentNews(int id) {
        try(Connection con=sql2o.open()) {
            String sql="SELECT * FROM department_news WHERE id=:id ";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetch(DepartmentNews.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from departments WHERE id=:id";
        String deleteJoin = "DELETE from users_departments WHERE users_id = :users_id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();

            con.createQuery(deleteJoin)
                    .addParameter("users_id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        try (Connection con=sql2o.open()){
            String sql="DELETE FROM departments";
            String sqlUsersDepartments="DELETE FROM users_departments";
            con.createQuery(sql).executeUpdate();
            con.createQuery(sqlUsersDepartments).executeUpdate();

        }catch (Sql2oException e){
            System.out.println(e);
        }
    }
}
