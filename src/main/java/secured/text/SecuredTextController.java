package secured.text;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main MVC controller.
 */
public class SecuredTextController {
    public static final Logger LOG = Logger.getLogger("secured.text");
    /**
     * Helper status string
     */
    private final static String STATUS = "Status: ";
    /**
     * Label for status
     */
    @FXML
    private Label statusLabel;
    /**
     * Encrypt radio button
     */
    @FXML
    private RadioButton radioEncrypt;
    /**
     * Decrypt radio button
     */
    @FXML
    private RadioButton radioDecrypt;
    /**
     * Password field
     */
    @FXML
    private PasswordField passwordField;
    /**
     * Zip filename
     */
    @FXML
    private TextField fileNameField;
    /**
     * Text area for the zip file text
     */
    @FXML
    private TextArea textArea;

    /**
     * Initialize components before use.
     */
    @FXML
    private void initialize() {
        ToggleGroup group = new ToggleGroup();
        radioEncrypt.setToggleGroup(group);
        radioDecrypt.setToggleGroup(group);
        radioDecrypt.setSelected(true);
        fileNameField.setText("info.bin");
        statusLabel.setStyle("-fx-font-weight: bold;");
        textArea.setWrapText(true);
    }

    /**
     * Submit button logic.
     * Encryption/decryption of zip file is performed here.
     */
    @FXML
    protected void onSubmitButtonClick() {
        Path path = Paths.get(fileNameField.getText());
        File file = new File(path.toUri());

        if (radioEncrypt.isSelected()) {
            try {
                ZipUtils.writeEncryptedFile(file, textArea.getText(),
                        RandomStringUtils.randomAlphanumeric(8),
                        passwordField.getText().toCharArray(),
                        CompressionMethod.DEFLATE, true,
                        EncryptionMethod.AES,
                        AesKeyStrength.KEY_STRENGTH_256);
                success();
            } catch (IOException ex) {
                error(ex);

            }

        } else if (radioDecrypt.isSelected()) {
            try {
                textArea.setText(ZipUtils.readEncryptedFile(file, passwordField.getText().toCharArray()));
                success();
            } catch (IOException ex) {
                error(ex);
            }
        }
    }

    /**
     * Helper method to handle success.
     */
    private void success() {
        statusLabel.setText(STATUS + "success");
        statusLabel.setTextFill(Color.web("#05730a"));
    }

    /**
     * Helper method to handle errors.
     *
     * @param ex Exception
     */
    private void error(final Exception ex) {
        statusLabel.setText(STATUS + ex.getMessage());
        statusLabel.setTextFill(Color.web("#fa1105"));
        LOG.log(Level.SEVERE, ex.toString());
    }

}