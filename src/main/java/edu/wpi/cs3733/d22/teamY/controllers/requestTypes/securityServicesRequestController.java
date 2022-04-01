package edu.wpi.cs3733.d22.teamY.controllers.requestTypes;

import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.d22.teamY.controllers.AbsGlobalControllerFuncs;
import edu.wpi.cs3733.d22.teamY.model.dao.exception.DaoAddException;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class securityServicesRequestController extends AbsGlobalControllerFuncs {
  // Text input
  @FXML private TextField input_RoomID;
  @FXML private TextField input_PatientName;
  @FXML private TextField input_AssignedNurse;
  @FXML private TextField input_RequestStatus;

  @FXML private TextArea input_AdditionalNotes;
  // Radio buttons
  @FXML private JFXRadioButton unwantedGuestRadioButton;
  @FXML private JFXRadioButton disruptionRadioButton;
  @FXML private JFXRadioButton theftRadioButton;

  @FXML private JFXRadioButton mostUrgentRadioButton;
  @FXML private JFXRadioButton urgentRadioButton;
  @FXML private JFXRadioButton lowPriorityRadioButton;

  private Scene requestMenu = null;

  // Security types text. These should be changed depending on what the names in the database are.
  private final String unwantedGuestText = "unwantedGuest";
  private final String disruptionText = "disruption";
  private final String theftText = "theft";

  // Security priority text. These should be changed depending on what the names in  the database
  // are.
  private final String mostUrgentText = "mostUrgent";
  private final String urgentText = "urgent";
  private final String lowPriorityText = "lowPriority";

  public securityServicesRequestController() throws IOException {}

  // BACKEND PEOPLE, THIS FUNCTION PASSES THE PARAMETERS TO THE DATABASE
  /**
   * Submits a service request.
   *
   * @param roomID The room ID.
   * @param patientName The patient name.
   * @param assignedNurse The assigned nurse.
   * @param requestStatus The request status.
   * @param additionalNotes Any additional notes.
   * @param requestTypeSelected The type of request selected.
   * @param requestPriority The priority of the request.
   * @throws DaoAddException if there is an error adding something to the database (one of the
   *     fields is invalid)
   */
  private void submitRequest(
      String roomID,
      String patientName,
      String assignedNurse,
      String requestStatus,
      String additionalNotes,
      String requestTypeSelected,
      String requestPriority)
      throws DaoAddException {
    // Code to add the fields to the database goes here.
  }

  // Called when the submit button is pressed.
  @FXML
  void submitButton() {
    // Checks if a bouquet choice has been made
    if (RequestControllerUtil.isRadioButtonSelected(
            disruptionRadioButton, theftRadioButton, unwantedGuestRadioButton)
        && RequestControllerUtil.isRadioButtonSelected(
            urgentRadioButton, mostUrgentRadioButton, lowPriorityRadioButton)) {
      try {
        submitRequest(
            input_RoomID.getText(),
            input_PatientName.getText(),
            input_AssignedNurse.getText(),
            input_RequestStatus.getText(),
            input_AdditionalNotes.getText(),
            getRequestType(),
            getRequestPriority());
      }
      // Thrown if one of the fields in submitRequest is invalid.
      catch (DaoAddException e) {
        System.out.println("One of more fields was invalid.");
      }
    } else {
      System.out.println("Please select a request type and priority.");
    }
  }

  // Returns the database name of the selected radio button.
  private String getRequestType() {
    if (theftRadioButton.isSelected()) return theftText;
    if (disruptionRadioButton.isSelected()) return disruptionText;
    if (unwantedGuestRadioButton.isSelected()) return unwantedGuestText;
    // Should never happen
    return ("");
  }

  // Returns the database name of the selected radio button.
  private String getRequestPriority() {
    if (lowPriorityRadioButton.isSelected()) return lowPriorityText;
    if (urgentRadioButton.isSelected()) return urgentText;
    if (mostUrgentRadioButton.isSelected()) return mostUrgentText;
    // Should never happen
    return ("");
  }

  @FXML
  void backToRequestMenu(ActionEvent event) throws IOException {
    loadScene("views/requestMenu.fxml");
    resetAllFields();
  }

  //  Reset button functionality
  @FXML
  void resetAllFields() {
    // Text input
    input_RoomID.setText("");
    input_PatientName.setText("");
    input_AssignedNurse.setText("");
    input_RequestStatus.setText("");

    input_AdditionalNotes.setText("");
    // Report type radio buttons
    unwantedGuestRadioButton.setSelected(false);
    disruptionRadioButton.setSelected(false);
    theftRadioButton.setSelected(false);
    // Priority radio buttons
    mostUrgentRadioButton.setSelected(false);
    urgentRadioButton.setSelected(false);
    lowPriorityRadioButton.setSelected(false);
  }
}
