import java.util.*;

public class Weerstation {

    public static void main(String[] args) {
        Weerstation weerstation = new Weerstation();
        weerstation.init();
    }


    Measurement converted = new Measurement(DatabaseConnection.getMostRecentMeasurement());
    private Period period;
    private RawMeasurement rawMeasurement = DatabaseConnection.getMostRecentMeasurement();
    private boolean redButton;
    private boolean blueButtonRight;
    private boolean blueButtonLeft;
    private int selectedItem = 0;
    private ArrayList<String> graphs = new ArrayList<>(Arrays.asList("Buiten temperatuur", "Binnen temperatuur",
            "Buiten", "Binnen", "Windsnelheid", "Windchill", "Heat index", "Dewpoint"));

    public Weerstation() {
        IO.init();
        GuiHelper.clearAllDisplays();
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
        GuiHelper.clearAllDisplays();
        ArrayList<String> menuOptions = new ArrayList<>();
        Collections.addAll(menuOptions, "Buiten temperatuur", "Binnen temperatuur");

        menu(menuOptions, "Temperatuur");
    }

    public void humidityMenu() {
        GuiHelper.clearAllDisplays();
        ArrayList<String> menuOptions = new ArrayList<>();
        Collections.addAll(menuOptions, "Buiten", "Binnen");

        menu(menuOptions, "Luchtvochtigheid");
    }

    public void windMenu() {
        GuiHelper.clearAllDisplays();
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
            case "Wind":
                windMenu();
                break;
            case "Regen":
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Period methodes \nnog niet aangemaakt \nRegenRate");
                break;
            case "Zonsopgang/ondergang":
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString(converted.getDateStamp().toLocalDate() + "\nZonsopgang: " + converted.getSunRise() + "\nZonsondergang: " + converted.getSunSet());
                break;
            case "Windchill":
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Period methodes \nnog niet aangemaakt \nWindchill");
                break;
            case "Heat index":
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Period methodes \nnog niet aangemaakt \nHeat index");
                break;
            case "Dewpoint":
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Period methodes \nnog niet aangemaakt \nDewpoint");
                break;
            case "Individueel":
                individueelMenu();
                break;

            // Submenu's
            // Temperatuur
            case "Buiten temperatuur" :
                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("WIP");
                break;
            case "Binnen temperatuur" :
                break;

                // Luchtvochtigheid
            case "Buiten" :
                break;
            case "Binnen" :
                break;

            // Wind
            case "Windsnelheid":
                break;
            case "Windrichting":
                GuiHelper.displayDoubleNumber(GuiHelper.display1, rawMeasurement.getWindDir(),0);

                GuiHelper.clearDMDisplay();
                GuiHelper.displayString("Windrichting in\ngraden ");
                break;
            case "Sam":

                //Misschien is er een snellere manier.
                break;
            case "Sander":
                jaarMenu();
                break;
            case "2009":
                int year = 2009;
                individueleOpdrachtSander(year);
                break;
            case "2010":
                year = 2010;
                individueleOpdrachtSander(year);
                break;
            case "2011":
                year = 2011;
                individueleOpdrachtSander(year);
                break;
            case "2012":
                year = 2012;
                individueleOpdrachtSander(year);
                break;
            case "2013":
                year = 2013;
                individueleOpdrachtSander(year);
                break;
            case "2014":
                year = 2014;
                individueleOpdrachtSander(year);
                break;
            case "2015":
                year = 2015;
                individueleOpdrachtSander(year);
                break;
            case "2016":
                year = 2016;
                individueleOpdrachtSander(year);
                break;
            case "2017":
                year = 2017;
                individueleOpdrachtSander(year);
                break;
            case "2018":
                year = 2018;
                individueleOpdrachtSander(year);
                break;
            case "2019":
                year = 2019;
                individueleOpdrachtSander(year);
                break;
            case "2020":
                year = 2020;
                individueleOpdrachtSander(year);
                break;
            case "2021":
                year = 2021;
                individueleOpdrachtSander(year);
                break;
            case "2022":
                year = 2022;
                individueleOpdrachtSander(year);
                break;
//---------------------------------------------------------------------------------------------------------------------------//
            case "Yuichi" :
                individueleOpdrachtYuichi();
                break;
            case "Rick":
                individueleOpdrachtRick();
                break;
            default:
                GuiHelper.clearAllDisplays();
                GuiHelper.displayString("Functie bestaat niet");
                System.out.println("?");
                break;
        }

