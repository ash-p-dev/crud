package com.example;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = getAllUsers(); 
        request.setAttribute("users", users); 
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    // Method to fetch all users from the database
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/userdb";
        String username = "root";
        String password = "ash@123";

        String query = "SELECT * FROM users";

        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setCountry(rs.getString("country"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework instead of printStackTrace
        }
        return users;
    }

    // Method to fetch a user by ID from the database
    public User getUserById(int id) {
        User user = new User();
        String url = "jdbc:mysql://localhost:3306/userdb";
        String username = "root";
        String password = "ash@123";

        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setCountry(rs.getString("country"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework instead of printStackTrace
        }
        return user;
    }
}
