import java.lang.reflect.Array;
import java.time.*;
import java.time.temporal.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to contain a period of time
 *
 * @author Johan Talboom
 * @version 2.0
 */
public class Period {
    private LocalDate beginPeriod;
    private LocalDate endPeriod;

    /**
     * default constructor, sets the period to today
     */
    public Period() {
        beginPeriod = LocalDate.now();
        endPeriod = LocalDate.now();
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
        ArrayList<Measurement> measurements = new ArrayList<>();
        ArrayList<RawMeasurement> rawMeasurements = getRawMeasurements();
        for (RawMeasurement rawMeasurement : rawMeasurements) {
            Measurement measurement = new Measurement(rawMeasurement);
            if (measurement.isValid()) {
                measurements.add(measurement);
            }
        }
        return measurements;
    }


    /**
     * todo
     *
     * @return
     */

    // Average
    public double getAverageOutsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getOutsideTemp());
        }
        return getAverage(temperatures);
    }

    public double getAverageInsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getInsideTemp());
        }
        return getAverage(temperatures);
    }

    public double getAverageAirPressure() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airPressures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airPressures.add(measurement.getAirPressure());
        }
        return getAverage(airPressures);
    }

    public double getAverageOutsideHumdity() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airPressures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airPressures.add((double) measurement.getOutsideHumidity());
        }
        return getAverage(airPressures);
    }


    // Highest
    public double getHighestOutsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getOutsideTemp());
        }
        return getHighest(temperatures);
    }

    public double getHighestInsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getInsideTemp());
        }
        return getHighest(temperatures);
    }

    public double getHighestAirPressure() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airPressures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airPressures.add(measurement.getAirPressure());
        }
        return getHighest(airPressures);
    }

    public double getHighestOutsideHumdity() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airPressures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airPressures.add((double) measurement.getOutsideHumidity());
        }
        return getHighest(airPressures);
    }


    // Lowest
    public double getLowestOutsideTemptemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getOutsideTemp());
        }
        return getLowest(temperatures);
    }

    public double getLowestInsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getInsideTemp());
        }
        return getLowest(temperatures);
    }

    public double getLowestAirPressure() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airPressures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airPressures.add(measurement.getAirPressure());
        }
        return getLowest(airPressures);
    }

    public double getLowestOutsideHumdity() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airPressures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airPressures.add((double) measurement.getOutsideHumidity());
        }
        return getLowest(airPressures);
    }


    // Modus
    public double getModusOutsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getOutsideTemp());
        }
        return getModus(temperatures);
    }

    public double getModusInsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getInsideTemp());
        }
        return getModus(temperatures);
    }

    public double getModusAirPressure() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airPressures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airPressures.add(measurement.getAirPressure());
        }
        return getModus(airPressures);
    }

    public double getModusOutsideHumdity() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airPressures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airPressures.add((double) measurement.getOutsideHumidity());
        }
        return getModus(airPressures);
    }


    // Median
    public double getMedianOutsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getOutsideTemp());
        }

        return getMedian(temperatures);
    }

    public double getMedianInsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getInsideTemp());
        }
        return getMedian(temperatures);
    }

    public double getMedianAirPressure() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airPressures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airPressures.add(measurement.getAirPressure());
        }
        return getMedian(airPressures);
    }

    public double getMedianOutsideHumdity() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airPressures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airPressures.add((double) measurement.getOutsideHumidity());
        }
        return getMedian(airPressures);
    }

    // StandardDeviation
    public double getStandardDeviationOutsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getOutsideTemp());
        }
        return getStandardDeviation(temperatures);
    }

    public double getStandardDeviationInsideTemperature() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> temperatures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            temperatures.add(measurement.getInsideTemp());
        }
        return getStandardDeviation(temperatures);
    }

    public double getStandardDeviationInsideAirpressure() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airPressures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airPressures.add(measurement.getAirPressure());
        }
        return getStandardDeviation(airPressures);
    }

    public double getStandardDeviationOutsideHumdity() {
        ArrayList<Measurement> measurements = getMeasurements();
        ArrayList<Double> airPressures = new ArrayList<>();

        for (Measurement measurement : measurements) {
            airPressures.add((double) measurement.getOutsideHumidity());
        }
        return getStandardDeviation(airPressures);
    }


    // Calculators
    private double getAverage(ArrayList<Double> numbers) {
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

    private double getHighest(ArrayList<Double> numbers) {
        double highest = numbers.get(0);

        for (double number : numbers) {
            if (number > highest) {
                highest = number;
            }
        }

        return highest;
    }

    private double getLowest(ArrayList<Double> numbers) {
        double lowest = numbers.get(0);

        for (double number : numbers) {
            if (number < lowest) {
                lowest = number;
            }
        }

        return lowest;
    }

    private double getModus(ArrayList<Double> numbers) {
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

    private double getMedian(ArrayList<Double> numbers) {
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

    private double getStandardDeviation(ArrayList<Double> numbers) {
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



    public Month mostRainfall(){
        ArrayList<Period> months = new ArrayList<>();
        int year = 2015;

        Period january = new Period(LocalDate.of(year,Month.JANUARY,1),LocalDate.of(year,Month.JANUARY,31));
        Period february = new Period(LocalDate.of(year,Month.FEBRUARY,1 ),LocalDate.of(year,Month.FEBRUARY,28));
        Period march = new Period(LocalDate.of(year,Month.MARCH,1),LocalDate.of(year,Month.MARCH,31));
        Period april = new Period(LocalDate.of(year,Month.APRIL,1),LocalDate.of(year,Month.APRIL,30));
        Period may = new Period(LocalDate.of(year,Month.MAY,1),LocalDate.of(year,Month.MAY,31));
        Period june = new Period(LocalDate.of(year,Month.JUNE,1),LocalDate.of(year,Month.JUNE,30));
        Period july = new Period(LocalDate.of(year,Month.JULY,1),LocalDate.of(year,Month.JULY,31));
        Period august = new Period(LocalDate.of(year,Month.AUGUST,1),LocalDate.of(year,Month.AUGUST,31));
        Period september = new Period(LocalDate.of(year,Month.SEPTEMBER,1),LocalDate.of(year,Month.SEPTEMBER,30));
        Period october = new Period(LocalDate.of(year,Month.OCTOBER,1),LocalDate.of(year,Month.OCTOBER,31));
        Period november = new Period(LocalDate.of(year,Month.NOVEMBER,1),LocalDate.of(year,Month.NOVEMBER,30));
        Period december = new Period(LocalDate.of(year,Month.DECEMBER,1),LocalDate.of(year,Month.DECEMBER,31));

        Collections.addAll(months, january, february, march, april, may, june, july, august, september, october,november,december); // voegt alle periodes aan months.

        ArrayList<Double> rainfall = new ArrayList<>();
        Month greatestRainfallMonth;

        for (Period period:months) {
            rainfall.add(period.getRainfallMonths()); //voegt alle omgerekende waarden aan rainfall
        }

        if (january.getRainfallMonths() == getHighest(rainfall)){  //kijkt in welke maand het meest heeft geregend.
            greatestRainfallMonth = Month.JANUARY;
        }else if (february.getRainfallMonths() == getHighest(rainfall)){
            greatestRainfallMonth = Month.FEBRUARY;
        }else if (march.getRainfallMonths() == getHighest(rainfall)){
            greatestRainfallMonth = Month.MARCH;
        }else if (april.getRainfallMonths() == getHighest(rainfall)){
            greatestRainfallMonth = Month.APRIL;
        }else if (may.getRainfallMonths() == getHighest(rainfall)){
            greatestRainfallMonth = Month.MAY;
        }else if (june.getRainfallMonths() == getHighest(rainfall)){
            greatestRainfallMonth = Month.JUNE;
        }else if (july.getRainfallMonths() == getHighest(rainfall)){
            greatestRainfallMonth = Month.JULY;
        }else if (august.getRainfallMonths() == getHighest(rainfall)){
            greatestRainfallMonth = Month.AUGUST;
        }else if (september.getRainfallMonths() == getHighest(rainfall)){
            greatestRainfallMonth = Month.SEPTEMBER;
        }else if (october.getRainfallMonths() == getHighest(rainfall)){
            greatestRainfallMonth = Month.OCTOBER;
        }else if (november.getRainfallMonths() == getHighest(rainfall)){
            greatestRainfallMonth = Month.NOVEMBER;
        }else{
            greatestRainfallMonth = Month.DECEMBER;
        }
        return greatestRainfallMonth; // returned de maand waarin het het meest heeft geregend
    }

}
