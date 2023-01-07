package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private final Link homePage;

    private final List<OrganizationNote> notes = new ArrayList<>();

    public Organization(String name, String url) {
        this.homePage = new Link(name, url);
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<OrganizationNote> getNotes() {
        return notes;
    }

    public void addNote(OrganizationNote note) {
        notes.add(note);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return homePage.equals(that.homePage) && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, notes);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", notes=" + notes +
                '}';
    }
}