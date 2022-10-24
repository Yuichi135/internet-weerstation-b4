import java.util.ArrayList;
import java.util.Arrays;

public class GuiHelper {
    public static ArrayList<Integer> display1 = new ArrayList<>(Arrays.asList(0x10, 0x12, 0x14, 0x16, 0x18));
    public static ArrayList<Integer> display2 = new ArrayList<>(Arrays.asList(0x20, 0x22, 0x24));
    public static ArrayList<Integer> display3 = new ArrayList<>(Arrays.asList(0x30, 0x32, 0x34));

    // Laat string op DMDisplay zien
    public static void displayString(String string) {
        char character;
        for (int i = 0; i < string.length(); i++) {
            character = string.charAt(i);
            IO.writeShort(0x40, character);
        }
    }

    // TODO: 0.xxx fixen (laat de eerste 0 niet zien)
    // Support negatieve en positieve getallen met decimalen
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

        // Maak van het nummer een niet kommagetal
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
