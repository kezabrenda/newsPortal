import dao.Sql2oDepartmentNewsDao;
import dao.Sql2oDepartmentsDao;
import dao.Sql2oGeneralNewsDao;
import dao.Sql2oUsersDao;
import exceptions.ApiException;
import models.DepartmentNews;
import models.Departments;
import models.GeneralNews;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Sql2oDepartmentsDao sql2oDepartmentsDao;
        Sql2oUsersDao sql2oUsersDao;
        Sql2oGeneralNewsDao sql2oGeneralNewsDao;
        Sql2oDepartmentNewsDao sql2oDepartmentNewsDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/newsportal";
        Sql2o sql2o = new Sql2o(connectionString, "keza", "icecream123");

        sql2oDepartmentsDao = new Sql2oDepartmentsDao(sql2o);
        sql2oUsersDao = new Sql2oUsersDao(sql2o);
        sql2oGeneralNewsDao = new  Sql2oGeneralNewsDao(sql2o);
        sql2oDepartmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
        conn = sql2o.open();

        staticFileLocation("/public");
              /********************************************************/
        get("/",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());

        get("/create/departments",(request, response) -> { /* /departments/new */
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"departmentsForm.hbs");
        },new HandlebarsTemplateEngine());

        post("/create/departments/new",(request, response) -> { /* /departments/new/new */
            Map<String,Object> model=new HashMap<String, Object>();
            String name=request.queryParams("name");
            String description=request.queryParams("description");
            String size=request.queryParams("size");
            Departments departments=new Departments(name,description);
            sql2oDepartmentsDao.addDepartments( departments);
            request.session().attribute("item", name);
            model.put("item", request.session().attribute("item"));
            return new ModelAndView(model,"departmentsForm.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/departments",(request, response) -> { /* /departments */
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("departments",sql2oDepartmentsDao.getAll());
            return new ModelAndView(model,"departments.hbs");
        },new HandlebarsTemplateEngine());


        ///////////////////////////////////////////////////////////////////////////
        post("/departments/new", "application/json", (req, res) -> {
            Departments departments = gson.fromJson(req.body(), Departments.class);
            sql2oDepartmentsDao.addDepartments(departments);
            res.status(201);
            res.type("application/json");
            return gson.toJson(departments);
        });

        get("/departments/all","application/json",(req, res) -> {
            res.type("application/json");
            if(sql2oDepartmentsDao.getAll().size()>0){
                return gson.toJson(sql2oDepartmentsDao.getAll());
            }
            else {
                return "{\"message\":\"Sorry, there are no registered departments .\"}";
            }
        });

        get("/departments/:id/users","application/json",(req, res) -> {
            int id=Integer.parseInt(req.params("id"));
            if(sql2oDepartmentsDao.getAllUsersForADepartments(id).size()>0){
                return gson.toJson(sql2oDepartmentsDao.getAllUsersForADepartments(id));
            }
            else {
                return "{\"message\":\"This department has no users.\"}";
            }
        });

        get("/departments/:id","application/json",(req, res) -> {
            int id=Integer.parseInt(req.params("id"));
            if(sql2oDepartmentsDao.findById(id)==null){
                throw new ApiException(404, String.format("No department with id: \"%s\" exists",
                        req.params("id")));
            }
            else {
                return gson.toJson(sql2oDepartmentsDao.findById(id));
            }
        });

        get("/news/departments/:id","application/json",(req, res) -> {
            int id=Integer.parseInt(req.params("id"));
            Departments departments=sql2oDepartmentsDao.findById(id);
            if(departments==null){
                throw new ApiException(404, String.format("No department with id: \"%s\" exists",
                        req.params("id")));
            }
            if(sql2oDepartmentsDao.getDepartmentNews(id).size()>0){
                return gson.toJson(sql2oDepartmentsDao.getDepartmentNews(id));
            }
            else {
                return "{\"message\":\"no news in this department.\"}";
            }
        });
/*************************************************************************************************************/

        get("/create/users",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("departments",sql2oDepartmentsDao.getAll());
            return new ModelAndView(model,"usersForm.hbs");
        },new HandlebarsTemplateEngine());

        post("/create/users/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            String name=request.queryParams("name");
            String position=request.queryParams("position");
            int departments_id= Integer.parseInt(request.queryParams("departments"));
            Users users = new Users(name,position,departments_id);
            sql2oUsersDao.add(users);
            request.session().attribute("item", name);
            model.put("item", request.session().attribute("item"));
            return new ModelAndView(model,"usersForm.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/users",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("users",sql2oUsersDao.getAll());
            model.put("departments",sql2oDepartmentsDao.getAll());
            return new ModelAndView(model,"users.hbs");
        },new HandlebarsTemplateEngine());

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        post("/users/new","application/json",(req, res) -> {
            Users users=gson.fromJson(req.body(),Users.class);
            sql2oUsersDao.add(users);
            res.status(201);
            return gson.toJson(users);
        });

        post("/add/users/:users_id/departments/:departments_id","application/json",(req, res) -> {
            int users_id=Integer.parseInt(req.params("users_id"));
            int departments_id=Integer.parseInt(req.params("departments_id"));
            Departments departments=sql2oDepartmentsDao.findById(departments_id);
            Users users=sql2oUsersDao.findById(users_id);
            if(departments==null){
                throw new ApiException(404, String.format("No department with id: \"%s\" exists",
                        req.params("departments_id")));
            }
            if(users==null){
                throw new ApiException(404, String.format("No user with id: \"%s\" exists",
                        req.params("users_id")));
            }
            sql2oDepartmentsDao.addUsersToDepartments(users,departments);
            List<Users> departmentUsers=sql2oDepartmentsDao.getAllUsersForADepartments(departments.getId());
            res.status(201);
            return gson.toJson(departmentUsers);
        });

        get("/users", "application/json", (req, res) -> {
            if(sql2oDepartmentsDao.getAll().size() > 0){
                return gson.toJson(sql2oUsersDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no users are currently listed in the database.\"}";
            }
        });

        get("/users/:id/departments","application/json",(req, res) -> {
            int id=Integer.parseInt(req.params("id"));
            if(sql2oUsersDao.getAllUserDepartments(id).size()>0){
                return gson.toJson(sql2oUsersDao.getAllUserDepartments(id));
            }
            else {
                return "{\"message\":\"I'm sorry, but users is in no department.\"}";
            }
        });

        get("/users/:id", "application/json", (req, res) -> {
            int id=Integer.parseInt(req.params("id"));
            if(sql2oUsersDao.findById(id)==null){
                throw new ApiException(404, String.format("No users with the id: \"%s\" exists",
                        req.params("id")));
            }
            else {
                return gson.toJson(sql2oUsersDao.findById(id));
            }
        });

        /*******************************************************************************************************/
        get("/create/generalNews",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("users",sql2oUsersDao.getAll());
            return new ModelAndView(model,"generalNewsForm.hbs");
        },new HandlebarsTemplateEngine());

        post("/create/generalNews/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            String title=request.queryParams("title");
            String writtenBy=request.queryParams("writtenby");
            String content=request.queryParams("content");
            int users_id=Integer.parseInt(request.params("users"));
            GeneralNews generalnews=new GeneralNews(title,writtenBy, content,users_id);
            sql2oGeneralNewsDao.addGeneralNews(generalnews);
            request.session().attribute("item", title);
            model.put("item", request.session().attribute("item"));
            return new ModelAndView(model,"generalNewsForm.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/generalNews",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("generalNews",sql2oGeneralNewsDao.getAll());
            model.put("users",sql2oUsersDao.getAll());
            return new ModelAndView(model,"generalNews.hbs");
        },new HandlebarsTemplateEngine());
///////////////////////////////////////////////////////////////////////////////////////////////////////////
        post("/generalNews/new/general","application/json",(req, res) -> {
            GeneralNews generalnews =gson.fromJson(req.body(),GeneralNews.class);
            sql2oGeneralNewsDao.addGeneralNews(generalnews);
            res.status(201);
            return gson.toJson(generalnews);
        });

        get("/generalNews/general","application/json",(req, res) -> {
            res.type("application/json");
            if(sql2oGeneralNewsDao.getAll().size()>0){
                return gson.toJson(sql2oGeneralNewsDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no news are currently listed in the database.\"}";
            }
        });

        get("/generalNews/:id", "application/json", (req, res) -> {
            int id=Integer.parseInt(req.params("id"));
            if(sql2oGeneralNewsDao.findById(id)==null){
                throw new ApiException(404, String.format("No news with the id: \"%s\" exists",
                        req.params("id")));
            }
            else {
                return gson.toJson(sql2oGeneralNewsDao.findById(id));
            }
        });
        /***********************************************************************************************/

        get("/create/departmentNews",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("users",sql2oUsersDao.getAll());
            model.put("departments",sql2oDepartmentsDao.getAll());
            return new ModelAndView(model,"departmentNewsForm.hbs");
        },new HandlebarsTemplateEngine());

        post("/create/departmentNews/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            String title=request.queryParams("title");
            String writtenBy=request.queryParams("writtenby");
            String content=request.queryParams("content");
            int users_id=Integer.parseInt(request.params("users"));
            int departments_id=Integer.parseInt(request.params("department"));
            DepartmentNews departmentnews=new DepartmentNews(title,writtenBy, content,users_id,departments_id);
            sql2oDepartmentNewsDao.addDepartmentNews(departmentnews);
            request.session().attribute("item", title);
            model.put("item", request.session().attribute("item"));
            return new ModelAndView(model,"departmentNewsForm.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/departmentNews",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("users",sql2oUsersDao.getAll());
            model.put("departments",sql2oDepartmentsDao.getAll());
            model.put("departmentNews",sql2oDepartmentNewsDao.getAll());
            return new ModelAndView(model,"departmentNews.hbs");
        },new HandlebarsTemplateEngine());

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
        post("/departmentNews/new/department","application/json",(req, res) -> {
            DepartmentNews department_news =gson.fromJson(req.body(),DepartmentNews.class);
            Departments departments=sql2oDepartmentsDao.findById(department_news.getDepartment_id());
            Users users=sql2oUsersDao.findById(department_news.getUsers_id());
            if(departments==null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists",
                        req.params("id")));
            }
            if(users==null){
                throw new ApiException(404, String.format("No users with the id: \"%s\" exists",
                        req.params("id")));
            }
            sql2oDepartmentNewsDao.addDepartmentNews(department_news);
            res.status(201);
            return gson.toJson(department_news);
        });

        get("/departmentNews/:id", "application/json", (req, res) -> {
            int id=Integer.parseInt(req.params("id"));
            if(sql2oDepartmentNewsDao.findById(id)==null){
                throw new ApiException(404, String.format("No news with the id: \"%s\" exists",
                        req.params("id")));
            }
            else {
                return gson.toJson(sql2oDepartmentNewsDao.findById(id));
            }
        });


      /* //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });*/

        /*after((req, res) ->{
            res.type("application/json");
        });*/
    }
}
