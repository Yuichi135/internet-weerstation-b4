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
            ArrayList<Measurement> measurements = new ArrayList<>();
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


}
