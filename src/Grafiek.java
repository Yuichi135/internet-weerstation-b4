import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Grafiek {
    public static ArrayList<Integer> display1 = new ArrayList<>();
    public static ArrayList<Integer> display2 = new ArrayList<>();
    public static ArrayList<Integer> display3 = new ArrayList<>();

    public static void main(String[] args) {
        Collections.addAll(display1, 0x10, 0x12, 0x14, 0x16, 0x18);
        Collections.addAll(display2, 0x20, 0x22, 0x24);
        Collections.addAll(display3, 0x30, 0x32, 0x34);
        IO.init();

        clearAllDisplays();

        displayAverageOutsideTemperatureGraph(3650);
    }

    public static void displayAverageOutsideTemperatureGraph(int days) {
        displayString("Average outside \ntemperature over \n" + days + " days");

        ArrayList<Double> averageTemperatures = new ArrayList<>();
        double averageTemperature;
        for (int i = 0; i < days; i++) {
            Period period = createSinglePeriod(LocalDate.now().minusDays(days - i));

            // Inhoud veranderen?
            averageTemperature = period.getAverageOutsideTemperature();
            if (averageTemperature == 0.0) {
                continue;
            }
            averageTemperatures.add(averageTemperature);
        }

        displayGraph(averageTemperatures);
    }

    public static void displayAverageInsideTemperatureGraph(int days) {
        displayString("Average inside \ntemperature over \n" + days + " days");

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
        displayString("Average \nairpressure over \n" + days + " days");

        ArrayList<Double> averageAirPressures = new ArrayList<>();
        double averageAirPressure;
        for (int i = 0; i < days; i++) {
            Period period = createSinglePeriod(LocalDate.now().minusDays(days - i));

            // Inhoud veranderen?
            averageAirPressure = period.getAverageAirPressure();
            if (averageAirPressure == 0.0) {
                continue;
            }
            averageAirPressures.add(averageAirPressure);
        }

        displayGraph(averageAirPressures);
    }

    public static void displayAverageOutsideHumidityGraph(int days) {
        displayString("Average outside \nhumidity over \n" + days + " days");

        ArrayList<Double> averageHumidities = new ArrayList<>();
        double averageHumidity;
        for (int i = 0; i < days; i++) {
            Period period = createSinglePeriod(LocalDate.now().minusDays(days - i));

            // Inhoud veranderen?
            averageHumidity = period.getAverageOutsideHumdity();
            if (averageHumidity == 0.0) {
                continue;
            }
            averageHumidities.add(averageHumidity);
        }

        displayGraph(averageHumidities);
    }

    public static void displayHighestTemperatureGraph(int days) {
        displayString("Highest outside \ntemperature over \n" + days + " days");

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
        displayString("Test graph over \n" + days + " days");

        ArrayList<Double> highestTemperatures = new ArrayList<>();
        double highestTemperature;
        for (int i = 0; i < days; i++) {
            Period period = createSinglePeriod(LocalDate.now().minusDays(days - i));

            // period.x aanpassen
            // if x == 0.0 mogelijk aanpassen (ligt eraan wat er wordt gestuurd in methode x
            highestTemperature = period.getHighestAirPressure();
            if (highestTemperature == 0.0) {
                continue;
            }
            highestTemperatures.add(highestTemperature);
        }

        displayGraph(highestTemperatures);
    }


    // Werkt met percentages AKA kan gebruikt worden op dingen buiten temperatuur :)
    public static void displayGraph(ArrayList<Double> values) {
        double highest = Period.getHighest(values);
        double lowest = Period.getLowest(values);
        double difference = highest - lowest;

        double temp;
        ArrayList<Double> percentages = new ArrayList<>();
        for (double temperature : values) {
            temp = temperature - lowest;

            percentages.add(temp / difference);
        }

        displayNumber(display1, (int) Period.getAverage(values));
        displayNumber(display2, (int) highest);
        displayNumber(display3, (int) lowest);



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
            IO.delay(5);
            relativeX++;
        }
    }

    // Poep code, maar het werkt
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


    // Maak een period van 1 dag
    public static Period createSinglePeriod(LocalDate date) {
        return new Period(date, date);
    }

    public static void displayString(String string) {
        char character;
        for (int i = 0; i < string.length(); i++) {
            character = string.charAt(i);
            IO.writeShort(0x40, character);
            IO.delay(25);
        }
    }

    public static void displayNumber(ArrayList<Integer> display, int number) {
        int prod;

        for (Integer adress : display) {
            prod = number % 10;
            IO.writeShort(adress, prod);
            number = (number - prod) / 10;
        }
    }

    public static void clearAllDisplays() {
        clearDisplay(display1);
        clearDisplay(display2);
        clearDisplay(display3);
        clrDMDisplay();
    }

    public static void clearDisplay(ArrayList<Integer> display) {
        for (Integer adress : display) {
            clearDisplay(adress);
        }
    }

    public static void clearDisplay(int adress) {
        IO.writeShort(adress, 0b100000000);
    }

    public static void clrDMDisplay() {
        IO.writeShort(0x40, 0xFE);
        IO.writeShort(0x40, 0x01);
    }

    public static void displayAxis(int zeroPoint) {
        clrDMDisplay();
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
