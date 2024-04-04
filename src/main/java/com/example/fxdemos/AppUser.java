package com.example.fxdemos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AppUser {
    private String name;
    private String password;
    private int role;

    public AppUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public boolean isExists() {
        String jdbcUrl = "jdbc:sqlserver://localhost\\DESKTOP-ETVSTBH\\SQLEXPRESS:1434;database=SportClub;encrypt=true;trustServerCertificate=true;username=ralu;password=ralu";
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Connection connection = DriverManager.getConnection(jdbcUrl);
            Statement statement = connection.createStatement();
            String query = "select * from [ApplicationUser] Where UserName = '" + name + "' AND UserPasswd = '" + password +"'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                role = resultSet.getInt("Role");
                return true;
            }
            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
