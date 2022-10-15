import java.time.LocalDateTime;


public class Measurement {

    private boolean isValid = true;
    private int stationId;
    private LocalDateTime dateStamp;
    private double airPressure;
    private double insideTemp;
    private double outsideTemp;
    private int insideHumidity;
    private int outsideHumidity;
    private double windSpeed;
    private double avgWindSpeed;
    private int windDirection;
    private String sunRise;
    private String sunSet;
    private double battery;
    private double rainRate;
    private int uvIndex;
    private double heatIndex;
    private double windChill;
    private double dewPoint;

    public Measurement(RawMeasurement rawData) {
        this.stationId = Integer.parseInt(rawData.getStationId());
        this.dateStamp = rawData.getDateStamp();
        this.airPressure = ValueConverter.airPressure(rawData.getBarometer());
        this.outsideHumidity = ValueConverter.humidity(rawData.getOutsideHum());
        this.insideHumidity = ValueConverter.humidity(rawData.getInsideHum());
        this.insideTemp = ValueConverter.temp(rawData.getInsideTemp());
        this.outsideTemp = ValueConverter.temp(rawData.getOutsideTemp());
        this.windSpeed = ValueConverter.windSpeed(rawData.getWindSpeed());
        this.avgWindSpeed = ValueConverter.windSpeed(rawData.getAvgWindSpeed());
        this.windDirection = ValueConverter.windDirection(rawData.getWindDir());
        this.sunRise = ValueConverter.sunRise(rawData.getSunrise());
        this.sunSet = ValueConverter.sunset(rawData.getSunset());
        this.battery = ValueConverter.battery(rawData.getBattLevel());
        this.rainRate = ValueConverter.rainMeter(rawData.getRainRate());
        this.uvIndex = ValueConverter.uvIndex(rawData.getUVLevel());
        this.heatIndex = ValueConverter.heatIndex(rawData.getOutsideHum(), rawData.getOutsideTemp());
        this.windChill = ValueConverter.windChill(rawData.getWindSpeed(), rawData.getOutsideTemp());
        this.dewPoint = ValueConverter.dewPoint(ValueConverter.temp(rawData.getOutsideTemp()), rawData.getOutsideHum());

        if (rawData.getOutsideTemp() == 32767 || rawData.getOutsideHum() == 255
                || rawData.getWindSpeed() == 255 || rawData.getRainRate() == 32767) {
            this.isValid = false;
        }
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

    public boolean isValid() {
        return this.isValid;
    }

    public int getStationId() {
        return stationId;
    }

    public LocalDateTime getDateStamp() {
        return dateStamp;
    }

    public double getAirPressure() {
        return airPressure;
    }

    public double getInsideTemp() {
        return insideTemp;
    }

    public double getOutsideTemp() {
        return outsideTemp;
    }

    public int getInsideHumidity() {
        return insideHumidity;
    }

    public int getOutsideHumidity() {
        return outsideHumidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getAvgWindSpeed() {
        return avgWindSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public String getSunRise() {
        return sunRise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public double getBattery() {
        return battery;
    }

    public double getRainRate() {
        return rainRate;
    }

    public double getUvIndex() {
        return uvIndex;
    }

    public double getHeatIndex() {
        return heatIndex;
    }

    public double getWindChill() {
        return windChill;
    }

    public double getDewPoint() {
        return dewPoint;
    }
}



