package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private VBox pane;

    @FXML
    private JFXTextField loginname;

    @FXML
    private JFXPasswordField passwordform;

    @FXML
    private FontAwesomeIconView checkpicture;


    @FXML
    private JFXButton loginbutton;


    @FXML
    void checkpassword(KeyEvent event) {

        Connection con = sample.databaseconnect.getconnect();

        try {
            Statement st = con.createStatement();
            String query = "select * from account where id=1";       //+passwordform.getText()+"'";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                if (passwordform.getText().contains(rs.getString("passwordd"))) {

                    checkpicture.setVisible(true);
                }
                else
                    checkpicture.setVisible(false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

        Stage stage;

    public  void clear(){

        passwordform.setText("");
        loginname.setText("");
    }
    String x="";
    String y="";
    @FXML
    void login(ActionEvent event) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("../fxml_files/Dashboard.fxml"));

        Connection con= sample.databaseconnect.getconnect();
        try {
            Statement st = con.createStatement();
            String query="select * from account where id=1";       //+passwordform.getText()+"'";
            ResultSet rs=st.executeQuery(query);
            Alert alert=new Alert(Alert.AlertType.NONE);

            while (rs.next()){
            y=rs.getString("username");
                 x=rs.getString("passwordd");
                 if(loginname.getText().contains(y)&&passwordform.getText().contains(x)) {
                     stage = (Stage) loginbutton.getScene().getWindow();
                     stage.setScene(new Scene(root));
                 }else {
                     clear();
                     alert.setHeaderText("please check the password or username");
                     alert.setAlertType(Alert.AlertType.ERROR);
                     alert.show();
                 }


    }


        }catch (Exception ex){
            ex.printStackTrace();
        }


    }

    @FXML
    void close(MouseEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passwordform.setStyle("-fx-text-inner-color: orange;");

        loginname.setStyle("-fx-text-inner-color: orange;");

        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);-fx-background-radius: 50;");
        //pane.setStyle("-fx-background-radius:50px");
        JFXDepthManager.setDepth(pane,4);
    }
}



