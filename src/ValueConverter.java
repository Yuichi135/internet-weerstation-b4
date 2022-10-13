public class ValueConverter {

    /**
     * Returns air pressure
     * @param rawValueBarometer in inch/hg
     * @return air pressure in hPa
     */

    public static double airPressure(short rawValueBarometer) {
        return rawValueBarometer * 25.4 / 1000 * 1.333224;
    }

    /**
     * Returns temperature
     * @param rawvalueTemp outside temperature in °F * 10
     * @return temperatue in °C
     */

    public static double temp(short rawvalueTemp) {
        return (rawvalueTemp / 10.0 - 32) / 1.8;
    }

    /**
     * Returns humidity
     * @param rawValueInsideHumidity
     * @return humidity in percentage
     */

    public static int humidity(short rawValueInsideHumidity) {
        return rawValueInsideHumidity;
    }

    /**
     * Returns windspeed
     * @param rawvalueWindSpeed
     * @return windspeed in km/h
     */

    public static double windSpeed(short rawvalueWindSpeed) {
        return rawvalueWindSpeed * 1.61;
    }

    /**
     * Returns winddirection
     * @param rawValueWindDirection
     * @return winddirection in degrees (0 = north, 180 = south)
     */

    public static int windDirection(short rawValueWindDirection) {
        return rawValueWindDirection;
    }

    /**
     * Returns time at sunrise
     * @param rawValueSunRise
     * @return sunrise in string hh:mm
     */

    public static String sunRise(short rawValueSunRise) {
        double sunRise = (double) rawValueSunRise / 100;
        String timeRise = String.valueOf(sunRise);
        timeRise = timeRise.replace('.', ':');
        return ((sunRise > 9.59) ? "" : "0") + timeRise;
    }

    /**
     * Returns time at sunset
     * @param rawValueSunSet
     * @return sunset in string hh:mm
     */

    public static String sunset(short rawValueSunSet) {
        double sunSet = (double) rawValueSunSet / 100;
        String timeSet = String.valueOf(sunSet);
        timeSet = timeSet.replace('.', ':');
        return timeSet;

    }

    /**
     * Returns Voltage
     * @param Voltage
     * @return Voltage
     */

    public static double battery(short Voltage) {
        return ((Voltage * 300.0) / 512.0) / 100;
    }

    /**
     * Returns rainfall
     * @param rawValueRainMeter rain in inch/h * 100
     * @return rainfall in mm/h
     */

    public static double rainMeter(short rawValueRainMeter) {
        return rawValueRainMeter * 0.2;
    }

    /**
     * Returns UV index
     * @param rawValueUvIndex
     * @return UV index
     */

    public static int uvIndex(short rawValueUvIndex) {
        return rawValueUvIndex / 10;
    }

    /**
     * Returns Heat index
     * @param rawOutsideHumidity humidity in percentage
     * @param rawOutsideTemp outside temperature in °F * 10
     * @return heatIndex in °C
     */

    public static double heatIndex(short rawOutsideHumidity, short rawOutsideTemp) {
        double outsideTemp = rawOutsideTemp / 10;
        int outsideHum = rawOutsideHumidity;
        double heatIndex = -42.379 + 2.04901523 * outsideTemp + 10.14333127 * outsideHum;
        heatIndex = heatIndex - 0.22475541 * outsideTemp * outsideHum - 6.83783 * Math.pow(10, -3) * outsideTemp * outsideTemp;
        heatIndex = heatIndex - 5.481717 * Math.pow(10, -2) * outsideHum * outsideHum;
        heatIndex = heatIndex + 1.22874 * Math.pow(10, -3) * outsideTemp * outsideTemp * outsideHum;
        heatIndex = heatIndex + 8.5282 * Math.pow(10, -4) * outsideTemp * outsideHum * outsideHum;
        heatIndex = heatIndex - 1.99 * Math.pow(10, -6) * outsideTemp * outsideTemp * outsideHum * outsideHum;
        //convert from °F to °C
        heatIndex = (heatIndex - 32) / 1.8;
        return heatIndex;
    }

    /**
     * Returns Windchill
     * @param rawWindSpeed windspeed in mph/h
     * @param rawOutsideTemp outside temperature in °F * 10
     * @return Windchill in °C
     */

    public static double windChill(short rawWindSpeed, short rawOutsideTemp) {
        double windChill = 0;
        double outsideTemp = (double) rawOutsideTemp / 10;
        double windSpeed = (double) rawWindSpeed;
        if ((windSpeed <= 0) || outsideTemp > 93.2) {
            windChill = outsideTemp;
        } else {
            windChill = 35.74 +
                    (0.6215 * outsideTemp) - 35.75 * (Math.pow(windSpeed, 0.16)) +
                    (0.4275 * outsideTemp) * (Math.pow(windSpeed, 0.16));
            if (windChill > outsideTemp) {
                windChill = outsideTemp;
            }
        }
        //convert from °F to °C
        windChill = (windChill - 32) / 1.8;
        return windChill;
    }

    /**
     * Returns Dewpoint
     * @param Temp outside temperature in °F * 10
     * @param rawOutsideHumidity outside humidity in percentage
     * @return dewPoint in °C
     */


    public static double dewPoint(double Temp, short rawOutsideHumidity) {
        double dewPoint = Temp - ((100 - rawOutsideHumidity) / 10.0);
        return dewPoint;
    }

}

