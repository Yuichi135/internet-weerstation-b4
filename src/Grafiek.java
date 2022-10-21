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
        displayAverageOutsideTemperatureGraph(5);
    }

    // Alleen loading bij v toegevoegd
    public static void displayAverageOutsideTemperatureGraph(int days) {
        displayString("Average outside \ntemperature over \n" + days + " days");
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
            averageAirPressure = period.getAverageAirpressure();
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
            averageHumidity = period.getAverageOutsideHumidity();
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
            highestTemperature = period.getHighestAirpressure();
            if (highestTemperature == 0.0) {
                continue;
            }
            highestTemperatures.add(highestTemperature);
        }

        displayGraph(highestTemperatures);
    }

    public static void displayLoadingScreen(int part, int all) {
        clrDMDisplay();
        displayString("Collected " + part + " out of \n" +
                all + " records\n" +
                (part * 100) / all + "% done");
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

//        displayNumber(display1, (int) Period.getAverage(values));
//        displayNumber(display2, (int) highest);
//        displayNumber(display3, (int) lowest);

        displayDoubleNumber(display1, Period.getAverage(values), 2);
        displayDoubleNumber(display2, highest, 1);
        displayDoubleNumber(display3, lowest, 1);


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
        }
    }

    // TODO: Maximaal aantal decimalen fixen
    public static void displayDoubleNumber(ArrayList<Integer> display, double number, int decimals) {
        int prod;
        boolean finalLoop = false;
        boolean negative = false;
        int iteration = 0;

        // Maak number positief
        if (number < 0) {
            negative = true;
            number = -number;
        }

        number = number * Math.pow(10, decimals);
        int roundedNumber = (int) Math.round(number);

        for (Integer adress : display) {
            // Stopt de loop, als het negatief is komt er een - voor
            if (finalLoop) {
                if (negative) {
                    IO.writeShort(adress, 0b101000000);
                }
                break;
            }

            prod = roundedNumber % 10;
            if (iteration == decimals) {
                // TODO: BETERE MANIER VINDEN
                switch (prod) {
                    case 1:
                        IO.writeShort(adress, 0b110000110); // 1.
                        break;
                    case 2:
                        IO.writeShort(adress, 0b111011011); // 2.
                        break;
                    case 3:
                        IO.writeShort(adress, 0b111001111); // 3.
                        break;
                    case 4:
                        IO.writeShort(adress, 0b111100110); // 4.
                        break;
                    case 5:
                        IO.writeShort(adress, 0b111101101); // 5.
                        break;
                    case 6:
                        IO.writeShort(adress, 0b111111101); // 6.
                        break;
                    case 7:
                        IO.writeShort(adress, 0b110000111); // 7.
                        break;
                    case 8:
                        IO.writeShort(adress, 0b111111111); // 8.
                        break;
                    case 9:
                        IO.writeShort(adress, 0b111101111); // 9.
                        break;
                    case 0:
                        IO.writeShort(adress, 0b110111111); // 0.
                        break;
                    default:
                        break;
                }
            } else {
                IO.writeShort(adress, prod);
            }
            roundedNumber = (roundedNumber - prod) / 10;

            if (roundedNumber == 0) {
                finalLoop = true;
            }
            iteration++;
        }
    }

    // Zonder nullen ervoor en kan negatief
    public static void improvedDisplayNumber(ArrayList<Integer> display, int number) {
        int prod;
        boolean negative = false;
        boolean finalLoop = false;
        clearDisplay(display);

        // Maak number positief
        if (number < 0) {
            negative = true;
            number = -number;
        }

        for (Integer adress : display) {
            // Stopt de loop, als het negatief is komt er een - voor
            if (finalLoop) {
                if (negative) {
                    IO.writeShort(adress, 0b101000000);
                }
                break;
            }
            prod = number % 10;
            IO.writeShort(adress, prod);
            number = (number - prod) / 10;
            if (number == 0) {
                finalLoop = true;
            }
        }
    }

    public static void displayNumber(ArrayList<Integer> display, int number) {
        int prod;
        clearDisplay(display);

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
