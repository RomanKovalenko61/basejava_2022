package ru.javawebinar.basejava.model;

import java.util.Objects;

public class StringSection implements Section {
    private String string;

    public StringSection(String string) {
        Objects.requireNonNull(string, "string must not be null");
        this.string = string;
    }

    public String getString() {
        return string;
    }

    @Override
    public String toString() {
        return "StringSection{" +
                "string='" + string + '\'' +
                '}';
    }
}
