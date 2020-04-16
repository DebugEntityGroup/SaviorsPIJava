/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Ahmed
 */
public class AlertController {

    @FXML
    private Button ok;

    
     @FXML
    public void close() {
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    
}
