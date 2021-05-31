package com.psy.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuButton FindPort;

    @FXML
    private RadioButton Stop_bit_1;

    @FXML
    private RadioButton Stop_bit_2;

    @FXML
    private Text Time_answer;

    @FXML
    private Spinner<?> Adress;

    @FXML
    private MenuButton Speed;

    @FXML
    private MenuButton Parity;

    @FXML
    private Button Update;

    @FXML
    private Label Byte_interval;

    @FXML
    void initialize() {
        assert FindPort != null : "fx:id=\"FindPort\" was not injected: check your FXML file '1.fxml'.";
        assert Stop_bit_1 != null : "fx:id=\"Stop_bit_1\" was not injected: check your FXML file '1.fxml'.";
        assert Stop_bit_2 != null : "fx:id=\"Stop_bit_2\" was not injected: check your FXML file '1.fxml'.";
        assert Time_answer != null : "fx:id=\"Time_answer\" was not injected: check your FXML file '1.fxml'.";
        assert Adress != null : "fx:id=\"Adress\" was not injected: check your FXML file '1.fxml'.";
        assert Speed != null : "fx:id=\"Speed\" was not injected: check your FXML file '1.fxml'.";
        assert Parity != null : "fx:id=\"Parity\" was not injected: check your FXML file '1.fxml'.";
        assert Update != null : "fx:id=\"Update\" was not injected: check your FXML file '1.fxml'.";
        assert Byte_interval != null : "fx:id=\"Byte_interval\" was not injected: check your FXML file '1.fxml'.";

    }
}