package secured.text;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@ExtendWith(ApplicationExtension.class)
public class SecuredTextControllerTest {
    public static final Logger LOG = Logger.getLogger("secured.text");

    private Button submit ;
    private RadioButton encrypt;
    private RadioButton decrypt;
    private PasswordField passwordField;
    private TextArea textArea;
    private String thisIsMeEncrypted;
    private String someOtherText;
    private String password;
    private String statusSuccess ;


    @Start
    public void start(Stage stage) throws IOException {
        Pane pane = FXMLLoader.load(SecuredTextApplication.class.getResource("secured-view.fxml"));
        stage.setScene(new Scene(pane));
        stage.show();
        stage.toFront();
    }


    @Test
    void testEncryptionBidirectional(FxRobot robot) {
        retrieveGuiElements(robot);

        passwordField.setText(password);
        textArea.setText(thisIsMeEncrypted);
        robot.clickOn(encrypt);
        robot.clickOn(submit);
        Assertions.assertThat(robot.lookup("#fileNameField").queryAs(TextField.class)).hasText("info.bin");
        Assertions.assertThat(robot.lookup("#statusLabel").queryAs(Label.class)).hasText(statusSuccess);

        textArea.setText(someOtherText);
        Assertions.assertThat(robot.lookup("#textArea").queryAs(TextArea.class)).hasText(someOtherText);
        robot.clickOn(decrypt);
        robot.clickOn(submit);
        Assertions.assertThat(robot.lookup("#textArea").queryAs(TextArea.class)).hasText(thisIsMeEncrypted);
        Assertions.assertThat(robot.lookup("#statusLabel").queryAs(Label.class)).hasText(statusSuccess);

    }

    @Test
    void testInvalidPassword(FxRobot robot) {
        retrieveGuiElements(robot);

        passwordField.setText(password);
        textArea.setText(thisIsMeEncrypted);
        robot.clickOn(encrypt);
        robot.clickOn(submit);
        Assertions.assertThat(robot.lookup("#statusLabel").queryAs(Label.class)).hasText(statusSuccess);

        passwordField.setText("not the correct password");
        robot.clickOn(decrypt);
        robot.clickOn(submit);
        Assertions.assertThat(robot.lookup("#statusLabel").queryAs(Label.class)).
                hasText("Status: Wrong Password");
    }



    private void retrieveGuiElements(FxRobot robot){
         submit = robot.lookup("#submit").queryAs(Button.class);
         encrypt = robot.lookup("#radioEncrypt").queryAs(RadioButton.class);
         decrypt = robot.lookup("#radioDecrypt").queryAs(RadioButton.class);
         passwordField = robot.lookup("#passwordField").queryAs(PasswordField.class);
         textArea= robot.lookup("#textArea").queryAs(TextArea.class);
         thisIsMeEncrypted = "This is me encrypted";
         someOtherText = "some other text";
         password = "some-random-pass";
         statusSuccess = "Status: success";
    }


    @AfterEach
    void cleanup(){
        Path path = Paths.get("info.bin");
        File file = new File(path.toUri());
        LOG.info("Temp file deleted: " + file.delete());
    }
}
