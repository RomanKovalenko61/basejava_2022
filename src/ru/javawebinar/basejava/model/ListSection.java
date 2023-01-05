package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection implements Section {
    private List<String> list;

    public ListSection(List<String> list) {
        Objects.requireNonNull(list, "List must not be null");
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "list=" + list +
                '}';
    }
}
