import java.text.DecimalFormat;

public class Test {
    private static final DecimalFormat NoDecimal = new DecimalFormat("0");
    private static final DecimalFormat TwoDecimal = new DecimalFormat("0.00");
    private static final DecimalFormat OneDecimel = new DecimalFormat("0.0");

    public static void main(String[] args) {
        RawMeasurement mostRecent = DatabaseConnection.getMostRecentMeasurement();
        ValueConverter converted = new ValueConverter(mostRecent);
        System.out.println(converted);
    }
}
