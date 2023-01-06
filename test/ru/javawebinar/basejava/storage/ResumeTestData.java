package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Roman K");

        resume.setSection(PERSONAL, new StringSection("personal"));
        resume.setSection(OBJECTIVE, new StringSection("objective"));
        resume.setContact(ContactType.GITHUB, "RomanKovalenko61");
        resume.setContact(ContactType.PHONE, "8(800)535-35-35");

        List<String> achievements = new ArrayList<>();
        achievements.add("startJava");
        achievements.add("baseJava");
        achievements.add("topJava");
        resume.setSection(ACHIEVEMENT, new ListSection(achievements));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("Java core");
        qualifications.add("Java collections");
        qualifications.add("Junit4");
        resume.setSection(QUALIFICATION, new ListSection(qualifications));

        List<Organization> educations = new ArrayList<>();
        educations.add(new Organization(
                "javaops",
                "javaops.ru",
                LocalDate.of(2022, 10, 1),
                LocalDate.of(2022, 12, 31),
                "trainee",
                "studying java"));
        educations.add(new Organization(
                "topjava",
                "topjava.ru",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 4, 30),
                "trainee",
                "studying advanced java"));
        resume.setSection(EDUCATION, new OrganizationSection(educations));

        List<Organization> experiences = new ArrayList<>();
        experiences.add(new Organization(
                "sber",
                "sber.ru",
                LocalDate.of(2012, 10, 1),
                LocalDate.of(2022, 12, 31),
                "software engineer",
                "work"));
        experiences.add(new Organization(
                "tinkoff",
                "tinkoff.ru",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 30),
                "programmer",
                "another work"));
        resume.setSection(EXPERIENCE, new OrganizationSection(experiences));

        System.out.println(resume);

        System.out.println("--------------------------------------------");
        System.out.println("formatted resume");
        System.out.println("-----------------BEGIN RESUME------------------------------");
        System.out.println("");
        System.out.println("Resume uuid: " + resume.getUuid());
        System.out.println("Resume fullname: " + resume.getFullName());
        System.out.println("-----------------CONTACTS------------------------------");
        printContacts(resume);
        System.out.println("-----------------SECTIONS------------------------------");
        printSection(resume, PERSONAL);
        printSection(resume, OBJECTIVE);
        printSection(resume, ACHIEVEMENT);
        printSection(resume, QUALIFICATION);
        printSection(resume, EDUCATION);
        printSection(resume, EXPERIENCE);
        System.out.println("-----------------END RESUME------------------------------");
    }

    private static void printSection(Resume resume, SectionType type) {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                System.out.println(type.getTitle() + ": " + ((StringSection) resume.getSection(type)).getString());
                break;
            case ACHIEVEMENT:
            case QUALIFICATION:
                System.out.println(type.getTitle() + ": ");
                for (String str : ((ListSection) resume.getSection(type)).getList()) {
                    System.out.println(str);
                }
                break;
            case EDUCATION:
            case EXPERIENCE:
                System.out.println(type.getTitle() + ": ");
                System.out.println("*******************************");
                for (Organization section : ((OrganizationSection) resume.getSection(type)).getList()) {

                    System.out.println("Company:  " + section.getHomePage());
                    System.out.println("Date from " + section.getWith());
                    System.out.println("Date to " + section.getTo());
                    System.out.println("Title:  " + section.getTitle());
                    System.out.println("Description:  " + section.getDescription());
                    System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
                }
                System.out.println("*******************************");
                break;
        }
    }

    private static void printContacts(Resume resume) {
        for (ContactType contact : ContactType.values()) {
            String str = resume.getContact(contact);
            if (str == null) {
                continue;
            }
            System.out.println(contact.getTitle() + str);
        }
    }
}
