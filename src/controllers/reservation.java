package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class reservation {

    @FXML
    private JFXTextField searchforproducts;

    @FXML
    private JFXButton addbutton;

    @FXML
    private JFXTextField nametextreservation;

    @FXML
    private JFXTextField phonetextreservation;

    @FXML
    private JFXComboBox<?> table_combo;

    @FXML
    private JFXDatePicker date_picker;

    @FXML
    private JFXTimePicker time_picker;

    @FXML
    void addtotable(ActionEvent event) {

    }

    @FXML
    void edittable(ActionEvent event) {

    }

}
