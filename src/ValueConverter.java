import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil;

public class ValueConverter {

    public static double airPressure(short rawValueBarometer) {
        return rawValueBarometer * 25.4 / 1000 * 1.333224;
    }

    public static double temp(short rawvalueInsideTemp) {
        return (rawvalueInsideTemp / 10.0 - 32) / 1.8;
    }

    public static double humidity(short rawValueHumidity) {
        return rawValueHumidity;
    }

    public static double windSpeed(short rawvalueWindSpeed) {
        return rawvalueWindSpeed * 1.61;
    }

    public static double avgWindSpeed(short rawValueAvgWindSpeed) {
        return rawValueAvgWindSpeed * 1.61;
    }

    public static double windDirection(short rawValueWindDirection) {
        return rawValueWindDirection;
    }

    public static double sunRise(short rawValueSunRise) {
        return rawValueSunRise / 100.0;
    }

    public static double sunSet(short rawValueSunSet) {
        rawValueSunSet /= 100.0;
        String SunSet = Integer.toString(rawValueSunSet);

        return rawValueSunSet;
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

