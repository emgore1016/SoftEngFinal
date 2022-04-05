package edu.wpi.cs3733.d22.teamY.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class sideBarController {
  @FXML AnchorPane mainPane;

  @FXML
  void loadMainPage() throws IOException {
    SceneLoading.loadScene("views/mainPage.fxml");
  }

  @FXML
  void loadViewMap() throws IOException {
    SceneLoading.loadScene("views/mapPage.fxml");
  }

  @FXML
  void loadCreateServiceRequest() throws IOException {
    SceneLoading.loadScene("views/requestMenu.fxml");
  }

  @FXML
  void loadViewServiceRequests() throws IOException {
    SceneLoading.loadScene("views/activeServiceRequest.fxml");
  }

  @FXML
  void loadLaundryRequest() throws IOException {
    SceneLoading.loadScene("views/requestTypes/laundryRequest.fxml");
  }

  @FXML
  void loadMedicalEquipment() throws IOException {
    SceneLoading.loadScene("views/requestTypes/medicalEquipmentRequest.fxml");
  }

  @FXML
  void loadMealDelivery() throws IOException {
    SceneLoading.loadScene("views/requestTypes/mealRequest.fxml");
  }

  @FXML
  void loadFloralDelivery() throws IOException {
    SceneLoading.loadScene("views/requestTypes/floralRequest.fxml");
  }

  @FXML
  void loadSecurity() throws IOException {
    SceneLoading.loadScene("views/requestTypes/securityServicesRequest.fxml");
  }

  @FXML
  void loadLab() throws IOException {
    SceneLoading.loadScene("views/requestTypes/labRequest.fxml");
  }

  @FXML
  void loadLocationTable() throws IOException {
    SceneLoading.loadScene("views/locTablePage.fxml");
  }

  @FXML
  void loadMedEquipmentRequests() throws IOException {
    SceneLoading.loadScene("views/ActServReqTablePage.fxml");
  }

  @FXML
  void killApplication() throws IOException {
    System.out.println("pressed button");
    SceneLoading.loadPopup("views/ConfirmClose.fxml", "views/sideBar.fxml");
  }
}
