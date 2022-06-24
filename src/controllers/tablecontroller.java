package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sample.databaseconnect;
import sample.tables;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class tablecontroller implements Initializable {


    @FXML
    private TableView<sample.tables> table_tableview;

    @FXML
    private TableColumn<sample.tables, Integer> idcol;

    @FXML
    private TableColumn<sample.tables, String> tablenamecol;

    @FXML
    private TableColumn<sample.tables, Integer> tablecapacitycol;

    @FXML
    private ContextMenu delete;


    @FXML
    private JFXTextField tableanametext;

    @FXML
    private JFXTextField searchfortable;

    @FXML
    private JFXButton addtable;

    @FXML
    private JFXButton edittable;

    @FXML
    private JFXTextField tablecapacity;

    @FXML
    void addtotable(ActionEvent event) {
        insertintoproducts();
    }
    @FXML
    private JFXTextField filteredtext;


    ObservableList<sample.tables> tablelist = FXCollections.observableArrayList();


    public void filter() {
        FilteredList<sample.tables>filteredList=new FilteredList<tables>(tablelist, b->true);

        filteredtext.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredList.setPredicate(tables -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercasefilter = newValue.toLowerCase();
                if (tables.getName().toLowerCase().indexOf(lowercasefilter) != -1) {
                    return true;
                } else if (String.valueOf(tables.getCapacity()).toLowerCase().indexOf(lowercasefilter) != -1) {

                    return true;
                } else
                    return false;
            });
        });

        SortedList<sample.tables> sortedList = new SortedList<tables>(filteredList);
sortedList.comparatorProperty().bind(table_tableview.comparatorProperty());
table_tableview.setItems(sortedList);
    }
    @FXML
    void delete(ActionEvent event) {
        sample.tables a = table_tableview.getSelectionModel().getSelectedItem();
        if (a == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("are you sure you want to delete  " + a.getName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                deletetablefromrow(a);
                refreshtable();
                clear();
            }

        }

    }



PreparedStatement pst=null;
Connection con=null;
    public void refreshtable() {

        tablelist.clear();
         con = databaseconnect.getconnect();
        String query = " select * from tablecafe";
        try {
             pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                tablelist.add(new tables((rs.getInt("number")),rs.getString("tablename"),
                        rs.getInt("capacity")));
                table_tableview.setItems(tablelist);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public  void clear(){

        tablecapacity.setText("");
        tableanametext.setText("");
    }
    @FXML
    void keycode(KeyEvent event) {

        if(event.getCode()==KeyCode.TAB){
            tablecapacity.requestFocus();
        }

    }


    public  void insertintoproducts() {
         con = databaseconnect.getconnect();
        String name = tableanametext.getText();
        int capacity = Integer.valueOf(tablecapacity.getText());
        try {
            String query = "insert into tablecafe(capacity,tablename)values(?,?)";
            pst = con.prepareStatement(query);
            pst.setInt(1,Integer.valueOf( capacity));
            pst.setString(2, name);
            int x = pst.executeUpdate();
            if (x == 1) {
clear();
            }
            refreshtable();


        } catch (Exception ex) {
            throw  new RuntimeException(ex);
        }
    }


    @FXML
    void edittablename(ActionEvent event) {
        sample.tables a = table_tableview.getSelectionModel().getSelectedItem();

update();
    }
    @FXML
    void filtertable(ActionEvent event) {

    }

    @FXML
    void filteroftable(ActionEvent event) {

    }

    @FXML
    void go_to_mainmenu(MouseEvent event) {

    }
    int index=-1;

    @FXML
    void getindex(MouseEvent event) {

        index=table_tableview.getSelectionModel().getSelectedIndex();
        tableanametext.setText(tablenamecol.getCellData(index));
        tablecapacity.setText(tablecapacitycol.getCellData(index).toString());
        addtable.setDisable(true);

    }

    public void update(){

        con=databaseconnect.getconnect();
        try{
            String sql="update tablecafe set tablename=? ,capacity=? where number=?";
tables m= table_tableview.getSelectionModel().getSelectedItem();
int y=table_tableview.getSelectionModel().getSelectedIndex();
            pst=con.prepareStatement(sql);
            pst.setString(1,tableanametext.getText());
            pst.setInt(2,Integer.valueOf(tablecapacity.getText()));
            pst.setInt(3,idcol.getCellData(y));
           int x= pst.executeUpdate();
           if(x==1){
               clear();
               addtable.setDisable(false);
               refreshtable();
           }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public boolean deletetablefromrow(sample.tables m) {
        try {
            Connection con = databaseconnect.getconnect();
            String query = "delete  from tablecafe where number = ? ";
            pst = con.prepareStatement(query);
            pst.setString(1, String.valueOf(m.getId()));
            pst.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

refreshtable();
loaddate();
filter();
    }

    public void loaddate() {
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablenamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        tablecapacitycol.setCellValueFactory(new PropertyValueFactory<>("capacity"));

    }

    }


