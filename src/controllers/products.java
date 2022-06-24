package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.databaseconnect;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class products implements Initializable {

    @FXML
    private TableView<sample.products> productstable;

    @FXML
    private TableColumn<sample.products, Integer> idcol;

    @FXML
    private TableColumn<sample.products, String> namecol;

    @FXML
    private TableColumn<sample.products, Integer> pricecol;

    @FXML
    private TableColumn<sample.products, String> categorycol;

    @FXML
    private JFXTextField filterproducts;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;


    @FXML
    private JFXButton addbutton;

    @FXML
    private JFXButton editbutton;

    @FXML
    private JFXTextField nametextproducts;

    @FXML
    private JFXTextField pricetextproducts;

    @FXML
    private JFXComboBox<String> category_combo;
    ObservableList<sample.products> productslist = FXCollections.observableArrayList();
ObservableList<String>categorylist=FXCollections.observableArrayList();
    @FXML
    void addtable(ActionEvent event) {

    }
    public void update() {

        con = databaseconnect.getconnect();
        try {
            String sql = "update products set name=?,price=?,catid=?  where idproduct=?";
            int y = productstable.getSelectionModel().getSelectedIndex();
            pst = con.prepareStatement(sql);
            pst.setString(1, nametextproducts.getText());
            pst.setInt(2, Integer.valueOf(pricetextproducts.getText()));
            pst.setString(3,String.valueOf(category_combo.getSelectionModel().getSelectedIndex()));
              pst.setInt(4,idcol.getCellData(y));
            int x = pst.executeUpdate();
            if (x == 1) {
                nametextproducts.setText("");
                addbutton.setDisable(false);
                refreshtable();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }


    @FXML
    void edittable(ActionEvent event) {
update();
    }
    public  void getcombodata(){

              con=databaseconnect.getconnect();
              String query="select name from category";
              try {
                  pst = con.prepareStatement(query);

                  ResultSet rs = pst.executeQuery();
                  while (rs.next()) {
                      categorylist.add(rs.getString("name"));
                      category_combo.setItems(categorylist);
                  }

              } catch (SQLException throwables) {
                  throwables.printStackTrace();
              }
    }




    @FXML
    void getindex(MouseEvent event) {


        index = productstable.getSelectionModel().getSelectedIndex();
        nametextproducts.setText(namecol.getCellData(index));
        pricetextproducts.setText(String.valueOf(pricecol.getCellData(index)));
        category_combo.getSelectionModel().select(index);
        addbutton.setDisable(true);
    }

    public void refreshtable() {


        productslist.clear();
        con = databaseconnect.getconnect();
        String query = " select products.idproduct,products.name,products.price,category.name FROM products,category where category.id=products.catid";
        try {
            Statement st=con.createStatement();
            pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                productslist.add(new sample.products((rs.getInt("idproduct")), rs.getString("name"), rs.getInt("price"),
                        rs.getString("category.name")));
productstable.setItems(productslist);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
int index=-1;


        @Override
    public void initialize(URL location, ResourceBundle resources) {

        loaddate();
        refreshtable();
        getcombodata();

    }


    public void loaddate() {
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
        categorycol.setCellValueFactory(new PropertyValueFactory<>("category"));

    }

}





