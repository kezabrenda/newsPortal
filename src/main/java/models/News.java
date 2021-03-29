package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class News {
    public String newsTitle;
    public String newsContent;
    public String writtenBy;
    public int users_id;
    public int department_id;
    public int id;
    public String type;
    private long createdat;
    private String formattedCreatedAt;

    private final String TYPE_OF_NEWS="general";

    public News(String newsTitle,String writtenBy, String newsContent,int users_id) {
        this.writtenBy = writtenBy;
        this.newsTitle = newsTitle;
        this.newsContent = newsContent;
        this.type = TYPE_OF_NEWS;
        this.users_id = users_id;
        this.department_id=0;
        this.createdat = System.currentTimeMillis();
        setFormattedCreatedAt();

    }
    public News(String newsTitle,String writtenBy, String newsContent,int users_id,int department_id) {
        this.writtenBy = writtenBy;
        this.newsTitle = newsTitle;
        this.newsContent = newsContent;
        this.type = "department";
        this.users_id = users_id;
        this.department_id=department_id;
        this.createdat = System.currentTimeMillis();
        setFormattedCreatedAt();

    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public long getCreatedat() {
        return createdat;
    }

    public void setCreatedat() {
        this.createdat = System.currentTimeMillis();

    }

    public String getFormattedCreatedAt(){
        Date date = new Date(createdat);
        String datePatternToUse = "MM/dd/yyyy @ K:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        return sdf.format(date);
    }

    public void setFormattedCreatedAt(){
        Date date = new Date(this.createdat);
        String datePatternToUse = "MM/dd/yyyy @ K:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        this.formattedCreatedAt = sdf.format(date);
    }
}
