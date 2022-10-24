import java.lang.reflect.Array;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

/**
 * A class to contain a period of time
 *
 * @author Johan Talboom
 * @version 2.0
 */
public class Period {
    private LocalDate beginPeriod;
    private LocalDate endPeriod;
    private ArrayList<Measurement> measurements;

    /**
     * default constructor, sets the period to today
     */
    public Period() {
        this.beginPeriod = LocalDate.now();
        this.endPeriod = LocalDate.now();
    }

    public Period(LocalDate beginPeriod, LocalDate endPeriod) {
        this.beginPeriod = beginPeriod;
        this.endPeriod = endPeriod;
    }

    public Period(LocalDate beginPeriod) {
        this.beginPeriod = beginPeriod;
        this.endPeriod = LocalDate.now();
    }

    public Period(int days) {
        this.beginPeriod = LocalDate.now().minus(java.time.Period.ofDays(days));
        this.endPeriod = LocalDate.now();
    }

    /**
     * Simple setter for start of period
     */
    public void setStart(int year, int month, int day) {
        beginPeriod = LocalDate.of(year, month, day);
    }

    /**
     * simple setter for end of period
     */
    public void setEnd(int year, int month, int day) {
        endPeriod = LocalDate.of(year, month, day);
    }

    /**
     * alternative setter for start of period
     *
     * @param beginPeriod
     */
    public void setStart(LocalDate beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    /**
     * alternative setter for end of period
     *
     * @param endPeriod
     */
    public void setEnd(LocalDate endPeriod) {
        this.endPeriod = endPeriod;
    }

    /**
     * calculates the number of days in the period
     */
    public long numberOfDays() {
        return ChronoUnit.DAYS.between(beginPeriod, endPeriod);
    }


    /**
     * gets all raw measurements of this period from the database
     *
     * @return a list of raw measurements
     */
    public ArrayList<RawMeasurement> getRawMeasurements() {
        return DatabaseConnection.getMeasurementsBetween(LocalDateTime.of(beginPeriod, LocalTime.of(0, 1)), LocalDateTime.of(endPeriod, LocalTime.of(23, 59)));
    }

    /**
     * Builds an ArrayList of measurements. This method also filters out any 'bad' measurements
     *
     * @return a filtered list of measurements
     */
    public ArrayList<Measurement> getMeasurements() {
        if (this.measurements == null) {
            this.measurements = new ArrayList<>();
//            ArrayList<Measurement> measurements = new ArrayList<>();
            ArrayList<RawMeasurement> rawMeasurements = getRawMeasurements();
            for (RawMeasurement rawMeasurement : rawMeasurements) {
                Measurement measurement = new Measurement(rawMeasurement);
                if (measurement.isValid()) {
//                    measurements.add(measurement);
                    this.measurements.add(measurement);
                }
            }
        }
        return this.measurements;
//        return measurements;
    }

    /**
     * todo
     *
     * @return
     */

    // Average
    public double getAverageOutsideTemperature() {
        return getAverage(getOutsideTemperature());
    }

    public double getAverageInsideTemperature() {
        return getAverage(getInsideTemperature());
    }

    public double getAverageOutsideHumidity() {
        return getAverage(getOutsideHumidity());
    }

    public double getAverageInsideHumidity() {
        return getAverage(getInsideHumidity());
    }

    public double getAverageAirpressure() {
        return getAverage(getAirpressure());
    }

    public double getAverageRainRate() {
        return getAverage(getRainRate());
    }

    public double getAverageUVIndex() {
        return getAverage(getUVIndex());
    }

    // Onnodig
//    public double getAverageWindDirection() {
//        return getAverage(getWindDirection());
//    }

    public double getAverageWindSpeed() {
        return getAverage(getWindSpeed());
    }

    public double getAverageHeatIndex() {
        return getAverage(getHeatIndex());
    }

    public double getAverageWindChill() {
        return getAverage(getWindChill());
    }

    public double getAverageDewpoint() {
        return getAverage(getDewpoint());
    }

    // Highest
    public double getHighestOutsideTemperature() {
        return getHighest(getOutsideTemperature());
    }

    public double getHighestInsideTemperature() {
        return getHighest(getInsideTemperature());
    }

    public double getHighestOutsideHumidity() {
        return getHighest(getOutsideHumidity());
    }

    public double getHighestInsideHumidity() {
        return getHighest(getInsideHumidity());
    }

    public double getHighestAirpressure() {
        return getHighest(getAirpressure());
    }

    public double getHighestRainRate() {
        return getHighest(getRainRate());
    }

    public double getHighestUVIndex() {
        return getHighest(getUVIndex());
    }

    // Onnodig
//    public double getHighestWindDirection() {
//        return getHighest(getWindDirection());
//    }

    public double getHighestWindSpeed() {
        return getHighest(getWindSpeed());
    }

    public double getHighestHeatIndex() {
        return getHighest(getHeatIndex());
    }

    public double getHighestWindChill() {
        return getHighest(getWindChill());
    }

    public double getHighestDewpoint() {
        return getHighest(getDewpoint());
    }

    // Lowest
    public double getLowestOutsideTemperature() {
        return getLowest(getOutsideTemperature());
    }

    public double getLowestInsideTemperature() {
        return getLowest(getInsideTemperature());
    }

    public double getLowestOutsideHumidity() {
        return getLowest(getOutsideHumidity());
    }

    public double getLowestInsideHumidity() {
        return getLowest(getInsideHumidity());
    }

    public double getLowestAirpressure() {
        return getLowest(getAirpressure());
    }

    public double getLowestRainRate() {
        return getLowest(getRainRate());
    }

    public double getLowestUVIndex() {
        return getLowest(getUVIndex());
    }

    // Onnodig
//    public double getLowestWindDirection() {
//        return getLowest(getWindDirection());
//    }

    public double getLowestWindSpeed() {
        return getLowest(getWindSpeed());
    }

    public double getLowestHeatIndex() {
        return getLowest(getHeatIndex());
    }

    public double getLowestWindChill() {
        return getLowest(getWindChill());
    }

    public double getLowestDewpoint() {
        return getLowest(getDewpoint());
    }

    // Modus
    public double getModusOutsideTemperature() {
        return getModus(getOutsideTemperature());
    }

    public double getModusInsideTemperature() {
        return getModus(getInsideTemperature());
    }

    public double getModusOutsideHumidity() {
        return getModus(getOutsideHumidity());
    }

    public double getModusInsideHumidity() {
        return getModus(getInsideHumidity());
    }

    public double getModusAirpressure() {
        return getModus(getAirpressure());
    }

    public double getModusRainRate() {
        return getModus(getRainRate());
    }

    public double getModusUVIndex() {
        return getModus(getUVIndex());
    }

    public double getModusWindDirection() {
        return getModus(getWindDirection());
    }

    public double getModusWindSpeed() {
        return getModus(getWindSpeed());
    }

    public double getModusHeatIndex() {
        return getModus(getHeatIndex());
    }

    public double getModusWindChill() {
        return getModus(getWindChill());
    }

    public double getModusDewpoint() {
        return getModus(getDewpoint());
    }

    // Median
    public double getMedianOutsideTemperature() {
        return getMedian(getOutsideTemperature());
    }

    public double getMedianInsideTemperature() {
        return getMedian(getInsideTemperature());
    }

    public double getMedianOutsideHumidity() {
        return getMedian(getOutsideHumidity());
    }

    public double getMedianInsideHumidity() {
        return getMedian(getInsideHumidity());
    }

    public double getMedianAirpressure() {
        return getMedian(getAirpressure());
    }

    public double getMedianRainRate() {
        return getMedian(getRainRate());
    }

    public double getMedianUVIndex() {
        return getMedian(getUVIndex());
    }

    // Onnodig
//    public double getMedianWindDirection() {
//        return getMedian(getWindDirection());
//    }

    public double getMedianWindSpeed() {
        return getMedian(getWindSpeed());
    }

    public double getMedianHeatIndex() {
        return getMedian(getHeatIndex());
    }

    public double getMedianWindChill() {
        return getMedian(getWindChill());
    }

    public double getMedianDewpoint() {
        return getMedian(getDewpoint());
    }

    // StandardDeviation
    public double getStandardDeviationOutsideTemperature() {
        return getStandardDeviation(getOutsideTemperature());
    }

    public double getStandardDeviationInsideTemperature() {
        return getStandardDeviation(getInsideTemperature());
    }

    public double getStandardDeviationOutsideHumidity() {
        return getStandardDeviation(getOutsideHumidity());
    }

    public double getStandardDeviationInsideHumidity() {
        return getStandardDeviation(getInsideHumidity());
    }

    public double getStandardDeviationAirpressure() {
        return getStandardDeviation(getAirpressure());
    }

    public double getStandardDeviationRainRate() {
        return getStandardDeviation(getRainRate());
    }

    public double getStandardDeviationUVIndex() {
        return getStandardDeviation(getUVIndex());
    }

    // Onnodig
//    public double getStandardDeviationWindDirection() {
//        return getStandardDeviation(getWindDirection());
//    }

    public double getStandardDeviationWindSpeed() {
        return getStandardDeviation(getWindSpeed());
    }

    public double getStandardDeviationHeatIndex() {
        return getStandardDeviation(getHeatIndex());
    }

    public double getStandardDeviationWindChill() {
        return getStandardDeviation(getWindChill());
    }

    public double getStandardDeviationDewpoint() {
        return getStandardDeviation(getDewpoint());
    }

    // Getters (In een double arraylist)
    public ArrayList<Double> getOutsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getOutsideTemp());
        }
        return temperatures;
    }

    public ArrayList<Double> getInsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getInsideTemp());
        }
        return temperatures;
    }

    public ArrayList<Double> getOutsideHumidity() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> humidity = new ArrayList<>();

        for (Measurement measurement : measurements) {
            humidity.add((double) measurement.getOutsideHumidity());
        }
        return humidity;
    }

    public ArrayList<Double> getInsideHumidity() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> humidity = new ArrayList<>();

        for (Measurement measurement : measurements) {
            humidity.add((double) measurement.getInsideHumidity());
        }
        return humidity;
    }

    public ArrayList<Double> getAirpressure() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airpressure = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airpressure.add(measurement.getAirPressure());
        }
        return airpressure;
    }

    public ArrayList<Double> getRainRate() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> rainRate = new ArrayList<>();

        for (Measurement measurement : measurements) {
            rainRate.add(measurement.getRainRate());
        }

        return rainRate;
    }

    public ArrayList<Double> getUVIndex() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> uvIndex = new ArrayList<>();

        for (Measurement measurement : measurements) {
            uvIndex.add(measurement.getUvIndex());
        }

        return uvIndex;
    }

    public ArrayList<Double> getWindDirection() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> windDirection = new ArrayList<>();

        for (Measurement measurement : measurements) {
            windDirection.add(measurement.getWindDirection());
        }

        return windDirection;
    }

    public ArrayList<Double> getWindSpeed() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> windSpeed = new ArrayList<>();

        for (Measurement measurement : measurements) {
            windSpeed.add(measurement.getWindSpeed());
        }

        return windSpeed;
    }

    public ArrayList<Double> getHeatIndex() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> heatIndex = new ArrayList<>();

        for (Measurement measurement : measurements) {
            heatIndex.add(measurement.getHeatIndex());
        }

        return heatIndex;
    }

    public ArrayList<Double> getWindChill() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> windChill = new ArrayList<>();

        for (Measurement measurement : measurements) {
            windChill.add(measurement.getWindChill());
        }

        return windChill;
    }

    public ArrayList<Double> getDewpoint() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> dewPoint = new ArrayList<>();

        for (Measurement measurement : measurements) {
            dewPoint.add(measurement.getDewPoint());
        }

        return dewPoint;
    }


    // Calculators
    public static double getAverage(ArrayList<Double> numbers) {
        double sum = 0, avg;
        int amount = 0;

        for (double number : numbers) {
            sum += number;
            amount++;
        }

        amount = (amount == 0) ? 1 : amount;
        avg = sum / amount;

        return avg;
    }

    public static double getHighest(ArrayList<Double> numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        double highest = numbers.get(0);

        for (double number : numbers) {
            if (number > highest) {
                highest = number;
            }
        }

        return highest;
    }

    public static double getLowest(ArrayList<Double> numbers) {
        double lowest = numbers.get(0);

        for (double number : numbers) {
            if (number < lowest) {
                lowest = number;
            }
        }

        return lowest;
    }

    public static double getModus(ArrayList<Double> numbers) {
        Map<Double, Integer> amountOfTimes = new HashMap<>();
        for (double number : numbers) {
            if (amountOfTimes.get(number) == null) {
                amountOfTimes.put(number, 1);
            } else {
                amountOfTimes.put(number, amountOfTimes.get(number) + 1);
            }
        }

        double key = amountOfTimes.entrySet().iterator().next().getKey();
        int value = amountOfTimes.entrySet().iterator().next().getValue();

        for (Map.Entry<Double, Integer> entry : amountOfTimes.entrySet()) {
            if (entry.getValue() > value) {
                value = entry.getValue();
                key = entry.getKey();
            }
        }

        return key;
    }

    public static double getMedian(ArrayList<Double> numbers) {
        Collections.sort(numbers);
        double median = 0.0;
        int amount = numbers.size();

        if (numbers.size() % 2 == 0) {                                // Is het even of oneven?
            median = ((numbers.get((numbers.size() / 2) - 1) + (numbers.get(numbers.size() / 2)) / amount));  // gemiddelde van de twee middelste waarden.
        } else if (numbers.size() % 2 == 1) {
            median = numbers.get((numbers.size() / 2) - 1);   // de middelste waarde.
        }

        return median;
    }

    public static double getStandardDeviation(ArrayList<Double> numbers) {
        double average = getAverage(numbers);
        double total = 0;

        for (double number : numbers) {
            total += Math.pow((number - average), 2);
        }
        double varience = total / (numbers.size() - 1);
        varience = Math.sqrt(varience);

        return varience;
    }

    /**
     * Todo
     */
    public ArrayList<Period> hasHeatWave() {
        return null;
    }

    /**
     * Todo
     */
    public Period longestDraught() {
        return new Period();
    }

    /**
     * Todo more methods
     */
    private int year = 2020;

    public double getRainfall(ArrayList<Double> numbers) {// Berekent het totaal van alle regen dat is gevallen.

        double sumOfRainfall = 0.0;
        for (double number : numbers) {
            sumOfRainfall = (sumOfRainfall + (number / 60));            // delen door zestig, omdat elke waarde in mm/h staan en om de minuut 1 waarde geeft.
        }                                                              // mm/h / 60 = mm/minuut. waarden wordt per minuut gegeven dus wordt aleen mm gepakt.

        return sumOfRainfall;
    }

    public double getRainfallMonths() { //Voegt alle waarden van rainRate van de maand toe aan rainfall.
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> rainfall = new ArrayList<>();


        for (Measurement measurement : measurements) {
            rainfall.add(measurement.getRainRate());
        }
        return getRainfall(rainfall);
    }

    private boolean isLeapYear() {

        if ((year % 100 == 0 && year % 400 == 0) || ((year % 4 == 0 && year % 100 != 0 && year % 400 != 0))) { // checkt if het jaar een schrikkeljaar is of niet.
            return true;
        } else {
            return false;
        }
    }

    public Month mostRainfall() {
        ArrayList<Period> months = new ArrayList<>();

        int lengthFebruary;
        if (isLeapYear()) {
            lengthFebruary = 29;
        } else {
            lengthFebruary = 28;
        }

        Period january = new Period(LocalDate.of(year, Month.JANUARY, 1), LocalDate.of(year, Month.JANUARY, 31));
        Period february = new Period(LocalDate.of(year, Month.FEBRUARY, 1), LocalDate.of(year, Month.FEBRUARY, lengthFebruary));
        Period march = new Period(LocalDate.of(year, Month.MARCH, 1), LocalDate.of(year, Month.MARCH, 31));
        Period april = new Period(LocalDate.of(year, Month.APRIL, 1), LocalDate.of(year, Month.APRIL, 30));
        Period may = new Period(LocalDate.of(year, Month.MAY, 1), LocalDate.of(year, Month.MAY, 31));
        Period june = new Period(LocalDate.of(year, Month.JUNE, 1), LocalDate.of(year, Month.JUNE, 30));
        Period july = new Period(LocalDate.of(year, Month.JULY, 1), LocalDate.of(year, Month.JULY, 31));
        Period august = new Period(LocalDate.of(year, Month.AUGUST, 1), LocalDate.of(year, Month.AUGUST, 31));
        Period september = new Period(LocalDate.of(year, Month.SEPTEMBER, 1), LocalDate.of(year, Month.SEPTEMBER, 30));
        Period october = new Period(LocalDate.of(year, Month.OCTOBER, 1), LocalDate.of(year, Month.OCTOBER, 31));
        Period november = new Period(LocalDate.of(year, Month.NOVEMBER, 1), LocalDate.of(year, Month.NOVEMBER, 30));
        Period december = new Period(LocalDate.of(year, Month.DECEMBER, 1), LocalDate.of(year, Month.DECEMBER, 31));

        Collections.addAll(months, january, february, march, april, may, june, july, august, september, october, november, december); // voegt alle periodes aan months.

        ArrayList<Double> rainfall = new ArrayList<>();

        //        ArrayList<Double> test = new ArrayList<>();
        //        Collections.addAll(test, 13.8, 10.7, 14.0, 3.0, 5.2, 16.6, 20.4, 24.6, 30.6, 38.6, 44.5, 52.4);

        Month greatestRainfallMonth;

        if (year >= 2009) {
            for (Period period : months) {
                rainfall.add(period.getRainfallMonths()); //voegt alle omgerekende waarden aan rainfall
            }

            if (january.getRainfallMonths() == getHighest(rainfall)) {  //kijkt in welke maand het meest heeft geregend.
                greatestRainfallMonth = Month.JANUARY;
            } else if (february.getRainfallMonths() == getHighest(rainfall)) {
                greatestRainfallMonth = Month.FEBRUARY;
            } else if (march.getRainfallMonths() == getHighest(rainfall)) {
                greatestRainfallMonth = Month.MARCH;
            } else if (april.getRainfallMonths() == getHighest(rainfall)) {
                greatestRainfallMonth = Month.APRIL;
            } else if (may.getRainfallMonths() == getHighest(rainfall)) {
                greatestRainfallMonth = Month.MAY;
            } else if (june.getRainfallMonths() == getHighest(rainfall)) {
                greatestRainfallMonth = Month.JUNE;
            } else if (july.getRainfallMonths() == getHighest(rainfall)) {
                greatestRainfallMonth = Month.JULY;
            } else if (august.getRainfallMonths() == getHighest(rainfall)) {
                greatestRainfallMonth = Month.AUGUST;
            } else if (september.getRainfallMonths() == getHighest(rainfall)) {
                greatestRainfallMonth = Month.SEPTEMBER;
            } else if (october.getRainfallMonths() == getHighest(rainfall)) {
                greatestRainfallMonth = Month.OCTOBER;
            } else if (november.getRainfallMonths() == getHighest(rainfall)) {
                greatestRainfallMonth = Month.NOVEMBER;
            } else {
                greatestRainfallMonth = Month.DECEMBER;
            }

//        if (test.get(0) == getHighest(test)){  //kijkt in welke maand het meest heeft geregend.
//            greatestRainfallMonth = Month.JANUARY;
//        }else if (test.get(1) == getHighest(test)){
//            greatestRainfallMonth = Month.FEBRUARY;
//        }else if (test.get(2) == getHighest(test)){
//            greatestRainfallMonth = Month.MARCH;
//        }else if (test.get(3) == getHighest(test)){
//            greatestRainfallMonth = Month.APRIL;
//        }else if (test.get(4) == getHighest(test)){
//            greatestRainfallMonth = Month.MAY;
//        }else if (test.get(5) == getHighest(test)){
//            greatestRainfallMonth = Month.JUNE;
//        }else if (test.get(6) == getHighest(test)){
//            greatestRainfallMonth = Month.JULY;
//        }else if (test.get(7) == getHighest(test)){
//            greatestRainfallMonth = Month.AUGUST;
//        }else if (test.get(8) == getHighest(test)){
//            greatestRainfallMonth = Month.SEPTEMBER;
//        }else if (test.get(9) == getHighest(test)){
//            greatestRainfallMonth = Month.OCTOBER;
//        }else if (test.get(10) == getHighest(test)){
//            greatestRainfallMonth = Month.NOVEMBER;
//        }else{
//            greatestRainfallMonth = Month.DECEMBER;
//        }

            return greatestRainfallMonth; // returned de maand waarin het het meest heeft geregend
        } else {
            return greatestRainfallMonth = null;
        }
    }

    public double consecutiveRain(int choice) {
        double k;
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> rainRate = new ArrayList<>();

        for (Measurement measurement : measurements) {
            rainRate.add(measurement.getRainRate());
        }

        int grootsteConsecutiveDays = 0;
        int consecutiveDays = 0;
        double mmGevallen = 0;
        double totaalMmGevallen = 0;

        for (int i = 0; i < rainRate.size(); i++) {
            k = rainRate.get(i);

            if (k == 0) {
                consecutiveDays = 0;
                mmGevallen = 0;
            } else {
                mmGevallen += ConsecutiveRainCal.berekenRegen(k);
                consecutiveDays++;
            }
            if (consecutiveDays > grootsteConsecutiveDays) {
                grootsteConsecutiveDays = consecutiveDays;
                totaalMmGevallen = mmGevallen;
            }
        }
        if (choice == 1) {
            return grootsteConsecutiveDays;
        } else {
            return totaalMmGevallen;
        }
    }


    public ArrayList<ArrayList<Measurement>> divideMeasurementsInDays(ArrayList<Measurement> measurements) {
//        Arraylist gevuld met een arraylist met alle data van één dag
        ArrayList<ArrayList<Measurement>> sortedMeasurements = new ArrayList<>();

        int dayOfYear = -1;
        int days = -1;
        for (Measurement measurement : measurements) {
            // Maak een nieuwe arraylist aan voor een nieuwe dag
            if (dayOfYear != measurement.getDateStamp().getDayOfYear()) {
                dayOfYear = measurement.getDateStamp().getDayOfYear();
                sortedMeasurements.add(new ArrayList<Measurement>());
                days++;
            }

            sortedMeasurements.get(days).add(measurement);
        }
        // Verwijder 2 uur aan data van de dag ervoor
        sortedMeasurements.remove(0);

        return sortedMeasurements;
    }

    public void getBiggestDifferenceMinMaxTemperature() {
        ArrayList<ArrayList<Measurement>> measurementsInDays = new ArrayList<>();
        measurementsInDays = divideMeasurementsInDays(getMeasurements());

        ArrayList<Double> temperatures = new ArrayList<>();
        double tempHeighest = 0;
        double tempLowest = 0;
        double heighest = 0;
        double lowest = 0;
        double biggestDifference = 0;

        LocalDate date = measurementsInDays.get(0).get(0).getDateStamp().toLocalDate();

        int index = 0;
        for (ArrayList<Measurement> singleDay : measurementsInDays) {
            temperatures.clear();
            for (Measurement measurement : singleDay) {
                temperatures.add(measurement.getOutsideTemp());
            }
            tempHeighest = Period.getHighest(temperatures);
            tempLowest = Period.getLowest(temperatures);

            if (biggestDifference < (tempHeighest - tempLowest)) {
                heighest = tempHeighest;
                lowest = tempLowest;
                biggestDifference = heighest - lowest;
                date = singleDay.get(index).getDateStamp().toLocalDate();
            }
            index++;
        }

        System.out.println("The biggest temperature difference was on " + date);
        System.out.println("With the difference " + biggestDifference);
        System.out.println("Heighest temperature: " + heighest + " and lowest " + lowest);
    }
}