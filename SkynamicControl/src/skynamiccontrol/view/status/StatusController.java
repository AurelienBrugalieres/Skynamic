package skynamiccontrol.view.status;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import skynamiccontrol.model.Aircraft;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by Elodie on 14/02/2017.
 * Controller of Status View
 */
public class StatusController implements Initializable{

    @FXML
    private Text altitude;

    @FXML
    private Text speed;

    @FXML
    private Text status;

    @FXML
    private javafx.scene.image.ImageView batterie_image;

    private Aircraft aircraft;

    public StatusController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setView(Aircraft aircraft) {
        this.aircraft = aircraft;
        altitude.setText(String.valueOf(aircraft.getAltitude()));
        speed.setText(String.valueOf(aircraft.getSpeed()));
        status.setText(String.valueOf(aircraft.getCurrent_status()));

        double battery_level = aircraft.getBatteryLevel();
        System.out.println("batterie : "+battery_level+" "+Aircraft.MAX_BATTERY_VOLTAGE * (2.0 / 5.0)+" "+Aircraft.MAX_BATTERY_VOLTAGE * (3 / 5));
        if (battery_level < Aircraft.MAX_BATTERY_VOLTAGE / 5.0) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat5.png").toExternalForm()));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (2.0 / 5.0)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat4.png").toExternalForm()));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (3.0 / 5.0)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat3.png").toExternalForm()));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (4.0 / 5.0)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat2.png").toExternalForm()));
        } else {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat1.png").toExternalForm()));
        }
        altitude.setVisible(true);
        speed.setVisible(true);
        status.setVisible(true);
        batterie_image.setVisible(true);
        // status_pane.getScene().setRoot(vbox);
    }

    public void update() {
        this.altitude.setText(String.valueOf(aircraft.getAltitude()));
        this.speed.setText(String.valueOf(aircraft.getSpeed()));
        this.status.setText(String.valueOf(aircraft.getCurrent_status()));
       System.out.println("update");
        System.out.println(aircraft.getCurrent_status());
        double battery_level = (aircraft.getBatteryLevel());

        if (battery_level < Aircraft.MAX_BATTERY_VOLTAGE / 5) {
            this.batterie_image.setImage(new Image("src\\resources\\bitmaps\\bat5.png"));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (2 / 5)) {
            this.batterie_image.setImage(new Image("src\\resources\\bitmaps\\bat4.png"));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (3 / 5)) {
            this.batterie_image.setImage(new Image("src\\resources\\bitmaps\\bat3.png"));
        }else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (4 / 5)) {
            this.batterie_image.setImage(new Image("src\\resources\\bitmaps\\bat2.png"));
        } else {
            this.batterie_image.setImage(new Image("src\\resources\\bitmaps\\bat1.png"));
        }

    }
}
