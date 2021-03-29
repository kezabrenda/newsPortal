package models;

public class DepartmentNews extends GeneralNews {
    private int department_id;
    public int users_id;


    public DepartmentNews( String newsTitle, String writtenBy, String newsContent, int users_id, int department_id) {
        super( newsTitle, writtenBy, newsContent, users_id);
        this.users_id = users_id;
        this.department_id = department_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    @Override
    public int getUsers_id() {
        return users_id;
    }

    @Override
    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }
}
