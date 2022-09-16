import java.text.DecimalFormat;

public class Test {
    private static final DecimalFormat NoDecimal = new DecimalFormat("0");
    private static final DecimalFormat TwoDecimal = new DecimalFormat("0.00");
    private static final DecimalFormat OneDecimel = new DecimalFormat("0.0");

    public static void main(String[] args) {

        double valueAirpressure = ValueConverter.airPressure((short) 29752);
        System.out.println("The air pressure is: " + TwoDecimal.format(valueAirpressure) + " HectoPascal");

        double valueInsideTemp = ValueConverter.temp((short) 753);
        System.out.println("The inside temp is: " + OneDecimel.format(valueInsideTemp) + " °C");

        double valueInsdeHumidity = ValueConverter.humidity((short) 55);
        System.out.println("The inside humidity is: " + NoDecimal.format(valueInsdeHumidity) + "%");

        double valueOutsideTemp = ValueConverter.temp((short) 605);
        System.out.println("The outside temp is: " + OneDecimel.format(valueOutsideTemp) + " °C");

        double valueWindSpeed = ValueConverter.windSpeed((short) 4);
        System.out.println("The wind speed is: " + OneDecimel.format(valueWindSpeed) + " Km/H");

        double valueAvgWindSpeed = ValueConverter.windSpeed((short) 5);
        System.out.println("The average wind speed is: " + valueAvgWindSpeed + "Km/H");

        double voltage = ValueConverter.battery((short) 35);
        System.out.println("The BatteryLevel is: " + TwoDecimal.format(voltage));

        double valueOutsideHumidity = ValueConverter.humidity((short) 89);
        System.out.println("The Outisde Humidity is: " + NoDecimal.format(valueOutsideHumidity)+ "%");

        double valueRainMeter = ValueConverter.rainMeter((short)5);
        System.out.println("The amount of rain is: " + valueRainMeter + " mm per hour");

        double valueWindDirection = ValueConverter.windDirection((short) 195);
        System.out.println("The Wind direction is: " + NoDecimal.format(valueWindDirection)+ " Degrees");

        String valueSunSet = ValueConverter.sunset((short) 1957);
        System.out.println("The sun set at: " + valueSunSet);

        String valueSunRise = ValueConverter.sunRise((short) 717);
        System.out.println("The sun rose at: " + valueSunRise);

        double value_UV_Index = ValueConverter.uvIndex((short)5);
        System.out.println("The UV-index is: " + value_UV_Index) ;

        double valueHeatIndex = ValueConverter.heatIndex((short) 89, (short) 605);
        System.out.println("The heat index is: " + NoDecimal.format(valueHeatIndex)+ " °C");

        double valueWindChill = ValueConverter.windChill((short) 4, (short)  605);
        System.out.println("The wind chill is: " + NoDecimal.format(valueWindChill)+ " °C");

        double valueDewPoint = ValueConverter.dewPoint((short) valueOutsideTemp, (short) valueOutsideHumidity);
        System.out.println("The dew point is: " + valueDewPoint + " °C");


    }
}
