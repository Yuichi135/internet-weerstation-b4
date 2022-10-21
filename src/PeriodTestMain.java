import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PeriodTestMain {

    public static void main(String[] args) {
		ArrayList<Period> months = new ArrayList<>();

        Period period = new Period();

//        int year = 2018;
//        if((year % 100 == 0 && year % 400 == 0) || (( year % 4 == 0 && year % 100 != 0 && year % 400 != 0) )  ){ // checkt if het jaar een schrikkeljaar is of niet.
//            System.out.println(true);
//        }else{
//            System.out.println(false);
//        }

        System.out.println("The month when it rained the most: " + period.mostRainfall());

//
//		System.out.println("The average temperature of last week was " + period.getAverageOutsideTemperature());
//		System.out.println("The highest temperature of last week was " + period.getHighestOutsideTemperature());
//		System.out.println("The lowest temperature of last week was " + period.getLowestOutsideTemptemperature());
//		System.out.println("The modus temperature of last week was " + period.getModusOutsideTemperature());
//		System.out.println("The median temperature of last week was " + period.getMedianOutsideTemperature());
//		System.out.println("The standard deviation temperature of last week was " + period.getStandardDeviationOutsideTemperature());
    }
}
