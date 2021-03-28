package models;

public class DepartmentNews extends GeneralNews {
    private int department_id;
    public int employee_id;


    public DepartmentNews( String newsTitle, String writtenBy, String newsContent, int employee_id, int department_id) {
        super( newsTitle, writtenBy, newsContent, employee_id);
        this.employee_id = employee_id;
        this.department_id = department_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    @Override
    public int getEmployee_id() {
        return employee_id;
    }

    @Override
    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
