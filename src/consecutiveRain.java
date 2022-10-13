public class consecutiveRain {

    public static void consecutiveRain(double i) {
        int grootsteConsecutiveDays = 0;
        int consecutiveDays = 0;
        double mmGevallen = 0;
        double totaalMmGevallen = 0;

        if (i == 0) {
            consecutiveDays = 0;
            mmGevallen = 0;
        } else {
            mmGevallen += ConsecutiveRainCal.berekenRegen(i);
            consecutiveDays++;
        }
        if (consecutiveDays > grootsteConsecutiveDays) {
            grootsteConsecutiveDays = consecutiveDays;
            totaalMmGevallen = mmGevallen;
        }
        ConsecutiveRainCal.print(grootsteConsecutiveDays, totaalMmGevallen);
    }

}

