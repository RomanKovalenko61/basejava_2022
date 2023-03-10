package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File filePath = new File(".\\.gitignore");

        try {
            System.out.println(filePath.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File(".\\src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        for (String name : Objects.requireNonNull(dir.list())) {
            System.out.println(name);
        }

        // until Java 7
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            System.out.println(fileInputStream.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // auto-closable
        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File f = new File("D:\\Projects\\basejava_2022\\src\\ru\\javawebinar");
        printListFiles(f, "");
    }


    private static void printListFiles(File file, String offset) {
        File[] files = file.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    System.out.println(offset + "File: " + f.getName());
                } else {
                    System.out.println(offset + "Directory: " + f.getName());
                    printListFiles(f, offset + "   ");
                }
            }
        }
    }
}

