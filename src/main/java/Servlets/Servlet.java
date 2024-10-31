package Servlets;

import DAO.DAO;
import Model.Person;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/person")
public class Servlet extends HttpServlet {
    private DAO dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dao = new DAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Person> persons = dao.getAllPeople();
        request.setAttribute("people", persons);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
            return;
        }

        switch (action) {
            case "create":
                if (!handleCreate(request, response)) {
                    List<Person> persons = dao.getAllPeople();
                    request.setAttribute("people", persons);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                    dispatcher.forward(request, response);
                }
                break;
            case "delete":
                handleDelete(request, response);
                break;
            case "update":
                handleUpdate(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    private boolean handleCreate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String res_FIO = request.getParameter("fullName");
        String birthDate = request.getParameter("birthDate");

        if (res_FIO == null || birthDate == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "FIO or birth date cannot be null");
            return false;
        }

        String[] FIO = res_FIO.split(" ");
        if (FIO.length == 3) {
            Person person = new Person(FIO[0], FIO[1], FIO[2], birthDate);
            dao.createPerson(person);
            return true;
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ERR with FIO: must contain three parts");
            return false;
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                dao.deletePerson(id);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID parameter is missing");
        }
    }

    private boolean handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        String res_FIO = request.getParameter("fullName");
        String birthDate = request.getParameter("birthDate");

        if (idStr == null || res_FIO == null || birthDate == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID, FIO, or birth date cannot be null");
            return false;
        }

        String[] FIO = res_FIO.split(" ");
        if (FIO.length == 3) {
            try {
                int id = Integer.parseInt(idStr);
                Person person = new Person(FIO[0], FIO[1], FIO[2], birthDate);
                dao.updatePerson(id, person);
                return true;
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
                return false;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ERR with FIO: must contain three parts");
            return false;
        }
    }

}
