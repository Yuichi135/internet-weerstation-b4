import java.util.ArrayList;
import java.util.Collections;

public class GuiBoardDemosYuichi {
    public static ArrayList<Integer> display1 = new ArrayList<>();
    public static ArrayList<Integer> display2 = new ArrayList<>();
    public static ArrayList<Integer> display3 = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<RawMeasurement> lastHour = DatabaseConnection.getMeasurementsLastHour();
        ArrayList<Measurement> converted = new ArrayList<>();

        for (RawMeasurement record : lastHour) {
            converted.add(new Measurement(record));
        }

        IO.init();

        clearDisplay(display1);
        clearDisplay(display2);
        clearDisplay(display3);
        clrDMDisplay();

        counter();

//        displayTest(display1);
//        displayTellerTest(display1, 1250);
//        displayNumber(display2, 821);

//        animateDisplay(display1);
//        animateDisplay(display2);
//        customAnimate(0x10);


//        for (Measurement record : converted) {
//            displayNumber(display1, (int) record.getWindChill());
//            displayNumber(display2, (int) record.getWindSpeed());
//            displayNumber(display3, (int) record.getOutsideTemp());
//            IO.delay(100);
//        }

//        displayWindspeedGraph(converted);

//        displayString("Hello, World!");
//        IO.delay(1500);
//        displayParabola();

//        TODO: Excel sheet lezen en grafiek ervan maken
//         Knoppen werkent krijgen terwijl er een timer loopt
    }


    public static void displayTest(ArrayList<Integer> display) {
        for (int i = 0; i < 20; i++) {
            for (Integer adress : display) {
                IO.writeShort(adress, i % 10);
            }
            IO.delay(200);
        }
    }

    public static void displayTellerTest(ArrayList<Integer> display, int loops) {
        for (int i = 1; i <= loops; i++) {
            displayNumber(display, i);
            IO.delay(10);
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

    public static void displayNumber(int adress, int number) {
        int prod = number % 10;
        IO.writeShort(adress, prod);
        number = (number - prod) / 10;
    }

    public static void clearDisplay(ArrayList<Integer> display) {
        for (Integer adress : display) {
            IO.writeShort(adress, 0b100000000);
        }
    }

    public static void clearDisplay(int adress) {
        IO.writeShort(adress, 0b100000000);
    }

    public static void clrDMDisplay() {
        IO.writeShort(0x40, 0xFE);
        IO.writeShort(0x40, 0x01);
    }

    // TODO: Meerdere displays tegelijkertijd laten animeren
    //  Nu gaat het 1 voor 1
    public static void animateDisplay(ArrayList<Integer> display) {
        clearDisplay(display);

        for (Integer adress : display) {
            animate(adress);
        }
    }

    public static void animate(int adress) {
        int mask = 0b100000000;
        int start = mask | 1;
        int bit = start;

        for (int i = 0; i < 2; i++) {
            bit = start;
            for (int j = 0; j < 6; j++) {
                IO.delay(200);
                IO.writeShort(adress, bit);
                bit <<= 24;
                bit >>= 23;
                bit = mask | bit;
            }
        }
        bit = start;
        for (int i = 0; i < 6; i++) {
            IO.delay(200);
            IO.writeShort(adress, bit);
            bit <<= 24;
            bit >>= 23;
            bit = mask | bit | 1;
        }

        for (int i = 0; i < 6; i++) {
            bit = bit ^ mask;
            bit <<= 1;
            bit = bit | mask;
            bit = bit ^ 0b001000000;
            if (i == 0) {
                bit = bit ^ 0b010000000;
            }
            IO.delay(200);
            IO.writeShort(adress, bit);
        }

        ArrayList<Integer> animation = new ArrayList<>();
        Collections.addAll(animation, 0b100000001, 0b100100010, 0b101000000, 0b100010100, 0b100001000, 0b100000000);
        for (Integer frame : animation) {
            IO.delay(200);
            IO.writeShort(adress, frame);
        }
    }

    public static void customAnimate(int adress) {
        ArrayList<Integer> animation = new ArrayList<>();
        Collections.addAll(animation, 0b110000000, 0b100000000, 0b110000000, 0b100000000, 0b100001000, 0b100010000, 0b101000000,
                0b100000010, 0b100000001, 0b100100000, 0b101000000, 0b100000100, 0b100001000, 0b100010000, 0b100100000,
                0b100000001, 0b100000010, 0b101000000, 0b100110000, 0b100001001, 0b100000110, 0b101000000, 0b100110000,
                0b100001001, 0b100000110, 0b101000000, 0b101111111, 0b100000000, 0b101111111, 0b100000000);
        for (Integer frame : animation) {
            IO.delay(200);
            IO.writeShort(adress, frame);
        }
    }

    public static void displayString(String string) {
        char character;
        for (int i = 0; i < string.length(); i++) {
            character = string.charAt(i);
            IO.writeShort(0x40, character);
            IO.delay(25);
        }
    }

    public static void pixelTest() {
        int opcode = 3;
        // opcode is 3, maar moet wel 12 plaatsen naar links worden geschoven
        IO.writeShort(0x42, opcode << 12);  // Clear display

        int x, y;
        opcode = 1;
        for (int idx = 0; idx < 1000; idx++) {
            x = (int) (Math.random() * 128);
            y = (int) (Math.random() * 32);
            IO.writeShort(0x42, opcode << 12 | x << 5 | y);
            IO.delay(10);
        }
    }

    public static void displayParabola() {
        int zeroPoint = 0b1100000010000;
        displayAxis(zeroPoint);

        int[] coords = getZeroPointAxis(zeroPoint);
        int x = coords[0];
        int y = coords[1];

        int relativeX = -64;
        int relativeY;
        int opdcode = 1;


        for (int i = 0; i < 128; i++) {
            relativeY = (int) ((0.1 * Math.pow(relativeX, 2)) - 10);
            IO.writeShort(0x42, opdcode << 12 | (x + relativeX) << 5 | (y - relativeY));
            IO.delay(10);
            relativeX++;
        }
    }

    // TODO: Excel bestand importeren en displayen
    public static void displayGraph() {
        int zeroPoint = 0b1000010011100;
        displayAxis(zeroPoint);

        ArrayList<String> list = new ArrayList<>();
    }

//    // TODO: Optioneel, de 0,0 coordinaten teruggeven (gedaan)
//    //  Nu nog gebruiken bij het maken van de grafieken
//    // Nvm functie compleet veranderd
//    public static void displayAxis(String type) {
//        clrDMDisplay();
//
//        int x, y, zeroPoint;
//        int opdcode = 1;
//        switch (type) {
//            case "centre":
//                // Y axis
//                x = 64;
//                for (y = 0; y < 32; y++) {
//                    IO.writeShort(0x42, opdcode << 12 | x << 5 | y);
//                }
//
//                zeroPoint = opdcode << 12 | x << 5;
//
//                // X axis
//                y = 16;
//                for (x = 0; x < 128; x++) {
//                    IO.writeShort(0x42, opdcode << 12 | x << 5 | y);
//                }
//
//                zeroPoint = zeroPoint | y;
//                break;
//            case "bottom-left":
//                // Y axis
//                x = 4;
//                for (y = 0; y < 32; y++) {
//                    IO.writeShort(0x42, opdcode << 12 | x << 5 | y);
//                }
//
//                zeroPoint = opdcode << 12 | x << 5;
//
//                // X axis
//                y = 28;
//                for (x = 0; x < 128; x++) {
//                    IO.writeShort(0x42, opdcode << 12 | x << 5 | y);
//                }
//
//                zeroPoint = zeroPoint | y;
//                break;
//            default:
//                zeroPoint = 0b0;
//        }
////        return zeroPoint;
//        System.out.println(Integer.toBinaryString(zeroPoint));
//    }

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

    public static void displayWindspeedGraph(ArrayList<Measurement> measurements) {
        int zeroPoint = 0b1000010011100;
        displayAxis(zeroPoint);

        int[] coords = getZeroPointAxis(zeroPoint);
        int x = coords[0];
        int y = coords[1];

        int relativeX = x + 1;
        int relativeY = y - 1;
        int opdcode = 1;
        int windSpeed;

        for (Measurement measurement : measurements) {
            windSpeed = (int) measurement.getAvgWindSpeed();
//            System.out.println(relativeX);

            IO.writeShort(0x42, opdcode << 12 | (x + relativeX) << 5 | (relativeY - windSpeed));
            IO.delay(10);
            relativeX++;

            System.out.println(relativeY - windSpeed);
//            System.out.println(Integer.toBinaryString(opdcode << 12 | (x + relativeX) << 5 | (windSpeed - relativeY)));
        }
    }

    public static void counter() {
        int counter = 0;
        displayNumber(display1, 0);

        while (true) {
            if ( IO.readShort(0x80) == 0 ) {
                displayNumber(display1, counter);
            } else {
                displayNumber(display1, 0);
            }

            if ( IO.readShort(0x100) != 0 ) {
                counter++;
            }

            if ( IO.readShort(0x90) != 0 ) {
                counter--;
            }
            IO.delay(100);
        }
    }
}
