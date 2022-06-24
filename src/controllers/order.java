package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.protocol.Resultset;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.databaseconnect;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class order implements Initializable {


    @FXML
    private TextField totalpricebottom;

    @FXML
    private TextField paymenttext;

    @FXML
    private Button button1;

    @FXML
    private Button button3;

    @FXML
    private Button button2;

    @FXML
    private Button button4;

    @FXML
    private Button button6;

    @FXML
    private Button button5;

    @FXML
    private Button button7;

    @FXML
    private Button button10;

    @FXML
    private Button button8;
    ObservableList<sample.order> orderlist = FXCollections.observableArrayList();
    @FXML
    private Button button9;
    @FXML
    private TableView<sample.order> ordertable;

    @FXML
    private TableColumn<sample.order, String> namecol;

    @FXML
    private TableColumn<sample.order, Integer> priceperunitcol;

    @FXML
    private TableColumn<sample.order, Integer> quantittycol;

    @FXML
    private TableColumn<sample.order, Integer> pricecol;
    @FXML
    private JFXTextField productnametext;

    @FXML
    private JFXButton addbutton;

    @FXML
    private Spinner<Integer> spinner;

    @FXML
    private JFXTextField pricenametext;

    @FXML
    private TextField totalpricetext;

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    @FXML
    private Button cancel;

    public void updatetable(){



    }
    public void getrowcount(){



    }

    public String catgeory(int i) {

        con = sample.databaseconnect.getconnect();

        String query = " select name from category where id='" + i + "'";
        try {
            pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            String x = "";
            while (rs.next()) {
                x = (rs.getString("name"));
            }
            return x;
        } catch (SQLException throwables) {
            String z = "no categort ";
            throwables.printStackTrace();
            return z;
        }
    }
    @FXML
    private Button back;
    @FXML
    void goback(ActionEvent event) throws IOException {
Stage stage=(Stage) back.getScene().getWindow();
        //primaryStage.setTitle("Hello World");
        stage.close();
    }
    @FXML
    private JFXTextField searchtext;

    @FXML
    void searchforproduct(ActionEvent event) {

  insertintable();
  refreshinsert();

    }

    @FXML
    private Button table1;

    public void refreshinsert(){

totalpricetext.setText("");
productnametext.setText("");
insertintable();


    }
    @FXML
    private TextField discounttext;


    @FXML
    void onlynumeric(KeyEvent event) {
        discounttext.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    discounttext.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        changetotalprice();


    }


    public void clearall(){
        orderlist.clear();
        paymenttext.setText("");
        totalpricebottom.setText("");
     discounttext.setText("");
        clear();

    }
    @FXML
    void table1method(MouseEvent event) {

        table1.setStyle("-fx-background-color:green");
    }



    public void insertintable(){

        Connection con = sample.databaseconnect.getconnect();
        try {
            String name = searchtext.getText();
            String query = "select name,price from products where products.name like '" + name + "%" + "'";
            Statement atm = con.createStatement();
            Statement stm=con.createStatement();
            rs = stm.executeQuery(query);

            while (rs.next()) {

                productnametext.setText(String.valueOf(rs.getString("name")));
                pricenametext.setText(String.valueOf(rs.getInt("price")));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private ComboBox<String> combo_order;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private AnchorPane anchlorpane;
    public void load(String name) {

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../fxml_files/" + name + ".fxml"));
            anchlorpane.getChildren().add(root);
            scrollpane.getContent();
            //borderpane.setCenter(root);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        int totalbottom=0;

    private void totalprice() {
        totalpricetext.setEditable(false);
        spinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                int qty = Integer.parseInt(spinner.getValue().toString());
                int price = Integer.parseInt(pricenametext.getText());
                int totalamount = qty * price;
                totalpricetext.setText(String.valueOf(totalamount));
                totalbottom=Integer.valueOf(totalpricetext.getText());
                totalpricebottom.setText(String.valueOf(totalbottom));

            }
        });

    }


    void changetotalprice() {
        int x=Integer.valueOf(discounttext.getText())/100;
        int y=x*Integer.valueOf(totalpricetext.getText());
        totalpricebottom.setText(y+"");

    }
    public void clear(){

        totalpricetext.setText("");
        productnametext.setText("");
        pricenametext.setText("");
        searchtext.setText("");


    }
    int sum=0;
    public void getcount() {

    }
    @FXML
    void addtotable(ActionEvent event) {

        orderlist.add(new sample.order(productnametext.getText(),Integer.parseInt(pricenametext.getText()),
                Integer.valueOf(totalpricetext.getText()),spinner.getValue()));
ordertable.setItems(orderlist);
clear();

    }

    @FXML
    void cancelorder(ActionEvent event) {

        clearall();
        table1.setStyle("-fx-background-color: #B0E0E6");
    }

    @FXML
    void printorder(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

totalpricebottom.setText(String.valueOf(totalbottom));
       // totalpricebottom.setText(z+"");
        button1.setText(catgeory(1));
        button2.setText(catgeory(2));
        button3.setText(catgeory(3));
        button4.setText(catgeory(9));
        button5.setText(catgeory(5));
        button6.setText(catgeory(6));
        button7.setText(catgeory(7));
        button8.setText(catgeory(8));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50);
valueFactory.setValue(1);
spinner.setValueFactory(valueFactory);
totalprice();
combo_order.getItems().setAll("Dine in","deleviry","take away");
//insertintable();
loaddate();

    } public void loaddate() {

        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceperunitcol.setCellValueFactory(new PropertyValueFactory<>("price"));
        pricecol.setCellValueFactory(new PropertyValueFactory<>("totalprice"));
        quantittycol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    }


    public void goto1(ActionEvent actionEvent) {
load("products1");

        }

    public void goto3(ActionEvent actionEvent) {
    }

    public void go2(ActionEvent actionEvent) {
    }

    public void goto4(ActionEvent actionEvent) {
    }

    public void goto6(ActionEvent actionEvent) {
    }

    public void goto5(ActionEvent actionEvent) {
    }

    public void goto7(ActionEvent actionEvent) {
    }

    public void goto8(ActionEvent actionEvent) {
    }
}
