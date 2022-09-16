import java.text.DecimalFormat;

public class Test {
    private static final DecimalFormat NoDecimal = new DecimalFormat("0");
    private static final DecimalFormat TwoDecimal = new DecimalFormat("0.00");
    private static final DecimalFormat OneDecimel = new DecimalFormat("0.0");

    public static void main(String[] args) {

        double valueAirpressure = ValueConverter.airPressure((short) 29752);
        System.out.println("De air pressure is: " + TwoDecimal.format(valueAirpressure) + " HectoPascal");

        double valueInsideTemp = ValueConverter.InsideTemp((short) 753);
        System.out.println("The temp is: " + OneDecimel.format(valueInsideTemp));

        double valueInsdeHumidity = ValueConverter.InsideHumidity((short) 55);
        System.out.println("De inside humidity is: " + NoDecimal.format(valueInsdeHumidity) + "%");

        double valueOutsideTemp = ValueConverter.OutsideTemp((short) 605);
        System.out.println("The temp is: " + OneDecimel.format(valueOutsideTemp));

        double valueWindSpeed = ValueConverter.WindSpeed((short) 4);
        System.out.println("De wind speed is: " + OneDecimel.format(valueWindSpeed) + " Km/H");

        double valueAvgWindSpeed = ValueConverter.avgWindSpeed((short) 5);
        System.out.println("The average wind speed is: " + valueAvgWindSpeed + "Km/H");

        double Voltage = ValueConverter.battery((short) 35);
        System.out.println("The BatteryLevel is: " + TwoDecimal.format(Voltage));

        double valueOutsideHumidity = ValueConverter.OutsideHumidity((short) 89);
        System.out.println("De Outisde Humidity is: " + NoDecimal.format(valueOutsideHumidity)+ "%");

        double valueRainMeter = ValueConverter.rainMeter((short)5);
        System.out.println(valueRainMeter + " mm");

        double valueWindDirection = ValueConverter.WindDirection((short) 195);
        System.out.println("De Wind direction is: " + NoDecimal.format(valueWindDirection)+ " Graden");

        double valueSunSet = ValueConverter.Sunset((short) 1957);
        System.out.println("De zon ging onder op: " + valueSunSet);

        double valueSunRise = ValueConverter.SunRise((short) 717);
        System.out.println("De zon kwam op om: " + valueSunRise);

        double value_UV_Index = ValueConverter.uvIndex((short)5);
        System.out.println("The UV-index is: " + value_UV_Index);

        double valueHeatIndex = ValueConverter.HeatIndex((short) 89, (short) 605);
        System.out.println("De heat index is: " + valueHeatIndex + " Graden celcius");

        double valueWindChill = ValueConverter.WindChill((short) 4, (short)  605);
        System.out.println("De wind chill is: " + valueWindChill);

        double valueDewPoint = ValueConverter.dewPoint((short) valueOutsideTemp, (short) valueOutsideHumidity);
        System.out.println("Het dew point is: " + valueDewPoint + " Graden celcius");


    }
}
