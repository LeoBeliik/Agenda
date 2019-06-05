package data;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alertas {

    public static void info(String text) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Alerta!");
        info.setHeaderText(null);
        info.setContentText(text);
        info.showAndWait();
    }

    public static boolean confirmar(String text) {
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.setTitle("Alerta!");
        conf.setHeaderText(null);
        conf.setTitle("Confirmation Dialog");
        conf.setContentText(text);

        Optional<ButtonType> result = conf.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
