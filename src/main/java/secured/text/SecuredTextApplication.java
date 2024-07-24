package secured.text;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SecuredTextApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SecuredTextApplication.class.getResource("secured-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        stage.setTitle("Secured Text 2 File");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main entry point.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}