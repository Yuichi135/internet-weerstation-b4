//import java.time.LocalDate;
//import java.time.Month;
//import java.util.ArrayList;
//import java.util.Collections;
//
//public class Sander {
//    public double getRainfall(ArrayList<Double> numbers) {// Berekent het totaal van alle regen dat is gevallen.
//        double sumOfRainfall = 0.0;
//        for (double number : numbers) {
//            sumOfRainfall = (sumOfRainfall + (number / 60));                // delen door zestig, omdat elke waarde in mm/h staan en om de minuut 1 waarde geeft.
//        }                                                              // mm/h / 60 = mm/minuut. waarden wordt per minuut gegeven dus wordt aleen mm gepakt.
//
//        return sumOfRainfall;
//    }
//
//    public double getRainfallMonths() { //Voegt alle waarden van rainRate van de maand toe aan rainfall.
//        ArrayList<Measurement> measurements = Period.getMeasurements();
//        ArrayList<Double> rainfall = new ArrayList<>();
//
//
//        for (Measurement measurement : measurements) {
//            rainfall.add(measurement.getRainRate());
//        }
//        return getRainfall(rainfall);
//    }
//
//
//
//    public Month mostRainfall(){
//        ArrayList<Period> months = new ArrayList<>();
//        int year = 2015;
//
//        Period january = new Period(LocalDate.of(year,Month.JANUARY,1),LocalDate.of(year,Month.JANUARY,31));
//        Period february = new Period(LocalDate.of(year,Month.FEBRUARY,1 ),LocalDate.of(year,Month.FEBRUARY,28));
//        Period march = new Period(LocalDate.of(year,Month.MARCH,1),LocalDate.of(year,Month.MARCH,31));
//        Period april = new Period(LocalDate.of(year,Month.APRIL,1),LocalDate.of(year,Month.APRIL,30));
//        Period may = new Period(LocalDate.of(year,Month.MAY,1),LocalDate.of(year,Month.MAY,31));
//        Period june = new Period(LocalDate.of(year,Month.JUNE,1),LocalDate.of(year,Month.JUNE,30));
//        Period july = new Period(LocalDate.of(year,Month.JULY,1),LocalDate.of(year,Month.JULY,31));
//        Period august = new Period(LocalDate.of(year,Month.AUGUST,1),LocalDate.of(year,Month.AUGUST,31));
//        Period september = new Period(LocalDate.of(year,Month.SEPTEMBER,1),LocalDate.of(year,Month.SEPTEMBER,30));
//        Period october = new Period(LocalDate.of(year,Month.OCTOBER,1),LocalDate.of(year,Month.OCTOBER,31));
//        Period november = new Period(LocalDate.of(year,Month.NOVEMBER,1),LocalDate.of(year,Month.NOVEMBER,30));
//        Period december = new Period(LocalDate.of(year,Month.DECEMBER,1),LocalDate.of(year,Month.DECEMBER,31));
//
//        Collections.addAll(months, january, february, march, april, may, june, july, august, september, october,november,december); // voegt alle periodes aan months.
//
//        ArrayList<Double> rainfall = new ArrayList<>();
//        Month greatestRainfallMonth;
//
//        for (Period period:months) {
//            rainfall.add(period.getRainfallMonths()); //voegt alle omgerekende waarden aan rainfall
//        }
//
//        if (january.getRainfallMonths() == getHighest(rainfall)){  //kijkt in welke maand het meest heeft geregend.
//            greatestRainfallMonth = Month.JANUARY;
//        }else if (february.getRainfallMonths() == getHighest(rainfall)){
//            greatestRainfallMonth = Month.FEBRUARY;
//        }else if (march.getRainfallMonths() == getHighest(rainfall)){
//            greatestRainfallMonth = Month.MARCH;
//        }else if (april.getRainfallMonths() == getHighest(rainfall)){
//            greatestRainfallMonth = Month.APRIL;
//        }else if (may.getRainfallMonths() == getHighest(rainfall)){
//            greatestRainfallMonth = Month.MAY;
//        }else if (june.getRainfallMonths() == getHighest(rainfall)){
//            greatestRainfallMonth = Month.JUNE;
//        }else if (july.getRainfallMonths() == getHighest(rainfall)){
//            greatestRainfallMonth = Month.JULY;
//        }else if (august.getRainfallMonths() == getHighest(rainfall)){
//            greatestRainfallMonth = Month.AUGUST;
//        }else if (september.getRainfallMonths() == getHighest(rainfall)){
//            greatestRainfallMonth = Month.SEPTEMBER;
//        }else if (october.getRainfallMonths() == getHighest(rainfall)){
//            greatestRainfallMonth = Month.OCTOBER;
//        }else if (november.getRainfallMonths() == getHighest(rainfall)){
//            greatestRainfallMonth = Month.NOVEMBER;
//        }else{
//            greatestRainfallMonth = Month.DECEMBER;
//        }
//        return greatestRainfallMonth;
//    }
//}