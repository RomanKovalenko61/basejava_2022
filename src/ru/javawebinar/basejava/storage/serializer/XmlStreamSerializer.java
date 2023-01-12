package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements SerializerStrategy {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, OrganizationSection.class, Organization.Position.class,
                Link.class, StringSection.class, ListSection.class
        );
    }

    @Override
    public void doWrite(Resume r, OutputStream outputStream) throws IOException {
        try (Writer w = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (Reader r = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}