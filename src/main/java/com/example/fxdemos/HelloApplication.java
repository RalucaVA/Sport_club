package com.example.fxdemos;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Button ok = new Button("Login");
        Label user = new Label("Username:");
        Label pass = new Label("Password:");
        Font font = Font.font("Arial", FontWeight.BOLD, 15);
        user.setFont(font);
        pass.setFont(font);
        ok.setFont(font);
        TextField userInput = new TextField("username");
        PasswordField passInput = new PasswordField();
        GridPane pane = new GridPane();
        pane.setVgap(15); //vertical gap
        pane.setHgap(15); //horizontal gap
        pane.add(user, 1, 1);
        pane.add(userInput, 2, 1);
        pane.add(pass, 1, 2);
        pane.add(passInput, 2, 2);
        pane.add(ok, 2, 4);


        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AppUser appUser = new AppUser(userInput.getText(), passInput.getText());
                if (appUser.isExists()){
                    Stage newStage = new Stage();
                    Label title = new Label("Sport Club");
                    title.setTextFill(Color.BLUE);
                    title.setFont(font);
                    Label userRole = new Label("userRole: " + (appUser.getRole() == 1 ? "admin" : "user"));
                    Button showGroups = new Button("Print group list");
                    Button showChildren = new Button("Print participants");
                    Button insertGroup = new Button("Add new group");
                    Button insertChild = new Button("Add new child");
                    GridPane mainPain = new GridPane();
                    showGroups.setOnAction(e -> handleShowGroupsButtonClick(mainPain));
                    showChildren.setOnAction(e -> handleShowChildrenButtonClick(mainPain));
                    insertGroup.setOnAction(e -> handleInsertGroupButtonClick(mainPain));
                    insertChild.setOnAction(e -> handleInsertChildButtonClick(mainPain));
                    mainPain.setVgap(15); //vertical gap
                    mainPain.setHgap(15); //horizontal gap
                    mainPain.add(title, 1, 1);
                    mainPain.add(userRole, 13, 1);
                    mainPain.add(showGroups, 1, 2);
                    mainPain.add(showChildren, 1, 3);
                    Scene scene2;
                    if (appUser.getRole() == 1) {
                        mainPain.add(insertGroup, 1, 4);
                        mainPain.add(insertChild, 1, 5);
                    }
                    if (appUser.getRole() == 1)
                         scene2 = new Scene(mainPain, 880, 700);
                    else
                        scene2 = new Scene(mainPain, 800, 500);
                    newStage.setScene(scene2);
                    newStage.setTitle("Sport Club");
                    stage.close();
                    newStage.show();
                }
                else {
                    Label errLogin = new Label("User doesn't exist!");
                    errLogin.setTextFill(Color.RED);
                    pane.add(errLogin, 2, 3);
                }

            }
        });

        Scene scene = new Scene(pane, 280, 170);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    private void handleShowGroupsButtonClick(GridPane pane) {
        clearContent(pane, 1, 8);
        ArrayList<Group> groupList = Group.readGroupsFromDB();
        GridPane root = new GridPane();
        //root.setPadding(new Insets(10,10,10,10));
        root.setHgap(30);
        root.setVgap(30);
        int colCnt = 0, rowCnt = 1;
        root.add(new Label("No"), 0, 0);
        root.add(new Label("Name"), 1, 0);
        root.add(new Label("Price[Ron]"), 2, 0);
        root.add(new Label("Hour"), 3, 0);
        root.add(new Label("Sport"), 4, 0);
        root.add(new Label("Participants"), 5, 0);
        root.add(new Label("Total Cost[Ron]"), 6, 0);
        for (int i=0; i<groupList.size(); i++) {
            root.add(new Label(Integer.toString(i+1)), colCnt++, rowCnt);
            root.add(new Label(groupList.get(i).getName()), colCnt++, rowCnt);
            root.add(new Label(Double.toString(groupList.get(i).getPrice())), colCnt++, rowCnt);
            root.add(new Label(Integer.toString(groupList.get(i).getHour())), colCnt++, rowCnt);
            root.add(new Label(groupList.get(i).getSport().getName()), colCnt++, rowCnt);
            root.add(new Label(Integer.toString(groupList.get(i).getChild().size())), colCnt++, rowCnt);
            root.add(new Label(Double.toString(groupList.get(i).totalCost())), colCnt++, rowCnt);
            rowCnt++;
            colCnt=0;
        }
        pane.add(root, 1, 8);
    }

    private void handleShowChildrenButtonClick(GridPane pane) {
        clearContent(pane, 1, 8);
        ArrayList<Group> groupList = Group.readGroupsFromDB();
        GridPane root = new GridPane();
        root.setHgap(30);
        root.setVgap(30);
        Label groupLabel = new Label("Group :");
        Label errorGroupLabel = new Label();
        errorGroupLabel.setTextFill(Color.RED);
        ObservableList<String> options  = FXCollections.observableArrayList();
        for (Group g: groupList) {
            options.add(g.getName());
        }
        final ComboBox comboBox = new ComboBox(options);
        root.add(groupLabel, 0, 0);
        root.add(comboBox, 1, 0);
        Button ok = new Button("Get participants");
        root.add(ok,3,0);
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int error = 0;
                String selectedGroup = (String) comboBox.getValue();
                if (selectedGroup == null ) {
                    errorGroupLabel.setText("Select a sport");
                    root.add(errorGroupLabel, 1, 1);
                    error = 1;
                }
                if (error == 0) {
                    Group g = groupList.stream()
                            .filter(group -> selectedGroup.equals(group.getName()))
                            .findAny()
                            .orElse(null);
                    if ( g != null) {
                        ArrayList<Child> childrenList = Group.readGroupChildrenListFromDB(g.getGroupId());
                        int colCnt = 0, rowCnt = 3;
                        root.add(new Label("No"), 0, 2);
                        root.add(new Label("Name"), 1, 2);
                        root.add(new Label("Age"), 2, 2);
                        root.add(new Label("Weight[kg]"), 3, 2);
                        root.add(new Label("Height[cm]"), 4, 2);
                        root.add(new Label("BMI"), 5, 2);
                        for (int i = 0; i < childrenList.size(); i++) {
                            root.add(new Label(Integer.toString(i + 1)), colCnt++, rowCnt);
                            root.add(new Label(childrenList.get(i).getName()), colCnt++, rowCnt);
                            root.add(new Label(Integer.toString(childrenList.get(i).getAge())), colCnt++, rowCnt);
                            root.add(new Label(Double.toString(childrenList.get(i).getWeight())), colCnt++, rowCnt);
                            root.add(new Label(Double.toString(childrenList.get(i).getHeight())), colCnt++, rowCnt);
                            root.add(new Label(Double.toString(childrenList.get(i).calculateBMI())), colCnt++, rowCnt);
                            rowCnt++;
                            colCnt = 0;
                        }
                    }
                }
            }
        });

        pane.add(root, 1, 8);
    }

    private void handleInsertGroupButtonClick(GridPane pane){
        clearContent(pane, 1, 8);
        ArrayList<Sport> sportList = Sport.readSportsFromDB();
        GridPane root = new GridPane();
        root.setHgap(30);
        root.setVgap(30);
        Button ok = new Button("Create Group");
        Label name = new Label("Group Name:");
        Label errorNameLabel = new Label();
        errorNameLabel.setTextFill(Color.RED);
        TextField nameTxt = new TextField();
        Label price = new Label("Group Price:");
        Label errorPriceLabel = new Label();
        errorPriceLabel.setTextFill(Color.RED);
        TextField priceTxt = new TextField();
        Label hour = new Label("Starting Hour:");
        Label errorHourLabel = new Label();
        errorHourLabel.setTextFill(Color.RED);
        TextField hourTxt = new TextField();
        Label sport = new Label("Sport:");
        Label errorSportLabel = new Label();
        errorSportLabel.setTextFill(Color.RED);
        ObservableList<String> options  = FXCollections.observableArrayList();
        for (Sport s: sportList) {
            options.add(s.getName());
        }
        final ComboBox comboBox = new ComboBox(options);

        root.add(name, 0, 0);
        root.add(nameTxt, 1, 0);
        root.add(price, 0, 1);
        root.add(priceTxt, 1, 1);
        root.add(hour, 0, 2);
        root.add(hourTxt, 1, 2);
        root.add(sport, 0, 3);
        root.add(comboBox, 1, 3);
        root.add(ok, 1, 4);
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int error = 0;
                if (nameTxt.getText() == null || nameTxt.getText().trim().length() == 0) {
                    errorNameLabel.setText("Group name is mandatory");
                    root.add(errorNameLabel, 2, 0);
                    error = 1;
                }
                if (priceTxt.getText() == null || priceTxt.getText().trim().length() == 0) {
                    errorPriceLabel.setText("Group price is mandatory");
                    root.add(errorPriceLabel, 2, 1);
                    error = 1;
                }
                if (hourTxt.getText() == null || hourTxt.getText().trim().length() == 0) {
                    errorHourLabel.setText("Starting hour is mandatory");
                    root.add(errorHourLabel, 2, 2);
                    error = 1;
                }
                String selectedSport = (String) comboBox.getValue();
                if (selectedSport == null ) {
                    errorSportLabel.setText("Select a sport");
                    root.add(errorSportLabel, 2, 3);
                    error = 1;
                }
                double priceGroup = Group.convertStringToDouble(priceTxt.getText());
                if (priceGroup == 0) {
                    errorPriceLabel.setText("Group price has incorrect value");
                    root.add(errorPriceLabel, 2, 1);
                    error = 1;
                }
                int hourGroup = Group.convertStringToInt(hourTxt.getText());
                if (hourGroup < 10  || hourGroup > 22) {
                    errorHourLabel.setText("starting hour: between 10 and 22");
                    root.add(errorHourLabel, 2, 2);
                    error = 1;
                }
                if (error == 0) {
                    Sport s = Sport.getSportByNameFromDB(selectedSport);
                    if ( s != null) {
                        Group.insertGroup(nameTxt.getText(), priceGroup, hourGroup, s.getSportId());
                        Label successMessage = new Label("Group created successfully");
                        successMessage.setTextFill(Color.GREEN);
                        root.add(successMessage, 2,4);
                    }
                }
            }
        });
        pane.add(root, 1, 8);
    }


    private void handleInsertChildButtonClick(GridPane pane){
        clearContent(pane, 1, 8);
        ArrayList<Group> groupList = Group.readGroupsFromDB();
        ArrayList<Child> childrenList = new ArrayList<Child>();
        GridPane root2 = new GridPane();
        root2.setHgap(30);
        root2.setVgap(30);
        Label childIndex =  new Label("Child no " + Integer.toString(childrenList.size() +1));
        Button ok = new Button("Insert all children");
        root2.add(childIndex, 0, 0);
        Button next = new Button("Next Child");
        Label group = new Label("Group :");
        Label errorGroupLabel = new Label();
        errorGroupLabel.setTextFill(Color.RED);
        ObservableList<String> options  = FXCollections.observableArrayList();
        for (Group g: groupList) {
            options.add(g.getName());
        }
        final ComboBox comboBox = new ComboBox(options);
        Label name = new Label("Name:");
        Label errorNameLabel = new Label();
        errorNameLabel.setTextFill(Color.RED);
        TextField nameTxt = new TextField();
        Label age = new Label("Age:");
        Label errorAgeLabel = new Label();
        errorAgeLabel.setTextFill(Color.RED);
        TextField ageTxt = new TextField();
        Label weight = new Label("Weight[kg]:");
        Label errorWeightLabel = new Label();
        errorWeightLabel.setTextFill(Color.RED);
        TextField weightTxt = new TextField();
        Label height = new Label("Height[cm]:");
        Label errorHeightLabel = new Label();
        errorHeightLabel.setTextFill(Color.RED);
        TextField heightTxt = new TextField();
        root2.add(group, 0, 1);
        root2.add(comboBox, 1, 1);
        root2.add(name, 0, 2);
        root2.add(nameTxt, 1, 2);
        root2.add(age, 0, 3);
        root2.add(ageTxt, 1, 3);
        root2.add(weight, 0, 4);
        root2.add(weightTxt, 1, 4);
        root2.add(height, 0, 5);
        root2.add(heightTxt, 1, 5);
        root2.add(next, 1, 6);
        root2.add(ok, 2, 6);
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int error = 0;
                String selectedGroup = (String) comboBox.getValue();
                if (selectedGroup == null ) {
                    errorGroupLabel.setText("Select a sport");
                    root2.add(errorGroupLabel, 2, 1);
                    error = 1;
                }
                if (nameTxt.getText() == null || nameTxt.getText().trim().length() == 0) {
                    errorNameLabel.setText("Child name is mandatory");
                    root2.add(errorNameLabel, 2, 2);
                    error = 1;
                }
                int ageValue = Group.convertStringToInt(ageTxt.getText());
                if (ageValue <= 0) {
                    errorAgeLabel.setText("Age has incorrect value");
                    root2.add(errorAgeLabel, 2, 3);
                    error = 1;
                }
                double weightValue = Group.convertStringToDouble(weightTxt.getText());
                if (weightValue <= 0) {
                    errorWeightLabel.setText("Weight has incorrect value");
                    root2.add(errorWeightLabel, 2, 4);
                    error = 1;
                }
                double heightValue = Group.convertStringToDouble(heightTxt.getText());
                if (heightValue <= 0) {
                    errorHeightLabel.setText("Group price is mandatory");
                    root2.add(errorHeightLabel, 2, 5);
                    error = 1;
                }
                if (error == 0)
                {
                    childrenList.add(new Child(nameTxt.getText(), ageValue, weightValue, heightValue));
                    childIndex.setText("Child no " + Integer.toString(childrenList.size() + 1));
                    nameTxt.setText("");
                    ageTxt.setText("");
                    weightTxt.setText("");
                    heightTxt.setText("");
                }
            }
        });

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int error = 0;
                String selectedGroup = (String) comboBox.getValue();
                if (selectedGroup == null ) {
                    errorGroupLabel.setText("Select a sport");
                    root2.add(errorGroupLabel, 2, 1);
                    error = 1;
                }
                if (nameTxt.getText() == null || nameTxt.getText().trim().length() == 0) {
                    errorNameLabel.setText("Child name is mandatory");
                    root2.add(errorNameLabel, 2, 2);
                    error = 1;
                }
                int ageValue = Group.convertStringToInt(ageTxt.getText());
                if (ageValue <= 0) {
                    errorAgeLabel.setText("Age has incorrect value");
                    root2.add(errorAgeLabel, 2, 3);
                    error = 1;
                }
                double weightValue = Group.convertStringToDouble(weightTxt.getText());
                if (weightValue <= 0) {
                    errorWeightLabel.setText("Weight has incorrect value");
                    root2.add(errorWeightLabel, 2, 4);
                    error = 1;
                }
                double heightValue = Group.convertStringToDouble(heightTxt.getText());
                if (heightValue <= 0) {
                    errorHeightLabel.setText("Group price is mandatory");
                    root2.add(errorHeightLabel, 2, 5);
                    error = 1;
                }

                if (error == 0) {
                    childrenList.add(new Child(nameTxt.getText(), ageValue, weightValue, heightValue));
                    Group g = groupList.stream()
                            .filter(group -> selectedGroup.equals(group.getName()))
                            .findAny()
                            .orElse(null);
                    if ( g != null) {
                        Child.insertChilds(childrenList, g.getGroupId());
                        Label successMessage = new Label(childrenList.size() + " child/children created successfully");
                        successMessage.setTextFill(Color.GREEN);
                        root2.add(successMessage, 1, 7);
                    }
                }
            }
        });
        pane.add(root2, 1, 8);
    }


    private void clearContent(GridPane gridPane, int columnIndex, int rowIndex) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == columnIndex && GridPane.getRowIndex(node) == rowIndex) {
                gridPane.getChildren().remove(node);
                break;
            }
        }
    }
    public static void main(String[] args) {

        launch();
    }
}

