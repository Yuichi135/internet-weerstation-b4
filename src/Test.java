import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        RawMeasurement mostRecent = DatabaseConnection.getMostRecentMeasurement();
        Measurement converted = new Measurement(mostRecent);
        System.out.println(converted);

        System.out.println("\n");
        System.out.println("Weerstation: " + converted.getStationId());
        System.out.println(converted.getDateStamp());
        System.out.println(Math.round(converted.getAirPressure() * 100) / 100.00 + " hPa");
        System.out.println(Math.round(converted.getInsideTemp() * 10) / 10.0 + " °C");
        System.out.println(Math.round(converted.getOutsideTemp() * 10) / 10.0 + " °C");
        System.out.println(Math.round(converted.getInsideHumidity() * 100) / 100.0 + "%");
        System.out.println(Math.round(converted.getOutsideHumidity() * 100) / 100.0 + "%");
        System.out.println(Math.round(converted.getWindSpeed() * 10) / 10.0 + " Km/H");
        System.out.println(Math.round(converted.getAvgWindSpeed() * 10) / 10.0 + " Km/h");
        System.out.println(Math.round(converted.getWindDirection() * 100) / 100.0 + " Graden");
        System.out.println(converted.getSunRise());
        System.out.println(converted.getSunSet());
        System.out.println(Math.round(converted.getBattery() * 10) / 10.0 + " Voltage");
        System.out.println(Math.round(converted.getRainRate() * 100) / 100.0 + " mm/H");
        System.out.println(Math.round(converted.getUvIndex() * 100) / 100.0);
        System.out.println(Math.round(converted.getHeatIndex() * 10) / 10.0 + " °C");
        System.out.println(Math.round(converted.getWindChill() * 10) / 10.0 + " °C");
        System.out.println(Math.round(converted.getDewPoint() * 100) / 100.0);


        ArrayList<Integer> GoodDays = new ArrayList<>();

//        if (converted.getRainRate() < 1 && converted.getUvIndex() > 0.9 && converted.getUvIndex() < 6.1 &&
//                converted.getWindChill() > 15.00 && converted.getWindChill() < 35.00) {
//            System.out.println("T is nou al " + GoodDays.size() + " dag verrekkes lekker weer.");
//        }

            if (converted.getRainRate() < 1) {
                if (converted.getUvIndex() > 0.9 && converted.getUvIndex() < 6.1) {
                    if (converted.getWindChill() > 15.00 && converted.getWindChill() < 35.00) {
                        System.out.println("\nNondejuh wa n lekker weertje.");
                        GoodDays.add(1); //misschien een getal maken in plaats van arraylist//
                        System.out.println("T is nou al " + GoodDays.size() + " dag verrekkes lekker weer.");
                    }
                }
            }

            boolean running =true;
        int summerdays = 0;

        while (running) {
            for (int days = 0; days <= 5; days++) {
                if (converted.getOutsideTemp() <= 5.0) {
                    summerdays++;
                }
            }
            for (int hots = 0; hots <= 3; hots++) {
                if (converted.getOutsideTemp() <= 8.0) {
                    summerdays++;
                }
            }
            if (summerdays > 0)
                System.out.println("fuck yeah lekker weer man");
            break;
        }
    }
}


