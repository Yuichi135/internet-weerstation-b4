import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.event.IIOWriteProgressListener;

public class GuiBoardDemosSander {


    public static void numberTeller1() {
        IO.init();

        for (int time = 0; time < 3; time++) {

            for (int count = 0; count < 10; count++) {

                IO.writeShort(0x10, count);
                IO.writeShort(0x12, count);
                IO.writeShort(0x14, count);
                IO.writeShort(0x16, count);
                IO.writeShort(0x18, count);
                IO.delay(1000);
            }
        }
    }

    public static void numberTeller2(){

        IO.writeShort(0x12, 0b100000000);

        for (int tienduizend = 0; tienduizend < 10; tienduizend++) {               //Hij telt totdat er 99999 op de Gui staat.

            if (tienduizend != 0){
                IO.writeShort(0x18, tienduizend);
            }else{
                IO.writeShort(0x18, 0b100000000);
            }

            for (int duizend = 0; duizend < 10; duizend++){

                if (duizend != 0){
                    IO.writeShort(0x16, duizend);
                }else{
                    IO.writeShort(0x16, 0b100000000);
                }

                for (int honderd = 0; honderd < 10; honderd++){

                    if (honderd != 0){
                        IO.writeShort(0x14, honderd);
                    }else{
                        IO.writeShort(0x14, 0b100000000);
                    }

                    for (int tien = 0;tien < 10; tien++){

                        if (tien != 0){
                            IO.writeShort(0x12, tien);
                        }else{
                            IO.writeShort(0x12, 0b100000000);
                        }

                        for (int een = 0; een < 10; een++){

                            IO.writeShort(0x10, een);
                            IO.delay(50);
                        }
                    }
                }
            }
        }
    }

    public static void clearAll(){

        ArrayList<Integer> display = new ArrayList<>();
        display.add(0x10);
        display.add(0x12);
        display.add(0x14);
        display.add(0x16);
        display.add(0x18);
        display.add(0x20);
        display.add(0x22);
        display.add(0x24);
        display.add(0x30);
        display.add(0x32);
        display.add(0x34);

        for (int nummer:display) {
            IO.writeShort(nummer, 0b100000000);
        }

    }

    public static void animatie(){
        ArrayList<Integer> animatieDisplay = new ArrayList<>();

        animatieDisplay.add(0b100000001);
        animatieDisplay.add(0b100000010);
        animatieDisplay.add(0b100000100);
        animatieDisplay.add(0b100001000);
        animatieDisplay.add(0b100010000);
        animatieDisplay.add(0b100100000);
        animatieDisplay.add(0b100000001);
        animatieDisplay.add(0b100000000);

        int aantalKeren = 0;

        for (int nummer:animatieDisplay) {
            IO.writeShort(0x20, nummer);
            IO.delay(50);

            IO.writeShort(0x22, nummer);
            IO.delay(50);

            IO.writeShort(0x24, nummer);
            IO.delay(50);
        }
    }

    public static ArrayList<Double> getValues()
    {
        return new ArrayList<Double>(Arrays.asList(new Double[]{ 22.4, 22.2, 22.1, 21.7, 21.3, 21.2, 21.2, 20.8, 20.6, 20.3, 20.1, 19.8, 19.8, 19.6, 19.3, 19.2, 19.1, 18.8, 18.8, 18.7, 18.6, 18.4, 18.3, 18.2, 18.0, 17.8, 17.6, 17.4, 17.7, 17.8, 17.8, 18.1, 18.3, 18.8, 19.2, 19.8, 19.9, 20.6, 20.9, 21.7, 22.0, 22.9, 23.4, 23.8, 24.2, 24.4, 24.7, 24.7, 25.2, 25.2, 25.4, 25.6, 25.9, 26.6, 26.3, 26.3, 26.3, 26.9, 27.1, 26.9, 26.6, 26.8, 26.7, 26.4, 26.6, 26.8, 27.1, 26.9, 26.7, 26.9, 24.9, 23.2, 23.7, 23.8, 24.1, 24.1, 23.8, 23.2, 22.6, 22.9, 22.8, 22.0, 21.8, 21.7, 21.4, 21.1, 20.7, 20.6, 20.4, 20.3, 20.1, 19.9, 19.7, 19.6, 19.3, 19.3 }));
    }

    public static void grafiek(){

    }

    public static void main(String[] args) {
        IO.init();
     //numberTeller1();
     //numberTeller2();
     //clearAll();
     //animatie();
    }


}




