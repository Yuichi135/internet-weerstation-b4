import java.text.DecimalFormat;

public class Test {
    private static final DecimalFormat NoDecimal = new DecimalFormat("0");
    private static final DecimalFormat TwoDecimal = new DecimalFormat("0.00");
    private static final DecimalFormat OneDecimel = new DecimalFormat("0.0");

    public static void main(String[] args) {

        double valueAirpressure = ValueConverter.airPressure((short) 29752);
        System.out.println("De air pressure is: " + TwoDecimal.format(valueAirpressure) + " HectoPascal");

        double valueInsideTemp = ValueConverter.temp((short) 753);
        System.out.println("The temp is: " + OneDecimel.format(valueInsideTemp));

        double valueInsdeHumidity = ValueConverter.humidity((short) 55);
        System.out.println("De inside humidity is: " + NoDecimal.format(valueInsdeHumidity) + "%");

        double valueOutsideTemp = ValueConverter.temp((short) 605);
        System.out.println("The temp is: " + OneDecimel.format(valueOutsideTemp));

        double valueWindSpeed = ValueConverter.windSpeed((short) 4);
        System.out.println("De wind speed is: " + OneDecimel.format(valueWindSpeed) + " Km/H");

        double valueAvgWindSpeed = ValueConverter.avgWindSpeed((short) 5);
        System.out.println("The average wind speed is: " + valueAvgWindSpeed + "Km/H");

        double Voltage = ValueConverter.battery((short) 35);
        System.out.println("The BatteryLevel is: " + TwoDecimal.format(Voltage));

        double valueOutsideHumidity = ValueConverter.humidity((short) 89);
        System.out.println("De Outisde Humidity is: " + NoDecimal.format(valueOutsideHumidity)+ "%");

        double valueRainMeter = ValueConverter.rainMeter((short)5);
        System.out.println(valueRainMeter + " mm");

        double valueWindDirection = ValueConverter.windDirection((short) 195);
        System.out.println("De Wind direction is: " + NoDecimal.format(valueWindDirection)+ " Graden");

        double valueSunSet = ValueConverter.sunSet((short) 1957);
        System.out.println("De zon ging onder op: " + valueSunSet);

        double valueSunRise = ValueConverter.sunRise((short) 717);
        System.out.println("De zon kwam op om: " + valueSunRise);

        double value_UV_Index = ValueConverter.uvIndex((short)5);
        System.out.println("The UV-index is: " + value_UV_Index);

        double valueHeatIndex = ValueConverter.heatIndex((short) 89, (short) 605);
        System.out.println("De heat index is: " + NoDecimal.format(valueHeatIndex)+ " Graden celcius");

        double valueWindChill = ValueConverter.windChill((short) 4, (short)  605);
        System.out.println("De wind chill is: " + NoDecimal.format(valueWindChill)+ " Graden celcius");

        double valueDewPoint = ValueConverter.dewPoint((short) valueOutsideTemp, (short) valueOutsideHumidity);
        System.out.println("Het dew point is: " + valueDewPoint + " Graden celcius");


    }
}
