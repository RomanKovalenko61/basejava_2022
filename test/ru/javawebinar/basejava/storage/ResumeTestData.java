package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.DateListSection.DateSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Roman K");

        resume.setStringSection(PERSONAL, "personal");
        resume.setStringSection(OBJECTIVE, "objective");

        List<String> achievements = new ArrayList<>();
        achievements.add("startJava");
        achievements.add("baseJava");
        achievements.add("topJava");
        resume.setListSection(ACHIEVEMENT, achievements);

        List<String> qualifications = new ArrayList<>();
        qualifications.add("Java core");
        qualifications.add("Java collections");
        qualifications.add("Junit4");
        resume.setListSection(QUALIFICATION, qualifications);

        List<DateSection> educations = new ArrayList<>();
        educations.add(new DateSection(
                LocalDate.of(2022, 10, 1),
                LocalDate.of(2022, 12, 31),
                "basejava",
                "trainee",
                "studying java"));
        educations.add(new DateSection(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 4, 30),
                "topjava",
                "trainee",
                "studying advanced java"));
        resume.setDateListSection(EDUCATION, educations);

        List<DateSection> experiences = new ArrayList<>();
        experiences.add(new DateSection(
                LocalDate.of(2012, 10, 1),
                LocalDate.of(2022, 12, 31),
                "sber",
                "software engineer",
                "work"));
        experiences.add(new DateSection(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 30),
                "tinkoff",
                "programmer",
                "another work"));
        resume.setDateListSection(EXPERIENCE, experiences);

        System.out.println(resume);

        System.out.println("--------------------------------------------");
        System.out.println("formatted resume");
        System.out.println("-----------------BEGIN RESUME------------------------------");
        System.out.println("Resume uuid: " + resume.getUuid());
        System.out.println("Resume fullname: " + resume.getFullName());
        System.out.println("");
        printSection(resume, PERSONAL);
        System.out.println("");
        printSection(resume, OBJECTIVE);
        System.out.println("");
        printSection(resume, ACHIEVEMENT);
        System.out.println("");
        printSection(resume, QUALIFICATION);
        System.out.println("");
        printSection(resume, EDUCATION);
        System.out.println("");
        printSection(resume, EXPERIENCE);
        System.out.println("-----------------END RESUME------------------------------");
    }

    private static void printSection(Resume resume, SectionType type) {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                System.out.println(type.getTitle() + ": " + resume.getStringSection(type));
                break;
            case ACHIEVEMENT:
            case QUALIFICATION:
                System.out.println(type.getTitle());
                for (String str : resume.getListSection(type)) {
                    System.out.println(str);
                }
                break;
            case EDUCATION:
            case EXPERIENCE:
                System.out.println(type.getTitle());
                for (DateSection section : resume.getDateListSection(type)) {
                    System.out.println("*******************************");
                    System.out.println("Date from " + section.getWith());
                    System.out.println("Date to " + section.getTo());
                    System.out.println("Company:  " + section.getCompany());
                    System.out.println("Position:  " + section.getPosition());
                    System.out.println("Description:  " + section.getDescription());
                }
                break;
        }
    }
}
