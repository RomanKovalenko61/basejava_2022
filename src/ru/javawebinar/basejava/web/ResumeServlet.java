package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        List<Resume> list = storage.getAllSorted();
        response.getWriter().write("<table>");
        response.getWriter().write("<tr>");
        response.getWriter().write("<th>uuid</th>");
        response.getWriter().write("<th>fullName</th>");
        response.getWriter().write("</tr>");
        for (Resume r : list) {
            response.getWriter().write("<tr>");
            response.getWriter().write("<td>" + r.getUuid() + "</td>");
            response.getWriter().write("<td>" + r.getFullName() + "</td>");
            response.getWriter().write("<tr>");
        }
        response.getWriter().write("</table>");
    }
}