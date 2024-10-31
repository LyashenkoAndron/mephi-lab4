package DAO;

import Model.Person;

import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    private final String URL = "jdbc:postgresql://localhost:5432/newDB";
    private final String USER = "postgres";
    private final String PASWD = "jangazy";


    public void createPerson(Person person) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASWD);
            if (con != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            }
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO people (name, surname, lastname, dateofbirth) VALUES ('"
                    + person.getName() + "', '"
                    + person.getSurname() + "', '"
                    + person.getLastName() + "', '"
                    + person.getDateOfBirth() + "')";
            stmt.executeUpdate(sql);
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePerson(int id, Person person) {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASWD);
            Statement stmt = con.createStatement();
            String sql = "UPDATE people SET name = '"
                    + person.getName() + "', surname = '"
                    + person.getSurname() + "', lastname = '"
                    + person.getLastName() + "', dateofbirth = '"
                    + person.getDateOfBirth() + "' WHERE id = " + id;
            stmt.executeUpdate(sql);
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(int id) {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASWD);
            Statement stmt = con.createStatement();
            String sql = "DELETE FROM people WHERE id = " + id;
            stmt.executeUpdate(sql);
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Person> getAllPeople() {
        try {
            List<Person> people = new ArrayList<>();

            Connection con = DriverManager.getConnection(URL, USER, PASWD);
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM people";
            ResultSet rs = stmt.executeQuery(sql);
            int columns = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                Person person = new Person(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("lastname"),
                        rs.getString("dateofbirth"));
                people.add(person);
            }

            rs.close();
            con.close();
            return people;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
