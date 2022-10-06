import java.util.ArrayList;
import java.util.Collections;

public class Weerstation {
    public ArrayList<Measurement> measurements = new ArrayList<>();

    public static ArrayList<Integer> display1 = new ArrayList<>();
    public static ArrayList<Integer> display2 = new ArrayList<>();
    public static ArrayList<Integer> display3 = new ArrayList<>();

    public Weerstation() {
        Collections.addAll(display1, 0x10, 0x12, 0x14, 0x16, 0x18);
        Collections.addAll(display2, 0x20, 0x22, 0x24);
        Collections.addAll(display3, 0x30, 0x32, 0x34);

        this.getMostRecent();

        IO.init();
        clearAll();
    }

    public void getMostRecent() {
        this.clearMeasurements();
        this.measurements.add(new Measurement(DatabaseConnection.getMostRecentMeasurement()));
    }

//    public void getLastHour() {
//        this.clearMeasurements();
//
//        for (RawMeasurement record : DatabaseConnection.getMeasurementsLastHour()) {
//            measurements.add(new Measurement(record));
//        }
//    }

    public void displayOutsideTemperature() {
        Measurement lastMeasurement = this.measurements.get(this.measurements.size() - 1);

        displayNumber(display1, (int) lastMeasurement.getOutsideTemp());
        displayString("Outside temperature");
    }

    public void displayInsideTemperature() {
        Measurement lastMeasurement = this.measurements.get(this.measurements.size() - 1);

        displayNumber(display1, (int) lastMeasurement.getInsideTemp());
        displayString("Inside temperature");
    }

    public void displayOutsideHumidity() {
        Measurement lastMeasurement = this.measurements.get(this.measurements.size() - 1);

        displayNumber(display1, (int) lastMeasurement.getOutsideHumidity());
        displayString("Outside Humidity");
    }

    public void displayInsideHumidity() {
        Measurement lastMeasurement = this.measurements.get(this.measurements.size() - 1);

        displayNumber(display1, (int) lastMeasurement.getInsideHumidity());
        displayString("Inside humidity");
    }

    private void displayNumber(ArrayList<Integer> display, int number) {
        int prod;

        for (Integer adress : display) {
            prod = number % 10;
            IO.writeShort(adress, prod);
            number = (number - prod) / 10;
        }
    }

    private void displayString(String string) {
        char character;
        for (int i = 0; i < string.length(); i++) {
            character = string.charAt(i);
            IO.writeShort(0x40, character);
            IO.delay(25);
        }
    }

    public void clearDisplay(ArrayList<Integer> display) {
        for (Integer adress : display) {
            IO.writeShort(adress, 0b100000000);
        }
    }

    public void clearDMDisplay() {
        IO.writeShort(0x40, 0xFE);
        IO.writeShort(0x40, 0x01);
    }

    public void clearAll() {
        clearDisplay(display1);
        clearDisplay(display2);
        clearDisplay(display3);
        clearDMDisplay();
    }

    private void clearMeasurements() {
        if (measurements != null) {
            measurements.clear();
        }
    }
}
