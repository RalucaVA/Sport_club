package com.example.fxdemos;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Sport implements TeamSport, IndoorSport, Serializable {

    private int sportId;
    private String name;
    private String place;
    private String equipment;

    public Sport(String name, String place, String equipment, int sportId) {

        boolean validation = true;
        if (name==null || name.isEmpty() || !(name instanceof String)) {
            throw new IllegalArgumentException("Name can not be null or empty and should be a string");
        }

        if (place==null || place.isEmpty() || !(place instanceof String))
            throw new IllegalArgumentException("Name can not be null or empty and should be a string");

        if  (equipment==null|| equipment.isEmpty() || !(equipment instanceof String))
            throw new IllegalArgumentException("Equipment can not be null or empty and should be a string");

        this.name=name;
        this.place = place;
        this.equipment = equipment;
        this.sportId = sportId;
    }

    public int getSportId() {return sportId;}


    public String getName() {return name;}

    public String getPlace() {
        return place;
    }

    public String getEquipment() { return equipment; }

    @Override
    public boolean isIndoorSport() {
        return false;
    }

    @Override
    public boolean isTeamSport() {
        return false;
    }

    public static Sport getSportByIdFromDB(int sportId)
    {
        String jdbcUrl = "jdbc:sqlserver://localhost\\DESKTOP-ETVSTBH\\SQLEXPRESS:1434;database=SportClub;encrypt=true;trustServerCertificate=true;username=ralu;password=ralu";

        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Connection connection = DriverManager.getConnection(jdbcUrl);
            Statement statement = connection.createStatement();
            String query = "select * from [Sport] Where Id = " + sportId;
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return new Sport(resultSet.getString("SportName"),
                        resultSet.getString("Place"),
                        resultSet.getString("Equipment"),
                        resultSet.getInt("SportId"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Sport getSportByNameFromDB(String sportName){
        String jdbcUrl = "jdbc:sqlserver://localhost\\DESKTOP-ETVSTBH\\SQLEXPRESS:1434;database=SportClub;encrypt=true;trustServerCertificate=true;username=ralu;password=ralu";

        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Connection connection = DriverManager.getConnection(jdbcUrl);
            Statement statement = connection.createStatement();
            String query = "select * from [Sport] Where SportName = '" + sportName + "'";
            ResultSet resultSet = statement.executeQuery(query);


            if (resultSet.next()) {
                return new Sport(resultSet.getString("SportName"),
                        resultSet.getString("Place"),
                        resultSet.getString("Equipment"),
                        resultSet.getInt("SportId"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ArrayList<Sport> readSportsFromDB()
    {
        ArrayList<Sport> sportList = new ArrayList<Sport>();
        String jdbcUrl = "jdbc:sqlserver://localhost\\DESKTOP-ETVSTBH\\SQLEXPRESS:1434;database=SportClub;encrypt=true;trustServerCertificate=true;username=ralu;password=ralu";

        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

            Connection connection = DriverManager.getConnection(jdbcUrl);
            Statement statement = connection.createStatement();
            String query = "select * from [Sport] ";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Sport s = new Sport(resultSet.getString("SportName"),
                        resultSet.getString("Place"),
                        resultSet.getString("Equipment"),
                        resultSet.getInt("SportId"));
                sportList.add(s);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sportList;
    }

}
