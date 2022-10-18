import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static java.time.LocalDate.of;

public class Opdracht2Yuichi {
    public static void main(String[] args) {
        Opdracht2Yuichi minMaxTemp = new Opdracht2Yuichi(100);
        minMaxTemp.getBiggestDifferenceMinMaxTemperature();
        System.out.println("\n\n");
        minMaxTemp.getBiggestDifferenceMinMaxTemperatureNew();
    }

    private LocalDate beginPeriod;
    private LocalDate endPeriod;
    private Period period;
    private ArrayList<Measurement> measurements = new ArrayList<>();
    private ArrayList<Double> temperatures = new ArrayList<>();

    public Opdracht2Yuichi() {
        beginPeriod = LocalDate.now();
        endPeriod = LocalDate.now();
    }

    public Opdracht2Yuichi(Period period) {
        this.period = period;
        this.measurements = period.getMeasurements();

        for (Measurement measurement : measurements) {
            this.temperatures.add(measurement.getOutsideTemp());
        }
    }

    public Opdracht2Yuichi(LocalDate beginPeriod, LocalDate endPeriod) {
        this.beginPeriod = beginPeriod;
        this.endPeriod = endPeriod;
    }

    public Opdracht2Yuichi(LocalDate beginPeriod) {
        this.beginPeriod = beginPeriod;
        this.endPeriod = LocalDate.now();
    }

    public Opdracht2Yuichi(int days) {
        this.beginPeriod = LocalDate.now().minus(java.time.Period.ofDays(days));
        this.endPeriod = LocalDate.now();

//        2 weken waarvan sommige dagen niet valide zijn
//        this.beginPeriod = LocalDate.of(2022, 8, 21);
//        this.endPeriod = beginPeriod.plusDays(14);
    }

    public long numberOfDays() {
        return ChronoUnit.DAYS.between(beginPeriod, endPeriod);
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

    // 365 dagen - 13929ms, 14388ms, 14386ms, 14135ms, 14149ms
    // 3650 dagen - 88181ms
    public void getBiggestDifferenceMinMaxTemperatureNew() {
        Instant startTime = Instant.now(); // Timer
        Period period = new Period((int) numberOfDays()); // temp

        ArrayList<ArrayList<Measurement>> measurementsInDays = new ArrayList<>();
        measurementsInDays = divideMeasurementsInDays(period.getMeasurements());

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

        // timer
        Instant endTime = Instant.now();
        System.out.println(Duration.between(startTime, endTime).toMillis());

        System.out.println("The biggest temperature difference was on " + date);
        System.out.println("With the difference " + biggestDifference);
        System.out.println("Heighest temperature: " + heighest + " and lowest " + lowest);
    }

    // 365 dagen - 63917ms, 42093ms, 55444ms, 55946ms, 57879ms
    // 3650 dagen - 550726ms
    // Werkt niet bij '0' dagen
    public void getBiggestDifferenceMinMaxTemperature() {
        // timer
        Instant startTime = Instant.now();

        long numberOfDays = this.numberOfDays();
        Period period;
        LocalDate date = beginPeriod;
        double tempHeighest = 0;
        double tempLowest = 0;
        double heighest = 0;
        double lowest = 0;
        double biggestDifference = 0;

        // Ga alle dagen af
        for (int i = 0; i < numberOfDays; i++) {
            // Maak een period aan van 1 dag lang
            period = this.createSinglePeriod(beginPeriod.plusDays(i));

            // Check of er records zijn in de period
            if (!period.getMeasurements().isEmpty()) {
                tempHeighest = period.getHighestOutsideTemperature();
                tempLowest = period.getLowestOutsideTemptemperature();

                // Sla alles op als het verschil groter is
                if (biggestDifference < (tempHeighest - tempLowest)) {
                    heighest = tempHeighest;
                    lowest = tempLowest;
                    biggestDifference = heighest - lowest;
                    date = beginPeriod.plusDays(i);
                }
            } else {
                System.out.println("No valid data for " + beginPeriod.plusDays(i));
            }
        }

        // timer
        Instant endTime = Instant.now();
        System.out.println(Duration.between(startTime, endTime).toMillis());

        System.out.println("The biggest temperature difference was on " + date);
        System.out.println("With the difference " + biggestDifference);
        System.out.println("Heighest temperature: " + heighest + " and lowest " + lowest);
    }

    // Maak een period van 1 dag
    private Period createSinglePeriod(LocalDate date) {
        return new Period(date, date);
    }
}
