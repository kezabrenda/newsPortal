package dao;

import models.Departments;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Sql2oUsersDao implements UsersDao {
    private final Sql2o sql2o;
    public Sql2oUsersDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Users users) {
        String sql = "INSERT INTO users (name, position, role) VALUES (:name, :position, :role)";
        try (Connection con = sql2o.open()) {
            con.getJdbcConnection().setAutoCommit(false);
            int id = (int) con.createQuery(sql, true)
                    .bind(users)
                    .executeUpdate()
                    .getKey();
            con.commit();
            users.setId(id);
        } catch (Sql2oException | SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Users> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM users")
                    .executeAndFetch(Users.class);
        }
    }

    @Override
    public Users findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM users WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Users.class);
        }
    }

    @Override
    public List<Departments> getAllUserDepartments(int users_id) {
        List<Departments> departments=new ArrayList<>();
        try (Connection con=sql2o.open()) {
            String sql = "SELECT departments_id FROM users_departments WHERE users_id=:users_id";
            List<Integer> department_ids = con.createQuery(sql)
                    .addParameter("users_id", users_id)
                    .executeAndFetch(Integer.class);
            for (Integer id : department_ids) {
                String usersResults = "SELECT * FROM departments WHERE id=:id";
                departments.add(con.createQuery(usersResults)
                        .addParameter("id", id)
                        .executeAndFetchFirst(Departments.class));
            }
            return departments;
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from users WHERE id = :id";
        String deleteJoin = "DELETE from users_departments WHERE users_id = :users_id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("users_d", id)
                    .executeUpdate();

        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        try (Connection con=sql2o.open()){
            String sql ="DELETE FROM users ";
            con.createQuery(sql).executeUpdate();
            String sqlUsersDepartments="DELETE FROM users_departments";
            con.createQuery(sqlUsersDepartments).executeUpdate();
        }catch (Sql2oException e){
            System.out.println(e);
        }
    }
}
