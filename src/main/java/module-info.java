module secured.text {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.apache.commons.lang3;
    requires zip4j;

    opens secured.text to javafx.fxml;
    exports secured.text;
}