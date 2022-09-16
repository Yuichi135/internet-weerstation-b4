import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil;

public class ValueConverter {

    public static double airPressure(short rawValueBarometer) {
        return rawValueBarometer * 25.4 / 1000 * 1.333224;
    }

    public static double InsideTemp(short rawvalueInsideTemp) {
        return (rawvalueInsideTemp / 10.0 - 32) / 1.8;
    }
    //Naar 1 methode maken

    public static double InsideHumidity(short rawValueInsideHumidity) {
        return rawValueInsideHumidity;
    }
    //Naar 1 methode maken

    public static double OutsideTemp(short rawvalueOutsideTemp) {
        return (rawvalueOutsideTemp / 10.0 - 32) / 1.8;
    }

    public static double WindSpeed(short rawvalueWindSpeed) {
        return rawvalueWindSpeed * 1.61;
    }

    public static double avgWindSpeed(short rawValueAvgWindSpeed) {
        return rawValueAvgWindSpeed * 1.61;
    }

    public static double WindDirection(short rawValueWindDirection) {
        return rawValueWindDirection;
    }

    public static double OutsideHumidity(short rawValueInsideHumidity) {
        return rawValueInsideHumidity;
    }

    public static double SunRise(short rawValueSunRise) {
        return rawValueSunRise / 100.0;
    }

    public static double Sunset(short rawValueSunSet) {
        rawValueSunSet /= 100.0;
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

    public static double HeatIndex(short rawOutsideHumidity, short rawOutsideTemp) {
        double rawValueHeatIndex = -42.379 + 2.04901523 * rawOutsideTemp + 10.14333127 * rawOutsideHumidity;
        rawValueHeatIndex = rawValueHeatIndex - 0.22475541 * rawOutsideTemp * rawOutsideHumidity - 6.83783 * Math.pow(10, -3) * rawOutsideTemp * rawOutsideTemp;
        rawValueHeatIndex = rawValueHeatIndex - 5.481717 * Math.pow(10, -2) * rawOutsideHumidity * rawOutsideHumidity;
        rawValueHeatIndex = rawValueHeatIndex + 1.22874 * Math.pow(10, -3) * rawOutsideTemp * rawOutsideTemp * rawOutsideHumidity;
        rawValueHeatIndex = rawValueHeatIndex + 8.5282 * Math.pow(10, -4) * rawOutsideTemp * rawOutsideHumidity * rawOutsideHumidity;
        rawValueHeatIndex = rawValueHeatIndex - 1.99 * Math.pow(10, -6) * rawOutsideTemp * rawOutsideTemp * rawOutsideHumidity * rawOutsideHumidity;
        rawValueHeatIndex = (rawValueHeatIndex - 32) / 1.8;
        return Math.round(rawValueHeatIndex);
    }

    public static double WindChill(short rawWindSpeed, short rawOutsideTemp) {
        double rawValueWindChill = 0;
        if ((rawWindSpeed <= 0) || rawOutsideTemp > 93.2) {
            System.out.println("Geen wind of temperatuur te hoog");
            rawValueWindChill = (double) rawOutsideTemp;
        } else {
            rawValueWindChill = 35.74 +
                    (0.6215 * rawOutsideTemp) - 35.75 * (Math.pow(rawWindSpeed, 0.16)) +
                    (0.4275 * rawOutsideTemp) * (Math.pow(rawWindSpeed, 0.16));
            if (rawValueWindChill > rawOutsideTemp) {
                rawValueWindChill = rawOutsideTemp;
            }
        }
        //
        rawValueWindChill = (rawValueWindChill - 32) / 1.8;

        return (int) Math.round(rawValueWindChill);
    }

    public static double dewPoint(short rawTemp, short rawOutsideHumidity) {
        double dewPoint = rawTemp - ((100 - rawOutsideHumidity) / 10.0);
        return dewPoint;
    }

}

