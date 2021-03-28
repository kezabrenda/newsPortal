package models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class GeneralNews {
    private static int id;
    public String newsTitle;
    public String writtenBy;
    public String newsContent;
    public int employee_id;
    private Date date= new Date();
    private Timestamp createdat;

    public GeneralNews(String newsTitle, String writtenBy, String newsContent, int employee_id) {
        this.newsTitle = newsTitle;
        this.writtenBy = writtenBy;
        this.newsContent = newsContent;
        this.employee_id = employee_id;
        this.createdat = new Timestamp(date.getTime());
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        GeneralNews.id = id;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralNews that = (GeneralNews) o;
        return id == that.id &&
                employee_id == that.employee_id &&
                Objects.equals(newsTitle, that.newsTitle) &&
                Objects.equals(writtenBy, that.writtenBy) &&
                Objects.equals(newsContent, that.newsContent) &&
                Objects.equals(date, that.date) &&
                Objects.equals(createdat, that.createdat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsTitle, writtenBy, newsContent, employee_id, date,createdat);
    }
}
