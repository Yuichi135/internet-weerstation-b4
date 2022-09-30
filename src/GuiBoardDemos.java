public class GuiBoardDemos {
    public static void main(String[] args) {
        IO.init();
        clear();
        counterOn0x10();
        clear();
        countToTenThousand();

    }

    public static void counterOn0x10() {
        for (int i = 0; i < 10; i++) {
            IO.writeShort(0x10, i);
            IO.delay(1000);
        }
    }

    public static void clear() {
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

    public static void countToTenThousand() {
        boolean running = true;
        boolean tenrunning;
        boolean hundredRunning;
        boolean thousandRunning;
        boolean tenThousandRunning;
        int ten = 0;
        int hundred;
        int thousand;
        int tenThousand;

        while(running) {
            for (int i = 0; i < 10; i++) {
                IO.writeShort(0x10, i);
                IO.delay(1000);

                if (i == 9) {
                    ten ++;
                }
            }

        }
    }
}



