package edu.wpi.cs3733.D22.teamX;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class ReqInTransportControllerTest extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ReqInTransport.fxml")));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testPatientNameTextField() {
    clickOn("#patientName");
    write("h");
    verifyThat("#patientName", hasText("h"));
  }

  @Test
  public void testRoomNumTextField() {
    clickOn("#roomNum");
    write("h");
    verifyThat("#roomNum", hasText("h"));
  }

  @Test
  public void testAssignStaffTextField() {
    clickOn("#assignStaff");
    write("h");
    verifyThat("#assignStaff", hasText("h"));
  }

  @Test
  public void testServiceStatusTextField() {
    clickOn("#serviceStatus");
    write("h");
    verifyThat("#serviceStatus", hasText("h"));
  }

  @Test
  public void testResetButton() {
    clickOn("#resetFields");
    verifyThat("#patientName", hasText(""));
    verifyThat("#roomNum", hasText(""));
    verifyThat("#assignStaff", hasText(""));
    verifyThat("#serviceStatus", hasText(""));
  }
}
