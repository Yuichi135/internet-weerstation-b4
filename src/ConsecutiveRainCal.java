import java.util.Random;

public class ConsecutiveRainCal {
    public static int getRandomNumber() {
        Random r = new Random();
        int highest = 50;
        int lowest = 0;
        return r.nextInt(highest-lowest) + lowest;
    }

    public static void print(int consecutiveDays, double mmGevallen) {
        System.out.println("consecutive minutes of rain: " + consecutiveDays + " minutes");
        System.out.println("totaal milimeter fallen: " + Math.round(mmGevallen* 1000) /1000.0 + " mm\n");
    }

    public static double berekenRegen(double rainrate) {
        return rainrate/ 60;
    }
}