        while (true) {
            // Rode knop - Ga terug naar het hoofd menu
            if (hasBooleanChanged(redButton, (IO.readShort(0x80) != 0))) {
                redButton = !redButton;

                System.out.println("Quit");
                GuiHelper.clearAllDisplays();
                break;
            }

            // Linker blauw knop - Terug naar het originele geseleteerde item
            if (hasBooleanChanged(blueButtonLeft, (IO.readShort(0x90) != 0))) {
                blueButtonLeft = !blueButtonLeft;

                showSelectedMenuItem(menuOptions, selectedItem);
            }

            // Rechter blauw knop - Laat de grafiek ervan zien
            if (hasBooleanChanged(blueButtonRight, (IO.readShort(0x100) != 0))) {
                blueButtonRight = !blueButtonRight;

                if (graphs.contains(menuOptions.get(selectedItem % menuOptions.size()))) {
                    switch (menuOptions.get(selectedItem % menuOptions.size())) {
                        case "Buiten temperatuur" :
                            Grafiek.displayGraph(period.getOutsideTemperature());
                            break;
                        case "Binnen temperatuur" :
                            Grafiek.displayGraph(period.getInsideTemperature());
                            break;
                        case "Buiten" :
                            Grafiek.displayGraph(period.getOutsideHumidity());
                            break;
                        case "Binnen" :
                            Grafiek.displayGraph(period.getInsideHumidity());
                            break;
                        case "Windsnelheid" :
                            Grafiek.displayGraph(period.getWindSpeed());
                            break;
                        case "Windchill" :
                            Grafiek.displayGraph(period.getWindChill());
                            break;
                        case "Heat index" :
                            Grafiek.displayGraph(period.getHeatIndex());
                            break;
                        case "Dewpoint" :
                            Grafiek.displayGraph(period.getDewpoint());
                            break;
                    }
                }
            }
        }

        mainMenu();
    }
    //individuele opdrachten
    //--------------------------------------------------------------------------------------------------------------------------------------------//

    public void individueleOpdrachtYuichi() {
        Period biggestDiffTemp = new Period(period.getBiggestDifferenceMinMaxTemperature(), period.getBiggestDifferenceMinMaxTemperature());
        double difference = biggestDiffTemp.getHighestOutsideTemperature() - biggestDiffTemp.getLowestOutsideTemperature();

        GuiHelper.displayDoubleNumber(GuiHelper.display1, difference, 2);
        GuiHelper.displayDoubleNumber(GuiHelper.display2, biggestDiffTemp.getHighestInsideTemperature(), 1);
        GuiHelper.displayDoubleNumber(GuiHelper.display3, biggestDiffTemp.getLowestOutsideTemperature(), 1);

        GuiHelper.clearDMDisplay();
        GuiHelper.displayString("Max - Verschil  - Min\nTemperatuur celsius\nop " + period.getBiggestDifferenceMinMaxTemperature());
    }

    public void individueleOpdrachtRick() {
        GuiHelper.displayDoubleNumber(GuiHelper.display1, period.consecutiveRain(0), 2);
        GuiHelper.displayDoubleNumber(GuiHelper.display2, period.consecutiveRain(1), 0);

        GuiHelper.clearDMDisplay();
        GuiHelper.displayString("Minutes - Rain mm \n");
    }
    
    public void individueleOpdrachtSander (int year){
       GuiHelper.clearDMDisplay();
       GuiHelper.displayString( "Maand met meeste\nregen in " + year + ":\n" + period.mostRainfall(year).toString());
    }

    public void jaarMenu(){
        ArrayList<String> menuOptions = new ArrayList<>();
        Collections.addAll(menuOptions, "2009", "2010", "2011", "2012", "2013",
                "2014", "2015", "2016", "2017", "2018","2019","2020","2021","2022");

        menu(menuOptions, "Sander");
    }

    //---------------------------------------------------------------------------------------------//
}
