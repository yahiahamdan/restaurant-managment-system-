package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @FXML
    private VBox slider;

    private Pane pane;
    @FXML
    private JFXButton categorybutton;

    @FXML
    private JFXButton change_passwordbutton;

    @FXML
    private JFXButton reportsbutton;

    @FXML
    private JFXButton table_button;

    @FXML
    private FontAwesomeIconView menu;

    @FXML
    private Label menuback;

    @FXML
    private Circle c8;

    @FXML
    private JFXButton orderbutton;

    @FXML
    private Circle c3;

    @FXML
    private JFXButton workersbutton;

    @FXML
    private Circle c2;

    @FXML
    private JFXButton statbutton;

    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Circle c5;

    @FXML
    private JFXButton reservationbutton;


    @FXML
    private BorderPane borderpane;
    @FXML
    private Circle c4;

    @FXML
    private JFXButton productsbutton;

    @FXML
    private Circle c6;
    @FXML
    private AnchorPane tablesanchorpane;
    @FXML
    private JFXButton tablesbutton;

    @FXML

    private void load(String name) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../fxml_files/"+name +".fxml"));
            anchorpane.getChildren().add(root);
            borderpane.setCenter(root);


        }
catch(Exception ex){

ex.printStackTrace();
            }

        }




    @FXML
    void bookingr_reserv(ActionEvent event) {
load("reservation");
    }

    @FXML
    void go_to_category(ActionEvent event) {
load("category");
    }

    @FXML
    void go_to_order(ActionEvent event) throws IOException {
//load("order");
Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml_files/order.fxml"));
        //primaryStage.setTitle("Hello World");
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    private JFXButton order;
    @FXML
    void go_to_orderr(ActionEvent event) throws IOException {

        Stage stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml_files/order.fxml"));
        stage.setScene(new Scene(root));


        stage.show();
    }


    @FXML
    void go_to_stat(ActionEvent event) {
load("statistics");
    }

    @FXML
    void go_to_workers(ActionEvent event) {

        load("worker");
    }
    @FXML
    void gotoproducts(ActionEvent event) {
load("products");
    }
Stage stage;
    @FXML
    void gototables(ActionEvent event)  {

        load("tables");
    }

    @FXML
    void exit(MouseEvent event) {
        Platform.exit();

    }

    @FXML
    void go_to_product(ActionEvent event) {
 load("products");
    }

    @FXML
    void go_to_rerservation(ActionEvent event) {
load("reservation");
    }



    @FXML
    void go_to_storage(ActionEvent event) {
load("storage");
    }
    @FXML
    void gotousers(ActionEvent event) {

        load("users");
    }
    @FXML
    void go_to_tables(ActionEvent event) {
load("tables");
    }


    @FXML
    void gobackward(MouseEvent event) {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToX(0);
        slide.play();
        slider.setTranslateX(0);
        slide.setOnFinished((ActionEvent e)->{

            menu.setVisible(true);
            menuback.setVisible(false);

        });
    }

    @FXML
    void goforword(MouseEvent event) {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToX(-300);
        slide.play();
        slider.setTranslateX(50);
        slide.setOnFinished((ActionEvent e)->{

            menu.setVisible(false);
            menuback.setVisible(true);

        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        c3.setFill(new ImagePattern(new Image("photos/workers.jpg")));
        c4.setFill(new ImagePattern(new Image("photos/products.jpg")));
        c5.setFill(new ImagePattern(new Image("photos/reservation2.jpg")));
        c6.setFill(new ImagePattern(new Image("photos/tables.jpg")));
        c8.setFill(new ImagePattern(new Image("photos/img.png")));
c2.setFill(new ImagePattern(new Image("photos/statistics.jpg")));
    }
}