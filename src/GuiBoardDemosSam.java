public class GuiBoardDemosSam {
    public static void main(String[] args) {
        IO.init();

//        clear();
//        counterOn0x10();
        clear();
        countToTenThousand();
    }

    public static void counterOn0x10() {
        for (int b = 0; b < 10; b++) {
            IO.writeShort(0x10, b);
            IO.delay(500);
        }
    }

    public static void clear() {
        int a = 0b100000000;
        IO.writeShort(0x10, a);
        IO.writeShort(0x12, a);
        IO.writeShort(0x14, a);
        IO.writeShort(0x16, a);
        IO.writeShort(0x18, a);
        IO.writeShort(0x20, a);
        IO.writeShort(0x22, a);
        IO.writeShort(0x24, a);
        IO.writeShort(0x30, a);
        IO.writeShort(0x32, a);
        IO.writeShort(0x34, a);
    }

    public static void countToTenThousand() {
        boolean running = true;
        boolean tenrunning;
        boolean hundredRunning;
        boolean thousandRunning;
        boolean tenThousandRunning;
        int ten = 0;
        int hundred = 0;
        int thousand = 0;
        int tenThousand = 0;


        while(running) {

            for (int i = 0; i < 10; i++) {
                IO.writeShort(0x10, i);
                IO.delay(200);

                // i % 10 == 1 && i!=0
                //boolean sander= ;
                if (i == 0){
                    ten++;
                    IO.writeShort(0x12,ten);
                    IO.delay(150);
                    if (ten == 9){
                        ten = 0;
                    }
                }

                    if (i == 9 && ten == 9) {
                        if (hundred <9){
                            IO.delay(50);
                        hundred ++;
                        IO.writeShort(0x14, hundred);
                        IO.delay(50);
                        } else {
                            hundred = 0;
                        }
                        IO.writeShort(0x12, 0);
                        IO.writeShort(0x10, 0);
                    }
                        if (i == 9 && ten == 9 && hundred ==9 ) {
                            if ( thousand < 9) {
                                IO.delay(50);
                                thousand++;
                                IO.writeShort(0x16, thousand);
                            } else {
                                thousand = 0;
                            }
                        }
                            if (thousand == 9 && i == 9 && ten == 9 && hundred ==9 ) {
                                if (tenThousand < 10) {
                                    tenThousand++;
                                    IO.writeShort(0x18, tenThousand);
                                    IO.delay(50);
                                } else {
                                    clear();
                                }
                            }
            }

        }
    }
}

