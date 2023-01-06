package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final LocalDate with;
    private final LocalDate to;
    private final String title;
    private final String description;

    public Organization(String name, String url, LocalDate with, LocalDate to, String title, String description) {
        Objects.requireNonNull(with, "date with must not be null");
        Objects.requireNonNull(to, "date to must not be null");
        Objects.requireNonNull(title, "title to must not be null");
        this.homePage = new Link(name, url);
        this.with = with;
        this.to = to;
        this.title = title;
        this.description = description;
    }

    public Link getHomePage() {
        return homePage;
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
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return homePage.equals(that.homePage)
                && with.equals(that.with)
                && to.equals(that.to)
                && title.equals(that.title)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, with, to, title, description);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", with=" + with +
                ", to=" + to +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}