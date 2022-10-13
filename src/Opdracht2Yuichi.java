import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static java.time.LocalDate.of;

public class Opdracht2Yuichi {
    public static void main(String[] args) {
        Opdracht2Yuichi minMaxTemp = new Opdracht2Yuichi(3650);
        minMaxTemp.getBiggestDifferenceMinMaxTemperature();
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
