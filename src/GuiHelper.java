import java.util.ArrayList;
import java.util.Arrays;

public class GuiHelper {
    // IO.init(); moet wel gedaan worden

    // Displays bevatten de adressen van de segment displays (van rechts naar links)
    public final static ArrayList<Integer> display1 = new ArrayList<>(Arrays.asList(0x10, 0x12, 0x14, 0x16, 0x18));
    public final static ArrayList<Integer> display2 = new ArrayList<>(Arrays.asList(0x20, 0x22, 0x24));
    public final static ArrayList<Integer> display3 = new ArrayList<>(Arrays.asList(0x30, 0x32, 0x34));
    // Alle getallen met een punt erachter ('0.', '1.', '2.', etc.)
    private final static int[] lookUpTable = {0b110111111, 0b110000110, 0b111011011, 0b111001111, 0b111100110,
            0b111101101, 0b111111101, 0b110000111, 0b111111111, 0b111101111};

    // Laat string op DMDisplay zien
    public static void displayString(String string) {
        char character;
        for (int i = 0; i < string.length(); i++) {
            character = string.charAt(i);
            IO.writeShort(0x40, character);
        }
    }

    // Support negatieve en positieve getallen met decimalen
    // Snijdt aan de linkerkant af als het getal te groot is (-4242572372 -> 72372)
    public static void displayDoubleNumber(ArrayList<Integer> display, double number, int decimals) {
        // Gebruik de displayNumber die alleen gehele nummers support bij 0 decimalen
        if (decimals == 0) {
            improvedDisplayNumber(display, (int) Math.round(number));
            return;
        }

        int prod;
        boolean finalLoop = false;
        boolean negative = false;
        int iteration = 0;

        // Maak number positief
        if (number < 0) {
            negative = true;
            number = -number;
        }

//        number = number * Math.pow(10, decimals);
        // Math.pow gebruikt blijkbaar erg veel 'power', daarom een for loopje gebruikt
        // Maak van het nummer een niet kommagetal
        int zeroes = 1;
        for (int i = 0; i < decimals; i++) zeroes *= 10;

        number = number * zeroes;
        int roundedNumber = (int) Math.round(number);

        for (Integer adress : display) {

            prod = roundedNumber % 10;
            if (iteration == decimals) {
                // lookUpTable bevat de juiste bits om een getal met punt erachter te zetten
                IO.writeShort(adress, lookUpTable[prod]);
            } else {
                IO.writeShort(adress, prod);
            }

            // Stopt de loop, als het negatief is komt er een - voor
            if (finalLoop) {
                if (negative) {
                    // adress + 2 gaat naar het volgende adres
                    if (iteration == decimals) IO.writeShort(adress + 2, 0b101000000);
                    else IO.writeShort(adress, 0b101000000);
                }
                break;
            }

            roundedNumber = (roundedNumber - prod) / 10;

            if (roundedNumber == 0) {
                finalLoop = true;
            }
            iteration++;
        }
    }

    // Support negatieve en positieve gehele getallen
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

    // Originele manier om getallen te tonen
    // Support alleen gehele positieve getallen
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
        clearDMDisplay();
    }

    public static void clearDisplay(ArrayList<Integer> display) {
        for (Integer adress : display) {
            clearDisplay(adress);
        }
    }

    public static void clearDisplay(int adress) {
        IO.writeShort(adress, 0b100000000);
    }

    public static void clearDMDisplay() {
        IO.writeShort(0x40, 0xFE);
        IO.writeShort(0x40, 0x01);
    }
}
