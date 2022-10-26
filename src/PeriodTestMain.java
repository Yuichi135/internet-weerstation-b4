public class PeriodTestMain {

    public static void main(String[] args) {
        Period period = new Period(365);

        System.out.println(period.mostRainfall(2019));
//        period.consecutiveRain();
        period.getBiggestDifferenceMinMaxTemperature();
    }
}
