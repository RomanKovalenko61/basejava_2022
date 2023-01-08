package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = getResume("UUID", "Roman Kovalenko");
        System.out.println(resume);

        System.out.println("--------------------------------------------");
        System.out.println("formatted resume");
        System.out.println("-----------------BEGIN RESUME------------------------------");
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

    public static Resume getResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

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
        Organization edu1 = new Organization("javaops", "javaops.ru");
        edu1.addNote(new Organization.Position(
                LocalDate.of(2022, 10, 1),
                LocalDate.of(2022, 12, 31),
                "trainee",
                "studying java"));
        Organization edu2 = new Organization("javaops", "javaops.ru");
        edu2.addNote(new Organization.Position(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 4, 30),
                "trainee",
                "studying advanced java"));
        educations.add(edu1);
        educations.add(edu2);
        resume.setSection(EDUCATION, new OrganizationSection(educations));

        List<Organization> experiences = new ArrayList<>();
        Organization exp1 = new Organization("almaz", "almaz.ru");
        exp1.addNote(new Organization.Position(
                DateUtil.of(2012, Month.JANUARY),
                LocalDate.of(2013, 12, 31),
                "junior engineer",
                "engineer work"));
        exp1.addNote(new Organization.Position(
                DateUtil.of(2014, Month.JANUARY),
                LocalDate.of(2022, 10, 1),
                "senior engineer",
                "another work"));
        Organization exp2 = new Organization("sber", "sber.ru");
        exp2.addNote(new Organization.Position(
                LocalDate.of(2022, 10, 1),
                LocalDate.of(2022, 12, 31),
                "software engineer",
                null));
        Organization exp3 = new Organization("tinkoff", "tinkoff.ru");
        exp3.addNote(new Organization.Position(
                DateUtil.of(2023, Month.JANUARY),
                DateUtil.NOW,
                "programmer",
                null));
        experiences.add(exp1);
        experiences.add(exp2);
        experiences.add(exp3);
        resume.setSection(EXPERIENCE, new OrganizationSection(experiences));
        return resume;
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
                for (Organization section : ((OrganizationSection) resume.getSection(type)).getList()) {

                    System.out.println("Company:  " + section.getHomePage());
                    for (Organization.Position note : section.getNotes()) {
                        System.out.println("Date from " + note.getWith());
                        System.out.println("Date to " + note.getTo());
                        System.out.println("Title:  " + note.getTitle());
                        System.out.println("Description:  " + note.getDescription());
                    }
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
