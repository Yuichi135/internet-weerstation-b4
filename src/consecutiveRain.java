public class consecutiveRain {
    public static void main(String[] args) throws Exception {
        int i = 0;
        boolean running = true;
        int grootsteConsecutiveDays = 0;
        int consecutiveDays = 0;
        double mmGevallen = 0;
        double totaalMmGevallen = 0;

        while (running) {
            i = klassen.getRandomNumber();
            System.out.println(i);

            if (i == 0) {
                consecutiveDays = 0;
                mmGevallen = 0;
            } else {
                mmGevallen += klassen.berekenRegen(i);
                consecutiveDays++;
            }
            if (consecutiveDays > grootsteConsecutiveDays) {
                grootsteConsecutiveDays = consecutiveDays;
                totaalMmGevallen = mmGevallen;
            }
            klassen.print(grootsteConsecutiveDays, totaalMmGevallen);
            Thread.sleep(1000);
        }
    }
}

