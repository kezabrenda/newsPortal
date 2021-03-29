package dao;

import org.sql2o.Sql2o;

public class Sql2oDepartmentNewsDao implements DepartmentsNewsDao{
    private final Sql2o sql2o;
    public Sql2oDepartmentNewsDao(Sql2o sql2o) { this.sql2o = sql2o; }
}
