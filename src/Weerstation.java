import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Weerstation {

    public static void main(String[] args) {
        Weerstation weerstation = new Weerstation();
        weerstation.init();
}

    private Period period;
    private boolean redButton;
    private boolean blueButtonRight;
    private boolean blueButtonLeft;
    private int selectedItem = 0;

    public Weerstation() {
        IO.init();
        GuiHelper.clearAllDisplays();
    }

    public void init() {
//        int days = readDateFromTerminal();
        int days = 365;
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
        GuiHelper.clearDMDisplay();
        int availableLines = 3;

        if (!header.equals("")) {
            GuiHelper.displayString(header + "\n");
            availableLines = 2;
        }

        for (int i = 0; i < availableLines; i++) {
            GuiHelper.displayString(menuOptions.get((selectedItem + i) % menuOptions.size()) + "\n");
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
                GuiHelper.clearAllDisplays();
                GuiHelper.displayString("Manier bekijken\nom te laten zien");

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
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Period methodes \nnog niet aangemaakt \nRegenRate");
                break;
            case "Zonsopgang/ondergang" :
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Period methodes \nnog niet aangemaakt \nZonsopgang/ondergang");
                break;
            case "Windchill" :
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Period methodes \nnog niet aangemaakt \nWindchill");
                break;
            case "Heat index" :
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Period methodes \nnog niet aangemaakt \nHeat index");
                break;
            case "Dewpoint" :
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Period methodes \nnog niet aangemaakt \nDewpoint");
                break;
            case "Individueel" :
                individueelMenu();
                break;

                // Submenu's
            // Temperatuur
            case "Buiten temperatuur" :
                GuiHelper.displayDoubleNumber(GuiHelper.display1, period.getAverageOutsideTemperature(), 2);
                GuiHelper.displayDoubleNumber(GuiHelper.display2, period.getHighestOutsideTemperature(), 1);
                GuiHelper.displayDoubleNumber(GuiHelper.display3, period.getLowestOutsideTemperature(), 1);

                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Max - Gemiddeld - Min\nBuiten temperatuur\nin graden Celsius");
                break;
            case "Binnen temperatuur" :
                GuiHelper.displayDoubleNumber(GuiHelper.display1, period.getAverageInsideTemperature(), 2);
                GuiHelper.displayDoubleNumber(GuiHelper.display2, period.getHighestInsideTemperature(), 1);
                GuiHelper.displayDoubleNumber(GuiHelper.display3, period.getLowestInsideTemperature(), 1);

                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Max - Gemiddeld - Min\nBinnen temperatuur\nin graden Celsius");
                break;

                // Luchtvochtigheid
            case "Buiten" :
                GuiHelper.displayDoubleNumber(GuiHelper.display1, period.getAverageOutsideHumidity(), 0);
                GuiHelper.displayDoubleNumber(GuiHelper.display2, period.getHighestOutsideHumidity(), 0);
                GuiHelper.displayDoubleNumber(GuiHelper.display3, period.getLowestOutsideHumidity(), 0);

                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Max - Gemiddeld - Min\nLuchtvochtigheid buiten\nin procenten");
                break;
            case "Binnen" :
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Period methodes \nnog niet aangemaakt \nhum in");
                break;

                // Wind
            case "Windsnelheid" :
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Period methodes \nnog niet aangemaakt \nwind");
                break;
            case "Windrichting" :
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Period methodes \nnog niet aangemaakt \nwind dir");
                break;
            case "Sam" :
                individueleOpdrachtSam();
                break;
            case "Sander" :

                break;
            case "Yuichi" :
                individueleOpdrachtYuichi();
                break;
            case "Rick" :
                GuiHelper.displayDoubleNumber(GuiHelper.display1, period.consecutiveRain(0), 2);
                GuiHelper.displayDoubleNumber(GuiHelper.display2, period.consecutiveRain(1), 0);

                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Minutes - Rain mm \n");
                break;
            default:
                GuiHelper.clearAllDisplays();
                GuiHelper.displayString("Functie bestaat niet");
                System.out.println("?");
        }

        while (true) {
            // Rode knop - Ga terug naar het hoofd menu
            if (hasBooleanChanged(redButton, (IO.readShort(0x80) != 0))) {
                redButton = !redButton;

                System.out.println("Quit");
                GuiHelper.clearAllDisplays();
                break;
            }
        }

        mainMenu();
    }

    public void individueleOpdrachtYuichi() {
        Period biggestDiffTemp = new Period(period.getBiggestDifferenceMinMaxTemperature(), period.getBiggestDifferenceMinMaxTemperature());
        double difference = biggestDiffTemp.getHighestOutsideTemperature() - biggestDiffTemp.getLowestOutsideTemperature();

        GuiHelper.displayDoubleNumber(GuiHelper.display1, difference, 2);
        GuiHelper.displayDoubleNumber(GuiHelper.display2, biggestDiffTemp.getHighestInsideTemperature(), 1);
        GuiHelper.displayDoubleNumber(GuiHelper.display3, biggestDiffTemp.getLowestOutsideTemperature(), 1);

        GuiHelper.clearDMDisplay();
        GuiHelper.displayString("Max - Verschil  - Min\nTemperatuur celsius\nop " + period.getBiggestDifferenceMinMaxTemperature());
    }
    public void individueleOpdrachtSam() {
        GuiHelper.displayNumber(GuiHelper.display3, period.summer());

        GuiHelper.clearDMDisplay();
        GuiHelper.displayString("Aantal mooie dagen \nin deze periode:");
    }
}
