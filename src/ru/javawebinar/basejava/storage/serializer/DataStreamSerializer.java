package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializerStrategy {
    @Override
    public void doWrite(Resume r, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(sectionType.name());
                        dos.writeUTF(((StringSection) entry.getValue()).getString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        dos.writeUTF(sectionType.name());
                        List<String> list = ((ListSection) entry.getValue()).getList();
                        dos.writeInt(list.size());
                        for (String s : list) {
                            dos.writeUTF(s);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        dos.writeUTF(sectionType.name());
                        List<Organization> organizations = ((OrganizationSection) entry.getValue()).getList();
                        dos.writeInt(organizations.size());
                        for (Organization org : organizations) {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl());
                            List<Organization.Position> positions = org.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position pos : positions) {
                                dos.writeUTF(pos.getWith().toString());
                                dos.writeUTF(pos.getTo().toString());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(pos.getDescription());
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullname = dis.readUTF();
            Resume resume = new Resume(uuid, fullname);
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSection(sectionType, new StringSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        int listSectionsSize = dis.readInt();
                        List<String> stringList = new ArrayList<>(listSectionsSize);
                        for (int j = 0; j < listSectionsSize; j++) {
                            stringList.add(dis.readUTF());
                        }
                        resume.setSection(sectionType, new ListSection(stringList));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int listOrganizationSize = dis.readInt();
                        List<Organization> organizations = new ArrayList<>(listOrganizationSize);
                        for (int k = 0; k < listOrganizationSize; k++) {
                            Organization org = new Organization(dis.readUTF(), dis.readUTF());
                            int positionsSize = dis.readInt();
                            for (int m = 0; m < positionsSize; m++) {
                                LocalDate with = LocalDate.parse(dis.readUTF());
                                LocalDate to = LocalDate.parse(dis.readUTF());
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                if (description.equals("")) {
                                    description = null;
                                }
                                org.addPosition(new Organization.Position(with, to, title, description));
                            }
                            organizations.add(org);
                        }
                        resume.setSection(sectionType, new OrganizationSection(organizations));
                        break;
                }
            }
            return resume;
        }
    }
}