package com.example.fxdemos;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
public class Group implements Comparable<Group>, Serializable {

    private int groupId;
    private String name;
    private double price;
    private int hour;
    private Sport sport;
    private ArrayList<Child> child;


    public Group(String name, double price, int hour, Sport sport, ArrayList<Child> children) {
        this.name = name;
        this.price = price;
        this.hour = hour;
        this.sport = sport;
        this.child = children;
    }

    public Group() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public void setChild(ArrayList<Child> child) {
        this.child = child;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() { return groupId; }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getHour() {
        return hour;
    }

    public Sport getSport() {
        return sport;
    }

    public ArrayList<Child> getChild() {
        return child;
    }

    public double totalCost() {
        return this.child.size() * price;
    }

    @Override
    public int compareTo(Group otherGroup) {

        return Double.compare(this.price, otherGroup.getPrice());
    }

    public static ArrayList<Group> readGroupsFromDB() {
        ArrayList<Group> groupList = new ArrayList<Group>();
        String jdbcUrl = "jdbc:sqlserver://localhost\\DESKTOP-ETVSTBH\\SQLEXPRESS:1434;database=SportClub;encrypt=true;trustServerCertificate=true;username=ralu;password=ralu";

        try {

            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Connection connection = DriverManager.getConnection(jdbcUrl);
            Statement statement = connection.createStatement();

            String query = "select g.Id, g.Name , g.Hour, g.Price, s.SportName, s.Place, s.Equipment, s.SportId from [Group] g INNER JOIN [Sport] s ON g.SportId = s.SportId";
            ResultSet resultSet = statement.executeQuery(query);

            // Process the result set
            while (resultSet.next()) {
                Group g = new Group();
                g.setGroupId(resultSet.getInt("Id"));
                g.setName(resultSet.getString("Name"));
                g.setHour(resultSet.getInt("Hour"));
                g.setPrice(resultSet.getDouble("Price"));
                g.setSport(new Sport(resultSet.getString("SportName"),
                        resultSet.getString("Place"),
                        resultSet.getString("Equipment"),
                        resultSet.getInt("SportId")));
                g.setChild(readGroupChildrenListFromDB(g.getGroupId()));
                groupList.add(g);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupList;
    }

    public static boolean insertGroup(String groupName, double price, int hour, int sportId){
        String jdbcUrl = "jdbc:sqlserver://localhost\\DESKTOP-ETVSTBH\\SQLEXPRESS:1434;database=SportClub;encrypt=true;trustServerCertificate=true;username=ralu;password=ralu";
        try {
                DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
                Connection connection = DriverManager.getConnection(jdbcUrl);
                Statement statement = connection.createStatement();
                String query="INSERT INTO [dbo].[Group]([Name],[Price],[Hour], [SportId]) VALUES('" + groupName +
                        "'," + price + "," + hour + "," + sportId +")";
               statement.executeUpdate(query);
               connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Child> readGroupChildrenListFromDB(int groupId) {
        ArrayList<Child> childrenList = new ArrayList<Child>();
        String jdbcUrl = "jdbc:sqlserver://localhost\\DESKTOP-ETVSTBH\\SQLEXPRESS:1434;database=SportClub;encrypt=true;trustServerCertificate=true;username=ralu;password=ralu";

        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Connection connection = DriverManager.getConnection(jdbcUrl);

            Statement statement = connection.createStatement();
            String query = "select c.* from [ChildList] cl inner join dbo.Child c on cl.ChildId = c.Id where GroupId = " + groupId;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Child c = new Child(
                        resultSet.getString("Name"),
                        resultSet.getInt("Age"),
                        resultSet.getDouble("Weight"),
                        resultSet.getDouble("Height"));
                childrenList.add(c);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return childrenList;
    }

    public static int convertStringToInt(String value) {
        try {
            int number = Integer.valueOf(value);
            return number;

        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double convertStringToDouble(String value) {
        try {
            double number = Double.valueOf(value);
            return number;

        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
