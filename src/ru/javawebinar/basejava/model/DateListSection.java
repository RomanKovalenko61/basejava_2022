package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class DateListSection implements Section {

    List<DateSection> list;

    public DateListSection(List<DateSection> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
    }

    public List<DateSection> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "DateListSection{" +
                "list=" + list +
                '}';
    }

    public static class DateSection {
        LocalDate with;
        LocalDate to;
        String company;
        String position;
        String description;

        public DateSection(LocalDate with, LocalDate to, String company, String position, String description) {
            this.with = with;
            this.to = to;
            this.company = company;
            this.position = position;
            this.description = description;
        }

        public LocalDate getWith() {
            return with;
        }

        public LocalDate getTo() {
            return to;
        }

        public String getCompany() {
            return company;
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "DateSection{" +
                    "with=" + with +
                    ", to=" + to +
                    ", company='" + company + '\'' +
                    ", position='" + position + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}

