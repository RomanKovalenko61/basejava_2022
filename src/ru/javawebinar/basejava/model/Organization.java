package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;

    private final List<Position> notes = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String url) {
        this.homePage = new Link(name, url);
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getNotes() {
        return notes;
    }

    public void addNote(Position note) {
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate with;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate to;
        private String title;
        private String description;

        public Position() {
        }

        public Position(LocalDate with, LocalDate to, String title, String description) {
            Objects.requireNonNull(with, "date with must not be null");
            Objects.requireNonNull(to, "date to must not be null");
            Objects.requireNonNull(title, "title to must not be null");
            this.with = with;
            this.to = to;
            this.title = title;
            this.description = description;
        }

        public LocalDate getWith() {
            return with;
        }

        public LocalDate getTo() {
            return to;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description == null ? "" : description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position note = (Position) o;
            return with.equals(note.with) && to.equals(note.to) && title.equals(note.title) && Objects.equals(description, note.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(with, to, title);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "with=" + with +
                    ", to=" + to +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}