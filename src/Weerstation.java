import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Weerstation {

    public static void main(String[] args) {
        Weerstation weerstation = new Weerstation();
        weerstation.init();
}

    public static ArrayList<Integer> display1 = new ArrayList<>();
    public static ArrayList<Integer> display2 = new ArrayList<>();
    public static ArrayList<Integer> display3 = new ArrayList<>();

    private Period period;
//    private boolean redButton = (IO.readShort(0x80) != 0);
    private boolean redButton;
    private boolean blueButtonRight;
    private boolean blueButtonLeft;
    private int selectedItem = 0;

    public Weerstation() {
        Collections.addAll(display1, 0x10, 0x12, 0x14, 0x16, 0x18);
        Collections.addAll(display2, 0x20, 0x22, 0x24);
        Collections.addAll(display3, 0x30, 0x32, 0x34);

        IO.init();
        clearAll();
    }

    public void init() {
//        int days = readDateFromTerminal();
        int days = 175;
        this.period = new Period(days);
        this.period.getMeasurements();
        mainMenu();
    }

    public void menu(ArrayList<String> menuOptions) {
        redButton = IO.readShort(0x80) != 0;
        blueButtonRight = IO.readShort(0x100) != 0;
        blueButtonLeft = IO.readShort(0x90) != 0;

        displayMenu(menuOptions, selectedItem);
        while (true) {
            // Rode knop is ingedrukt
            // Selecteer de optie

            if (hasBooleanChanged(redButton, (IO.readShort(0x80) != 0))) {
                redButton = !redButton;

                break;
            }
            // Rechter blauwe knop is ingedrukt
            // Ga naar onder in het menu
            if (hasBooleanChanged(blueButtonRight, (IO.readShort(0x100) != 0))) {
                blueButtonRight = !blueButtonRight;

                selectedItem++;
                displayMenu(menuOptions, selectedItem);
            }
            // Linker blauwe knop is ingedrukt
            // Ga naar boven in het menu
            if (hasBooleanChanged(blueButtonLeft, (IO.readShort(0x90) != 0))) {
                blueButtonLeft = !blueButtonLeft;

                selectedItem--;
                if (selectedItem < 0) selectedItem += menuOptions.size();
                displayMenu(menuOptions, selectedItem);
            }
        }

        showSelectedMenuItem(menuOptions, selectedItem);
    }

    public void mainMenu() {
        ArrayList<String> menuOptions = new ArrayList<>();
        Collections.addAll(menuOptions, "Luchtdruk", "Buiten temperatuur", "Binnen temperatuur", "Luchtvochtigheid buiten", "Wind", "Regen",
                "Zonsopgang/ondergang", "Windchill", "Heat index", "Dewpoint", "Grafieken");

        menu(menuOptions);
    }

    // TODO: wanneer er een string in wordt gestopt opvangen
    public int readDateFromTerminal() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Kies het aantal dagen");
        return Integer.parseInt(reader.nextLine());
    }

    public boolean hasBooleanChanged(boolean previousValue, boolean newValue) {
        if (previousValue == newValue) return false;
        return true;
    }

    public void displayMenu(ArrayList<String> menuOptions, int selectedItem) {
        clearDMDisplay();
        for (int i = 0; i < 3; i++) {
            displayString(menuOptions.get((selectedItem + i) % menuOptions.size()) + "\n");
        }
        highlightMenuItem();
    }

    public void highlightMenuItem() {
        for (int i = 0; i < 128; i++) {
            IO.writeShort(0x42, 1 << 12 | i << 5 | 0);
            IO.writeShort(0x42, 1 << 12 | i << 5 | 10);
        }

        for (int i = 0; i < 10; i++) {
            IO.writeShort(0x42, 1 << 12 | 0 << 5 | i);
            IO.writeShort(0x42, 1 << 12 | 127 << 5 | i);
        }
    }

    public void showSelectedMenuItem(ArrayList<String> menuOptions, int index) {
        // De cases moeten overeen komen met de items in menuOptions
        switch (menuOptions.get(selectedItem % menuOptions.size())) {
            case "Luchtdruk":
                System.out.println(period.getAverageAirPressure());
                System.out.println(period.getHighestAirPressure());
                System.out.println(period.getLowestAirPressure());
                clearAll();
                displayString("WIP");

                break;
            case "Buiten temperatuur":
                displayDoubleNumber(display1, period.getAverageOutsideTemperature(), 2);
                displayDoubleNumber(display2, period.getHighestOutsideTemperature(), 1);
                displayDoubleNumber(display3, period.getLowestOutsideTemptemperature(), 1);

                clearDMDisplay();
                displayString("Max - Gemiddeld - Min\nBuiten temperatuur\nin graden Celsius");

                break;
            case "Binnen temperatuur":
                displayDoubleNumber(display1, period.getAverageInsideTemperature(), 2);
                displayDoubleNumber(display2, period.getHighestInsideTemperature(), 1);
                displayDoubleNumber(display3, period.getLowestInsideTemperature(), 1);

                clearDMDisplay();
                displayString("Max - Gemiddeld - Min\nBinnen temperatuur\nin graden Celsius");
                break;
            case "Luchtvochtigheid buiten":
                displayDoubleNumber(display1, period.getAverageOutsideHumdity(), 0);
                displayDoubleNumber(display2, period.getHighestOutsideHumdity(), 0);
                displayDoubleNumber(display3, period.getLowestOutsideHumdity(), 0);

                clearDMDisplay();
                displayString("Max - Gemiddeld - Min\nLuchtvochtigheid buiten\nin graden Celsius");
                break;
            default:
                clearAll();
                displayString("Functie is nog niet \ngemaakt");
                System.out.println("?");
        }

        while (true) {
            // Rode knop - Ga terug naar het menu
            if (hasBooleanChanged(redButton, (IO.readShort(0x80) != 0))) {
                redButton = !redButton;

                System.out.println("Quit");
                clearAll();
                break;
            }
            // Rechter blauwe knop is ingedrukt
            // Ga naar het volgende item
            if (hasBooleanChanged(blueButtonRight, (IO.readShort(0x100) != 0))) {
                blueButtonRight = !blueButtonRight;

                selectedItem++;
                showSelectedMenuItem(menuOptions, selectedItem);
            }
            // Linker blauwe knop is ingedrukt
            // Ga naar de vorige item
            if (hasBooleanChanged(blueButtonLeft, (IO.readShort(0x90) != 0))) {
                blueButtonLeft = !blueButtonLeft;

                selectedItem--;
                if (selectedItem < 0) selectedItem += menuOptions.size();
                showSelectedMenuItem(menuOptions, selectedItem);
            }
        }

        menu(menuOptions);
    }

    public void displayString(String string) {
        char character;
        for (int i = 0; i < string.length(); i++) {
            character = string.charAt(i);
            IO.writeShort(0x40, character);
        }
    }

    public static void displayDoubleNumber(ArrayList<Integer> display, double number, int decimals) {
        clearDisplay(display);

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

    public static void clearDisplay(ArrayList<Integer> display) {
        for (Integer adress : display) {
            IO.writeShort(adress, 0b100000000);
        }
    }

    public void clearDMDisplay() {
        IO.writeShort(0x40, 0xFE);
        IO.writeShort(0x40, 0x01);
    }

    public void clearAll() {
        clearDisplay(display1);
        clearDisplay(display2);
        clearDisplay(display3);
        clearDMDisplay();
    }
}
