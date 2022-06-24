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
import javafx.scene.input.MouseEvent;
import sample.databaseconnect;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class category implements Initializable {
Connection con=null;
PreparedStatement pst=null;
ResultSet rs=null;

        @FXML
        private TableView<sample.category> categorytable;


        @FXML
        private TableColumn<sample.category, Integer> idcol;

        @FXML
        private TableColumn<sample.category, String> namecol;

        @FXML
        private ContextMenu delete;

        @FXML
        private JFXTextField categorysearch;

        @FXML
        private JFXButton addcategory;

        @FXML
        private JFXButton editname;

        @FXML
        private Label Mainmenu;

        ObservableList<sample.category>categorylist= FXCollections.observableArrayList();

        @FXML
        void addtable(ActionEvent event) {
insertintoproducts();
        }

        @FXML
        void category_filter(ActionEvent event) {

        }

        @FXML
        void edittable(ActionEvent event) {
update();
        }

        @FXML
        void go_to_menu(MouseEvent event) {

        }
    public void filter() {
        FilteredList<sample.category>filteredList=new FilteredList<sample.category>(categorylist, b->true);

        categorysearch.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredList.setPredicate(category -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercasefilter = newValue.toLowerCase();
                if (category.getName().toLowerCase().indexOf(lowercasefilter) != -1) {
                    return true;
                } else if (String.valueOf(category.getId()).toLowerCase().indexOf(lowercasefilter) != -1) {

                    return true;
                } else
                    return false;
            });
        });


        SortedList<sample.category> sortedList = new SortedList<sample.category>(filteredList);
        sortedList.comparatorProperty().bind(categorytable.comparatorProperty());
        categorytable.setItems(sortedList);
    }

    @FXML
    void delete(ActionEvent event) {
        sample.category a = categorytable.getSelectionModel().getSelectedItem();
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

            }

        }


    }
    @FXML
    private JFXTextField categorytext;


    public  void insertintoproducts() {
        con = databaseconnect.getconnect();
        String name = categorytext.getText();
        try {
            String query = "insert into category(name)values(?)";
            pst = con.prepareStatement(query);
            pst.setString(1, name);
            int x = pst.executeUpdate();
            if (x == 1) {

    categorytext.setText("");
            }
            refreshtable();
        } catch (Exception ex) {
            throw  new RuntimeException(ex);
        }
    }
int index=-1;
    @FXML
    void getindex(MouseEvent event) {

        index = categorytable.getSelectionModel().getSelectedIndex();
        categorytext.setText(namecol.getCellData(index));
        addcategory.setDisable(true);

    }  public void update(){

        con=databaseconnect.getconnect();
        try{
            String sql="update category set name=?  where id=?";
            int y=categorytable.getSelectionModel().getSelectedIndex();
            pst=con.prepareStatement(sql);
            pst.setString(1,categorytext.getText());
            pst.setInt(2,idcol.getCellData(y));
            int x= pst.executeUpdate();
            if(x==1){
                categorytext.setText("");
                addcategory.setDisable(false);
                refreshtable();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public boolean deletetablefromrow(sample.category m) {
        try {
            Connection con = databaseconnect.getconnect();
            String query = "delete  from category where id = ? ";
            pst = con.prepareStatement(query);
            pst.setString(1, String.valueOf(m.getId()));
            pst.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public void refreshtable() {


        categorylist.clear();
        con = databaseconnect.getconnect();
        String query = " select * from category";
        try {
            pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                categorylist.add(new sample.category((rs.getInt("id")),rs.getString("name")));
                categorytable.setItems(categorylist);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
loaddate();
refreshtable();
filter();


    }
    public void loaddate() {
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));

    }
}




