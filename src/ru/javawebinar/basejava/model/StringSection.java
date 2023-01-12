package ru.javawebinar.basejava.model;

import java.util.Objects;

public class StringSection extends Section {
    private static final long serialVersionUID = 1L;

    private String string;

    public StringSection() {
    }

    public StringSection(String string) {
        Objects.requireNonNull(string, "string sections must not be null");
        this.string = string;
    }

    public String getString() {
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringSection that = (StringSection) o;
        return string.equals(that.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string);
    }

    @Override
    public String toString() {
        return "StringSection{" +
                "string='" + string + '\'' +
                '}';
    }
}
