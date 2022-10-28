import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Grafiek {

    public static void main(String[] args) {
        IO.init();

        GuiHelper.clearAllDisplays();
        displayAverageOutsideTemperatureGraph(5);
    }

    // Alleen loading bij v toegevoegd
    public static void displayAverageOutsideTemperatureGraph(int days) {
        GuiHelper.displayString("Average outside \ntemperature over \n" + days + " days");
        IO.delay(1500);

        ArrayList<Double> averageTemperatures = new ArrayList<>();
        double averageTemperature;
        for (int i = 0; i < days; i++) {
            displayLoadingScreen(i, days);

            Period period = createSinglePeriod(LocalDate.now().minusDays(days - i));

            // Inhoud veranderen?
            averageTemperature = period.getAverageOutsideTemperature();
            // Filtert ongeldige eruit
            if (averageTemperature == 0.0) {
                continue;
            }
            averageTemperatures.add(averageTemperature);
        }

        displayGraph(averageTemperatures);
    }

    public static void displayAverageInsideTemperatureGraph(int days) {
        GuiHelper.displayString("Average inside \ntemperature over \n" + days + " days");

        ArrayList<Double> averageTemperatures = new ArrayList<>();
        double averageTemperature;
        for (int i = 0; i < days; i++) {
            Period period = createSinglePeriod(LocalDate.now().minusDays(days - i));

            // Inhoud veranderen?
            averageTemperature = period.getAverageInsideTemperature();
            if (averageTemperature == 0.0) {
                continue;
            }
            averageTemperatures.add(averageTemperature);
        }

        displayGraph(averageTemperatures);
    }

    public static void displayAverageAirPressureGraph(int days) {
        GuiHelper.displayString("Average \nairpressure over \n" + days + " days");

        ArrayList<Double> averageAirPressures = new ArrayList<>();
        double averageAirPressure;
        for (int i = 0; i < days; i++) {
            Period period = createSinglePeriod(LocalDate.now().minusDays(days - i));

            // Inhoud veranderen?
            averageAirPressure = period.getAverageAirpressure();
            if (averageAirPressure == 0.0) {
                continue;
            }
            averageAirPressures.add(averageAirPressure);
        }

        displayGraph(averageAirPressures);
    }

    public static void displayAverageOutsideHumidityGraph(int days) {
        GuiHelper.displayString("Average outside \nhumidity over \n" + days + " days");

        ArrayList<Double> averageHumidities = new ArrayList<>();
        double averageHumidity;
        for (int i = 0; i < days; i++) {
            Period period = createSinglePeriod(LocalDate.now().minusDays(days - i));

            // Inhoud veranderen?
            averageHumidity = period.getAverageOutsideHumidity();
            if (averageHumidity == 0.0) {
                continue;
            }
            averageHumidities.add(averageHumidity);
        }

        displayGraph(averageHumidities);
    }

    public static void displayHighestTemperatureGraph(int days) {
        GuiHelper.displayString("Highest outside \ntemperature over \n" + days + " days");

        ArrayList<Double> highestTemperatures = new ArrayList<>();
        double highestTemperature;
        for (int i = 0; i < days; i++) {
            Period period = createSinglePeriod(LocalDate.now().minusDays(days - i));

            // period.x aanpassen
            // if x == 0.0 mogelijk aanpassen (ligt eraan wat er wordt gestuurd in methode x
            highestTemperature = period.getHighestOutsideTemperature();
            if (highestTemperature == 0.0) {
                continue;
            }
            highestTemperatures.add(highestTemperature);
        }

        displayGraph(highestTemperatures);
    }

    public static void displayStandardDeviationOutsideTemperatureGraph(int days) {
        GuiHelper.displayString("Test graph over \n" + days + " days");

        ArrayList<Double> highestTemperatures = new ArrayList<>();
        double highestTemperature;
        for (int i = 0; i < days; i++) {
            Period period = createSinglePeriod(LocalDate.now().minusDays(days - i));

            // period.x aanpassen
            // if x == 0.0 mogelijk aanpassen (ligt eraan wat er wordt gestuurd in methode x
            highestTemperature = period.getHighestAirpressure();
            if (highestTemperature == 0.0) {
                continue;
            }
            highestTemperatures.add(highestTemperature);
        }

        displayGraph(highestTemperatures);
    }

    public static void displayLoadingScreen(int part, int all) {
        GuiHelper.clearDMDisplay();
        GuiHelper.displayString("Collected " + part + " out of \n" +
                all + " records\n" +
                (part * 100) / all + "% done");
    }

    // Maak een period van 1 dag
    public static Period createSinglePeriod(LocalDate date) {
        return new Period(date, date);
    }

    // Werkt met percentages AKA kan gebruikt worden op dingen buiten temperatuur :)
    public static void displayGraph(ArrayList<Double> values) {
        GuiHelper.clearAllDisplays();
        double highest = Period.getHighest(values);
        double lowest = Period.getLowest(values);
        double difference = highest - lowest;

        double temp;
        ArrayList<Double> percentages = new ArrayList<>();
        for (double temperature : values) {
            temp = temperature - lowest;

            percentages.add(temp / difference);
        }

//        displayNumber(display1, (int) Period.getAverage(values));
//        displayNumber(display2, (int) highest);
//        displayNumber(display3, (int) lowest);

//        GuiHelper.displayDoubleNumber(GuiHelper.display1, Period.getAverage(values), 2);
//        GuiHelper.displayDoubleNumber(GuiHelper.display2, highest, 1);
//        GuiHelper.displayDoubleNumber(GuiHelper.display3, lowest, 1);


        values = getFullArray(values);
        percentages = getFullArray(percentages);

        // 0,0 is linksonderin
        int zeroPoint = 0b1000010011100;
        displayAxis(zeroPoint);

        int[] coords = getZeroPointAxis(zeroPoint);
        int x = coords[0];
        int y = coords[1];

        int relativeX = x + 1;
        int relativeY = y;
        int opdcode = 1;


        for (int i = 0; i < values.size(); i++) {
            IO.writeShort(0x42, opdcode << 12 | (relativeX) << 5 | (relativeY - ((int) Math.round(y * percentages.get(i)))));
            relativeX++;
        }
    }

    // Returnt altijd een array met lengte van 123
    public static ArrayList<Double> getFullArray(ArrayList<Double> values) {
        int totalWidth = 123; // breedte van de dot matrix display - 4

        double stepSize = (double) totalWidth / values.size();
        double step = 0;
        ArrayList<Double> fullWidthValues = new ArrayList<>();

        // Om gemiddelde uitrekenen
        double sum;
        int total;
        int i = 0;
        for (int j = 0; j < totalWidth; j++) {
            total = 0;
            sum = 0;
            while (j >= step) {
                step += stepSize;
                i++;
                total++;
                sum += values.get(i - 1);
            }
            // Voorkomt '0 / x' en 'x / 0'
            fullWidthValues.add(((sum == 0) ? values.get(i - 1) : sum) / ((total == 0) ? 1 : total));
        }
        return fullWidthValues;
    }

    public static void displayAxis(int zeroPoint) {
        GuiHelper.clearDMDisplay();
        int x, y;
        int opdcode = 1;
        int[] coords = getZeroPointAxis(zeroPoint);

        // Draw Y axis
        x = coords[0];
        for (y = 0; y < 32; y++) {
            IO.writeShort(0x42, opdcode << 12 | x << 5 | y);
        }

        // Draw X axis
        y = coords[1];
        for (x = 0; x < 128; x++) {
            IO.writeShort(0x42, opdcode << 12 | x << 5 | y);
        }
    }

    public static int[] getZeroPointAxis(int zeroPoint) {
        int x = (zeroPoint ^ (1 << 12)) >> 5;
        int y = (zeroPoint | 0b1111111100000) ^ 0b1111111100000;
        return new int[]{x, y};
    }
}