package com.springtask.controller;

import com.springtask.controller.domain.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam Integer id, Map<String, Object> model) {
        executeUpdate("DELETE FROM users WHERE id = " + id + ";");
        return greeting("", model);
    }

    @GetMapping
    public String greeting(@RequestParam(required = false, defaultValue = "") String email, Map<String, Object> model){
        List<UserInfo> users = new ArrayList<>();
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:memory");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, surname TEXT, birth TEXT, email TEXT, password TEXT);");
            ResultSet rs = statement.executeQuery("select * from users");
            while(rs.next()) {
                users.add(new UserInfo(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("birth"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }
        }
        catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        model.put("users", users.stream().filter(user -> user.getEmail().contains(email)).collect(Collectors.toList()));
        return "main-page";
    }

    @PostMapping("/create-user")
    public String newUser(@RequestParam String name,
                          @RequestParam String surname,
                          @RequestParam String birth,
                          @RequestParam String email,
                          @RequestParam String password,
                          Map<String, Object> model) throws NoSuchAlgorithmException {


        executeUpdate("INSERT INTO users(name, surname, birth, email, password) VALUES('" + name + "', '" + surname + "', '" + birth + "', '" + email + "', '" + getMd5(password) + "');");
        return greeting("", model);
    }

    @GetMapping("/drop-table")
    public String dropTable(Map<String, Object> model){
        executeUpdate("DROP TABLE IF EXISTS users");
        return greeting("", model);
    }

    public void executeUpdate(String query) {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:memory");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(query);

        }
        catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}