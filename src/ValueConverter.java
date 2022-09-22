import java.time.LocalDateTime;

public class ValueConverter {
    private String stationId;
    private LocalDateTime dateStamp;
    private double airPressure;
    private double insideTemp;
    private double outsideTemp;
    private double insideHumidity;
    private double outsideHumidity;
    private double windSpeed;
    private double avgWindSpeed;
    private double windDirection;
    private String sunRise;
    private String sunSet;
    private double battery;
    private double rainRate;
    private double uvIndex;
    private double heatIndex;
    private double windChill;
    private double dewPoint;

    public ValueConverter(RawMeasurement rawData) {
        this.stationId = rawData.getStationId();
        this.dateStamp = rawData.getDateStamp();
        this.airPressure = airPressure(rawData.getBarometer());
        this.outsideHumidity = humidity(rawData.getOutsideHum());
        this.insideHumidity = humidity(rawData.getInsideHum());
        this.insideTemp = temp(rawData.getInsideTemp());
        this.outsideTemp = temp(rawData.getOutsideTemp());
        this.windSpeed = windSpeed(rawData.getWindSpeed());
        this.avgWindSpeed = windSpeed(rawData.getAvgWindSpeed());
        this.windDirection = windDirection(rawData.getWindDir());
        this.sunRise = sunRise(rawData.getSunrise());
        this.sunSet = sunset(rawData.getSunset());
        this.battery = battery(rawData.getBattLevel());
        this.rainRate = rainMeter(rawData.getRainRate());
        this.uvIndex = uvIndex(rawData.getUVLevel());
        this.heatIndex = heatIndex(rawData.getOutsideHum(), rawData.getOutsideTemp());
        this.windChill = windChill(rawData.getWindSpeed(),rawData.getOutsideTemp());
        this.dewPoint = dewPoint(rawData.getOutsideTemp(), rawData.getOutsideHum());
    }

    @Override
    public String toString() {
        String s = "RawMeasurement:"
                + "\nstationId = \t" + stationId
                + "\ndateStamp = \t" + dateStamp
                + "\nairPressure = \t" + airPressure
                + "\ninsideTemp = \t" + insideTemp
                + "\ninsideHum = \t" + insideHumidity
                + "\noutsideTemp = \t" + outsideTemp
                + "\nwindSpeed = \t" + windSpeed
                + "\navgWindSpeed = \t" + avgWindSpeed
                + "\nwindDir = \t\t" + windDirection
                + "\noutsideHum = \t" + outsideHumidity
                + "\nrainRate = \t\t" + rainRate
                + "\nUVLevel = \t\t" + uvIndex
//                + "\nsolarRad = \t" + solarRad
//                + "\nxmitBatt = \t" + xmitBatt
                + "\nbattLevel = \t" + battery
//                + "\nforeIcon = \t" + foreIcon
                + "\nsunrise = \t\t" + sunRise
                + "\nsunset = \t\t" + sunSet
                + "\nheatIndex = \t" + heatIndex
                + "\nwindChill = \t" + windChill
                + "\ndewPoint = \t\t" + dewPoint;
        return s;
    }

    public static double airPressure(short rawValueBarometer) {
        return rawValueBarometer * 25.4 / 1000 * 1.333224;
    }

    public static double temp(short rawvalueInsideTemp) {
        return (rawvalueInsideTemp / 10.0 - 32) / 1.8;
    }

    public static double humidity(short rawValueInsideHumidity) {
        return rawValueInsideHumidity;
    }

    public static double windSpeed(short rawvalueWindSpeed) {
        return rawvalueWindSpeed * 1.61;
    }

    public static double windDirection(short rawValueWindDirection) {
        return rawValueWindDirection;
    }

    public static String sunRise(short rawValueSunRise) {
        double sunRise= (double) rawValueSunRise / 100;
        String timeRise = String.valueOf(sunRise);
        timeRise = timeRise.replace('.', ':');
        return "0"+timeRise;

    }

    public static String sunset(short rawValueSunSet) {
        double sunSet = (double) rawValueSunSet / 100;
        String timeSet = String.valueOf(sunSet);
        timeSet = timeSet.replace('.', ':');
        return timeSet;

    }

    public static double battery(short Voltage) {
        return ((Voltage * 300.0) / 512.0) / 100.0;
    }

    public static double rainMeter(short rawValueRainMeter) {
        return rawValueRainMeter * 0.2;
    }

    public static double uvIndex(short rawValueUvIndex) {
        return rawValueUvIndex / 10.0;
    }

    public static double heatIndex(short rawOutsideHumidity, short rawOutsideTemp) {
        double outsideTemp = rawOutsideTemp / 10;
        int outsideHum = rawOutsideHumidity;
        double heatIndex = -42.379 + 2.04901523 * outsideTemp + 10.14333127 * outsideHum;
        heatIndex = heatIndex - 0.22475541 * outsideTemp * outsideHum - 6.83783 * Math.pow(10, -3) * outsideTemp * outsideTemp;
        heatIndex = heatIndex - 5.481717 * Math.pow(10, -2) * outsideHum * outsideHum;
        heatIndex = heatIndex + 1.22874 * Math.pow(10, -3) * outsideTemp * outsideTemp * outsideHum;
        heatIndex = heatIndex + 8.5282 * Math.pow(10, -4) * outsideTemp * outsideHum * outsideHum;
        heatIndex = heatIndex - 1.99 * Math.pow(10, -6) * outsideTemp * outsideTemp * outsideHum * outsideHum;
        heatIndex = (heatIndex - 32) / 1.8;
        return heatIndex;
    }

    public static double windChill(short rawWindSpeed, short rawOutsideTemp) {
        double windChill = 0;
        double outsideTemp = (double) rawOutsideTemp / 10;
        double windSpeed = (double) rawWindSpeed;
        if ((windSpeed <= 0) || outsideTemp > 93.2) {
            windChill = (double) outsideTemp;
        } else {
            windChill = 35.74 +
                    (0.6215 * outsideTemp) - 35.75 * (Math.pow(windSpeed, 0.16)) +
                    (0.4275 * outsideTemp) * (Math.pow(windSpeed, 0.16));
            if (windChill > outsideTemp) {
                windChill = outsideTemp;
            }
        }
        windChill = (windChill - 32) / 1.8;
        return windChill;
    }


    public static double dewPoint(short rawTemp, short rawOutsideHumidity) {
        double dewPoint = rawTemp - ((100 - rawOutsideHumidity) / 10.0);
        return dewPoint;
    }

}

