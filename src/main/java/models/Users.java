package models;

import java.util.Objects;

public class Users {
    private static int id;
    private String name;
    private String position;
    private int department_id;

    public Users(String name, String position){
        this.name = name;
        this.position = position;
        this.id =id;
        this.department_id = department_id;
    }
    public Users(String name, String position, int department_id){
        this.name = name;
        this.position = position;
        this.id =id;
        this.department_id = department_id;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Users.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id &&
                department_id == users.department_id &&
                Objects.equals(name, users.name) &&
                Objects.equals(position, users.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position, department_id);
    }
}
