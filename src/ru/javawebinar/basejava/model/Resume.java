package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.model.DateListSection.DateSection;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private String fullName;

    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullname must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setStringSection(SectionType type, String str) {
        sections.put(type, new StringSection(str));
    }

    public String getStringSection(SectionType type) {
        return ((StringSection) sections.get(type)).getString();
    }

    public void setListSection(SectionType type, List<String> list) {
        sections.put(type, new ListSection(list));
    }

    public List<String> getListSection(SectionType type) {
        return ((ListSection) sections.get(type)).getList();
    }

    public void setDateListSection(SectionType type, List<DateSection> list) {
        sections.put(type, new DateListSection(list));
    }

    public List<DateSection> getDateListSection(SectionType type) {
        return ((DateListSection) sections.get(type)).getList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName) && sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sections);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", sections=" + sections +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}
