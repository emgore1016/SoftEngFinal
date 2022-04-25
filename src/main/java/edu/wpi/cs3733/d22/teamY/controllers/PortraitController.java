package edu.wpi.cs3733.d22.teamY.controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PortraitController {

    @FXML private ImageView backButton;

    @FXML private Label nameText;
    @FXML private Label roleText;

    @FXML private ImageView portraitImage;

    @FXML private JFXTextArea quoteText;

    //Function to put the right text and image stuff in the right places in the portrait template
    @FXML
    void rewriteInfo(String[] name) throws FileNotFoundException {
        nameText.setText(name[0]);
        roleText.setText(name[1]);
        quoteText.setText(name[2]);
        portraitImage = (new ImageView(new Image(new FileInputStream(name[3]))));
    }


    @FXML
    private void backButton() {
        Stage stage;
        stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

  @FXML private Rectangle rectangle;

  // Function to put the right text and image stuff in the right places in the portrait template
  @FXML
  void rewriteInfo(String[] name) throws FileNotFoundException {
    SceneUtil.removeOpacity(rectangle);

    nameText.setText(name[0]);
    roleText.setText(name[1]);
    quoteText.setText(name[2]);
    // portraitImage = new ImageView(new Image(new FileInputStream(name[3])));
  }

}
