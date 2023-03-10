package ru.javawebinar.basejava.util;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.StringSection;

import static ru.javawebinar.basejava.storage.ResumeTestData.RESUME_5;

public class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_5);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_5, resume);
    }

    @Test
    public void write() {
        Section section1 = new StringSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }
}