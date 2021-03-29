package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeneralNewsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNewsTitle() {
        GeneralNews testGeneralNews = setupGeneralNews();
        assertEquals("Work Life", testGeneralNews.getNewsTitle());
    }

    private GeneralNews setupGeneralNews() {
        return new GeneralNews("Work Life", "Brenda","Enjoyyyyy",1);
    }

    @Test
    public void setNewsTitle() {
        GeneralNews testGeneralNews = setupGeneralNews();
        testGeneralNews.setNewsTitle("Work Life");
        assertNotEquals("Good", testGeneralNews.getNewsTitle());
    }

    @Test
    public void getWrittenBy() {
        GeneralNews testGeneralNews = setupGeneralNews();
        assertEquals("Brenda", testGeneralNews.getWrittenBy());
    }

    @Test
    public void setWrittenBy() {
        GeneralNews testGeneralNews = setupGeneralNews();
        testGeneralNews.setWrittenBy("Brenda");
        assertNotEquals("Keza", testGeneralNews.getWrittenBy());
    }

    @Test
    public void getNewsContent() {
        GeneralNews testGeneralNews = setupGeneralNews();
        assertEquals("Enjoyyyyy", testGeneralNews.getNewsContent());
    }

    @Test
    public void setNewsContent() {
        GeneralNews testGeneralNews = setupGeneralNews();
        testGeneralNews.setNewsContent("Enjoyyyyy");
        assertNotEquals("Or don't", testGeneralNews.getNewsContent());
    }

    @Test
    public void getUsers_id() {
        GeneralNews testGeneralNews = setupGeneralNews();
        assertEquals(1, testGeneralNews.getUsers_id());
    }

    @Test
    public void setUsers_id() {
        GeneralNews testGeneralNews = setupGeneralNews();
        testGeneralNews.setUsers_id(6);
        assertNotEquals(1, testGeneralNews.getUsers_id());
    }

}