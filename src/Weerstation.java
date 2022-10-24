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
        int days = 5;
        this.period = new Period(days);
        this.period.getMeasurements();
        mainMenu();
    }

    public void menu(ArrayList<String> menuOptions, String header) {
        selectedItem = 0;
        redButton = IO.readShort(0x80) != 0;
        blueButtonRight = IO.readShort(0x100) != 0;
        blueButtonLeft = IO.readShort(0x90) != 0;

        displayMenu(header, menuOptions, selectedItem);
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
                displayMenu(header, menuOptions, selectedItem);
            }
            // Linker blauwe knop is ingedrukt
            // Ga naar boven in het menu
            if (hasBooleanChanged(blueButtonLeft, (IO.readShort(0x90) != 0))) {
                blueButtonLeft = !blueButtonLeft;

                selectedItem--;
                if (selectedItem < 0) selectedItem += menuOptions.size();
                displayMenu(header, menuOptions, selectedItem);
            }
        }

        showSelectedMenuItem(menuOptions, selectedItem);
    }

    public void mainMenu() {
        ArrayList<String> menuOptions = new ArrayList<>();
        Collections.addAll(menuOptions, "Luchtdruk", "Temperatuur", "Luchtvochtigheid", "Wind", "Regen",
                "Zonsopgang/ondergang", "Windchill", "Heat index", "Dewpoint", "Individueel");

        menu(menuOptions, "");
    }

    public void temperatureMenu() {
        ArrayList<String> menuOptions = new ArrayList<>();
        Collections.addAll(menuOptions, "Buiten temperatuur", "Binnen temperatuur");

        menu(menuOptions, "Temperatuur");
    }

    public void humidityMenu() {
        ArrayList<String> menuOptions = new ArrayList<>();
        Collections.addAll(menuOptions, "Buiten", "Binnen");

        menu(menuOptions, "Luchtvochtigheid");
    }

    public void windMenu() {
        ArrayList<String> menuOptions = new ArrayList<>();
        Collections.addAll(menuOptions, "Windsnelheid", "Windrichting");

        menu(menuOptions, "Wind");
    }

    public void individueelMenu() {
        ArrayList<String> menuOptions = new ArrayList<>();
        Collections.addAll(menuOptions, "Sam", "Yuichi", "Sander", "Rick");

        menu(menuOptions, "Individuele opdr");
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

    public void displayMenu(String header, ArrayList<String> menuOptions, int selectedItem) {
        clearDMDisplay();
        int availableLines = 3;

        if (!header.equals("")) {
            displayString(header + "\n");
            availableLines = 2;
        }

        for (int i = 0; i < availableLines; i++) {
            displayString(menuOptions.get((selectedItem + i) % menuOptions.size()) + "\n");
        }
        highlightMenuItem(3 - availableLines);
    }

    public void highlightMenuItem(int line) {
        for (int i = 0; i < 128; i++) {
            IO.writeShort(0x42, 1 << 12 | i << 5 | (0 + (line * 10)));
            IO.writeShort(0x42, 1 << 12 | i << 5 | (10 + (line * 10)));
        }

        for (int i = 0; i < 10; i++) {
            IO.writeShort(0x42, 1 << 12 | 0 << 5 | (i + (line * 10)));
            IO.writeShort(0x42, 1 << 12 | 127 << 5 | (i + (line * 10)));
        }
    }

    public void showSelectedMenuItem(ArrayList<String> menuOptions, int index) {
        // De cases moeten overeen komen met de items in menuOptions
        switch (menuOptions.get(selectedItem % menuOptions.size())) {
            case "Luchtdruk":
                System.out.println(period.getAverageAirpressure());
                System.out.println(period.getHighestAirpressure());
                System.out.println(period.getLowestAirpressure());
                clearAll();
                displayString("Manier bekijken\nom te laten zien");

                break;
            case "Temperatuur":
                temperatureMenu();
                break;
            case "Luchtvochtigheid":
                humidityMenu();
                break;
            case "Wind" :
                windMenu();
                break;
            case "Regen" :
                clearDMDisplay();
                displayString("Period methodes \nnog niet aangemaakt \nRegenRate");
                break;
            case "Zonsopgang/ondergang" :
                clearDMDisplay();
                displayString("Period methodes \nnog niet aangemaakt \nZonsopgang/ondergang");
                break;
            case "Windchill" :
                clearDMDisplay();
                displayString("Period methodes \nnog niet aangemaakt \nWindchill");
                break;
            case "Heat index" :
                clearDMDisplay();
                displayString("Period methodes \nnog niet aangemaakt \nHeat index");
                break;
            case "Dewpoint" :
                clearDMDisplay();
                displayString("Period methodes \nnog niet aangemaakt \nDewpoint");
                break;
            case "Individueel" :
                individueelMenu();
                break;

                // Submenu's
            // Temperatuur
            case "Buiten temperatuur" :
                displayDoubleNumber(display1, period.getAverageOutsideTemperature(), 2);
                displayDoubleNumber(display2, period.getHighestOutsideTemperature(), 1);
                displayDoubleNumber(display3, period.getLowestOutsideTemperature(), 1);

                clearDMDisplay();
                displayString("Max - Gemiddeld - Min\nBuiten temperatuur\nin graden Celsius");
                break;
            case "Binnen temperatuur" :
                displayDoubleNumber(display1, period.getAverageInsideTemperature(), 2);
                displayDoubleNumber(display2, period.getHighestInsideTemperature(), 1);
                displayDoubleNumber(display3, period.getLowestInsideTemperature(), 1);

                clearDMDisplay();
                displayString("Max - Gemiddeld - Min\nBinnen temperatuur\nin graden Celsius");
                break;

                // Luchtvochtigheid
            case "Buiten" :
                displayDoubleNumber(display1, period.getAverageOutsideHumidity(), 0);
                displayDoubleNumber(display2, period.getHighestOutsideHumidity(), 0);
                displayDoubleNumber(display3, period.getLowestOutsideHumidity(), 0);

                clearDMDisplay();
                displayString("Max - Gemiddeld - Min\nLuchtvochtigheid buiten\nin procenten");
                break;
            case "Binnen" :
                clearDMDisplay();
                displayString("Period methodes \nnog niet aangemaakt \nhum in");
                break;

                // Wind
            case "Windsnelheid" :
                clearDMDisplay();
                displayString("Period methodes \nnog niet aangemaakt \nwind");
                break;
            case "Windrichting" :
                clearDMDisplay();
                displayString("Period methodes \nnog niet aangemaakt \nwind dir");
                break;
            case "Sam" :

                break;
            case "Sander" :

                break;
            case "Yuichi" :
                individueleOpdrachtYuichi();
                break;
            case "Rick" :
                displayDoubleNumber(display1, period.consecutiveRain(0), 2);
                displayDoubleNumber(display2, period.consecutiveRain(1), 0);

                clearDMDisplay();
                displayString("Minutes - Rain mm \n");
                break;
            default:
                clearAll();
                displayString("Functie bestaat niet");
                System.out.println("?");
        }

        while (true) {
            // Rode knop - Ga terug naar het hoofd menu
            if (hasBooleanChanged(redButton, (IO.readShort(0x80) != 0))) {
                redButton = !redButton;

                System.out.println("Quit");
                clearAll();
                break;
            }
        }

        mainMenu();
    }

    public void individueleOpdrachtYuichi() {
        Period biggestDiffTemp = new Period(period.getBiggestDifferenceMinMaxTemperature(), period.getBiggestDifferenceMinMaxTemperature());
        double difference = biggestDiffTemp.getHighestOutsideTemperature() - biggestDiffTemp.getLowestOutsideTemperature();

        displayDoubleNumber(display1, difference, 2);
        displayDoubleNumber(display2, biggestDiffTemp.getHighestInsideTemperature(), 1);
        displayDoubleNumber(display3, biggestDiffTemp.getLowestOutsideTemperature(), 1);

        clearDMDisplay();
        displayString("Max - Verschil  - Min\nTemperatuur celsius\nop " + period.getBiggestDifferenceMinMaxTemperature());
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
