package com.example.fxdemos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Child implements Comparable<Child>, Serializable {

    private int childId;
    private String name;
    private int age;
    private double weight;
    private double height;
    private int groupId;

    public Child()
    {

    }

    public Child( String name, int age, double weight, double height) {

        if (name==null || name.isEmpty() || !(name instanceof String)) {
            throw new IllegalArgumentException("Name can not be null or empty and should be a string");
        }

        if (age<0)
        {
            throw new IllegalArgumentException("Age should be greater than 0");
        }
        if(age>13) {
            throw new IllegalArgumentException("Doesn't exist a group for this age");
        }
        if(weight<0 || height<0) {
            throw new IllegalArgumentException("Weigth and height should be greater than 0");
        }


        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height= height;
    }

    public int getChildId() { return childId;}

    public int getGroupdId() { return groupId;}

    public int getAge() {
        return age;
    }


    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() { return height; }

    public void setChildId(int childId) { this.childId = childId; }

    public void setGroupId(int groupId) { this.groupId = groupId; }

    public double calculateBMI() {
        return weight/(height/100*height/100);
    }

    public static void insertChilds(ArrayList<Child> childrenList, int groupId) {
        for (Child c: childrenList) {
            c.setGroupId(groupId);
            Thread t = new Thread() {
                @Override
                public void run() {
                    insertChildInDB(c);
                }
            };
            t.start();
        }
    }

    public static void insertChildInDB(Child child) {
        String jdbcUrl = "jdbc:sqlserver://localhost\\DESKTOP-ETVSTBH\\SQLEXPRESS:1434;database=SportClub;encrypt=true;trustServerCertificate=true;username=ralu;password=ralu";
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Connection connection = DriverManager.getConnection(jdbcUrl);
            Statement statement = connection.createStatement();
            String query = "INSERT INTO [dbo].[Child]([Name],[Age],[Weight], [Height]) VALUES('" + child.getName() +
                    "'," + child.getAge() + "," + child.getWeight() + "," + child.getHeight() +")";
            statement.executeUpdate(query);
            query = "SELECT Id FROM [dbo].[Child] WHERE Name = '" + child.getName() + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                child.setChildId(resultSet.getInt("Id"));
            }
            query = "INSERT INTO [dbo].[ChildList] ([GroupId],[ChildId]) VALUES(" + child.getGroupdId() + "," + child.getChildId() + ")";
            statement.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(Child otherChild) {

        int compareHeight= Double.compare(this.height, otherChild.getHeight());

        if(compareHeight != 0)
            return compareHeight;
        else{
            return Double.compare(this.weight, otherChild.getWeight());
        }
    }
}
