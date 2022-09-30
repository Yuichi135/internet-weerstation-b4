public class GuiBoardDemosRick {
    public static void main(String[] args) {
        IO.init();
        clearAll();
        clearDisplay();
//        counterOn0x10();
//        clearAll();
        countToTenThousand();
//        clearAll();
//        animation();
//        clearAll();
//        smartAnimation();
//        matrixDisplay();
//        displayAxis(89);
    }

    public static void counterOn0x10() {
        for (int i = 0; i < 10; i++) {
            IO.writeShort(0x10, i);
            IO.delay(1000);
        }
    }

    public static void clearAll() {
        IO.writeShort(0x10, 0b100000000);
        IO.writeShort(0x12, 0b100000000);
        IO.writeShort(0x14, 0b100000000);
        IO.writeShort(0x16, 0b100000000);
        IO.writeShort(0x18, 0b100000000);
        IO.writeShort(0x20, 0b100000000);
        IO.writeShort(0x22, 0b100000000);
        IO.writeShort(0x24, 0b100000000);
        IO.writeShort(0x30, 0b100000000);
        IO.writeShort(0x32, 0b100000000);
        IO.writeShort(0x34, 0b100000000);
    }

    public static void clearDisplay() {
        IO.writeShort(0x40, 0xFE);
        IO.writeShort(0x40, 0x01);
    }


    public static void countToTenThousand() {
        boolean running = true;
        boolean tenrunning = false;
        boolean hundredRunning = false;
        boolean thousandRunning = false;
        boolean tenThousandRunning = false;
        int ten = 0;
        int hundred = 0;
        int thousand = 0;
        int tenThousand = 0;

        while (running) {
            for (int i = 0; i < 10; i++) {
                IO.writeShort(0x10, i);
                IO.delay(100);

                if (i == 9) {
                    ten++;
                    tenrunning = true;
                    i = 0;
                }

                if (ten == 9) {
                    hundred++;
                    hundredRunning = true;
                    ten = 0;
                }

                if (hundred == 9) {
                    thousand++;
                    thousandRunning = true;
                    hundred = 0;
                }

                if (thousand == 9) {
                    tenThousand++;
                    tenThousandRunning = true;
                    thousand = 0;
                }

                if (tenThousand == 9) {
                    break;
                }

                if (tenrunning) {
                    IO.writeShort(0x12, ten);
                }

                if (hundredRunning) {
                    IO.writeShort(0x14, hundred);
                }

                if (thousandRunning) {
                    IO.writeShort(0x16, thousand);
                }

                if (tenThousandRunning) {
                    IO.writeShort(0x18, tenThousand);
                }

            }
        }
    }

    public static void animation() {
        for (int i = 0; i < 2; i++) {
            IO.writeShort(0x30, 0b100000001);
            IO.delay(250);
            IO.writeShort(0x30, 0b100000010);
            IO.delay(250);
            IO.writeShort(0x30, 0b100000100);
            IO.delay(250);
            IO.writeShort(0x30, 0b100001000);
            IO.delay(250);
            IO.writeShort(0x30, 0b100010000);
            IO.delay(250);
            IO.writeShort(0x30, 0b100100000);
            IO.delay(250);
            IO.writeShort(0x30, 0b100000001);
        }
        for (int i = 0; i < 1; i++) {
            IO.delay(200);
            IO.writeShort(0x30, 0b100000011);
            IO.delay(200);
            IO.writeShort(0x30, 0b100000111);
            IO.delay(200);
            IO.writeShort(0x30, 0b100001111);
            IO.delay(200);
            IO.writeShort(0x30, 0b100011111);
            IO.delay(200);
            IO.writeShort(0x30, 0b100111111);
            IO.delay(200);
            IO.writeShort(0x30, 0b100111110);
            IO.delay(200);
            IO.writeShort(0x30, 0b100111100);
            IO.delay(200);
            IO.writeShort(0x30, 0b100111000);
            IO.delay(200);
            IO.writeShort(0x30, 0b100110000);
            IO.delay(200);
            IO.writeShort(0x30, 0b100100000);
            IO.delay(200);
            IO.writeShort(0x30, 0b100000000);
        }
    }

    public static void smartAnimation() {
        for (int i = 0; i < 2; i++) {
            int displayInput = 0b100000001;
            for (int j = 0; j < 6; j++) {
                System.out.println(displayInput);
                IO.writeShort(0x100, displayInput);
                displayInput = (displayInput << 1) | 0b100000000;
                IO.delay(250);
            }
        }

        int displayInput = 0b100000001;
        int vergelijking = 0b100000001;
        for (int i = 0; i < 6; i++) {
            System.out.println(displayInput);
            IO.writeShort(0x10, displayInput);
            displayInput = (displayInput << 1) | vergelijking;
            IO.delay(250);
            vergelijking = displayInput;
        }
        //vergelijking en displayInput = 0b100111111
        vergelijking = 0b100000001;
        for (int i = 0; i < 6; i++) {
            System.out.println(displayInput);
            IO.writeShort(0x10, displayInput);
            displayInput = (displayInput << 1) & vergelijking;
            IO.delay(250);
            vergelijking = displayInput;
        }
    }

//    public static void displayAxis(int zeropoint) {
//        int x = 128;
//        int y = 32;
//        int opcode = 1;
//        int[] coords = 0b1001011;
//
//        x = coords[0];
//        for(y=0; y<32; y++) {
//            IO.writeShort(0x42, opcode<<12 | x <<5 | 5);
//        }
//
//        for (x=0; x<128; x++) {
//            IO.writeShort(0x42, opcode <<12 | x << 5 | y);
//        }
//    }
}









