public class PeriodTestMain {
	public static void main(String[] args){

		Period period = new Period(7); // last week

		System.out.println("The average temperature of last week was " + period.getAverageOutsideTemperature());
		System.out.println("The highest temperature of last week was " + period.getHighestOutsideTemperature());
		System.out.println("The lowest temperature of last week was " + period.getLowestOutsideTemptemperature());
		System.out.println("The modus temperature of last week was " + period.getModusOutsideTemperature());
		System.out.println("The median temperature of last week was " + period.getMedianOutsideTemperature());
		System.out.println("The standard deviation temperature of last week was " + period.getStandardDeviationOutsideTemperature());
	}
}