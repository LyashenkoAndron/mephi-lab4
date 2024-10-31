import DAO.DAO;
import Model.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DAO dao = new DAO();
        List<Person> people = dao.getAllPeople();
        for (Person person : people) {
            System.out.println(person.toString());
        }
    }
}