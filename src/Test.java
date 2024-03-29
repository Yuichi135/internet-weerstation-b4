
public class Test {

    public static void main(String[] args) {
        Weerstation station = new Weerstation();

        RawMeasurement mostRecent = DatabaseConnection.getMostRecentMeasurement();
        Measurement converted = new Measurement(mostRecent);
        Period period = new Period(31);

        System.out.println(converted);

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
        System.out.println(Math.round(converted.getDewPoint() * 100) / 100.0 + "\n");

       // period.consecutiveRain();
    }
}
