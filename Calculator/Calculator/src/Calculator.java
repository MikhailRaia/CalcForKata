import java.util.Locale;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {

        CalculatorMath calk = new CalculatorMath();
        calk.showRimResult();
        calk.showArabResult();
    }
}

class CalculatorMath {

    Scanner sc = new Scanner(System.in);
    String inputStr = sc.nextLine().toUpperCase(Locale.ROOT).replace(" ", "");
    char[] inputChar = new char[inputStr.length()];

    // Перевод строки в char возврат массива Char
    public char[] convertToChar(String inputString) {
        for (int i = 0; i < inputStr.length(); i++) {
            inputChar[i] = inputStr.charAt(i);
        }
        return inputChar;
    }

    // Поиск знака и возврат его положения и факт знака
    public int[] findSign(String inputString) {
        int[] sign = new int[2];
        char[] znak = {'+', '-', '/', '*'};
        for (int i = 0; i < znak.length; i++) {
            for (int j = 1; j < convertToChar(inputStr).length; j++) {
                if (znak[i] == convertToChar(inputStr)[j]) {
                    sign[0] = i; //  сам знак +-/*
                    sign[1] = j; //  порядок знака
                }
            }
        }
        return sign;
    }

    // Возврат подстрок
    public String[] substrings(String inputString) {
        String[] subString = new String[2];
        subString[0] = inputStr.substring(0, findSign(inputString)[1]);
        subString[1] = inputStr.substring((findSign(inputString)[1] + 1), inputString.length());
        return subString;
    }

    //Положение чисел до мат. знака и после
    public int[] rimskie(String[] substrings) {

        int [] rimsk = new int[2];
        String rimskie1 = "I,II,III,IV,V,VI,VII,VIII,IX,X";
        String[] rimskie1Split = rimskie1.split(",");

        String substringBegin = substrings(inputStr)[0];
        String substringEnd = substrings(inputStr)[1];

        for (int i = 0; i < rimskie1Split.length; i++) {
            for (int j = 0; j < rimskie1Split.length; j++) {
                if ((substringBegin.equals(rimskie1Split[i])) & (substringEnd.equals(rimskie1Split[j]))) {
                    rimsk[0] = i; // Число до знака
                    rimsk[1] = j; // Число после знака
                }
            }
        }
        return rimsk;
    }

    // Результат математических операций с римскими числами.
    public String[] rimskieResult (int[] rimskie) {

        String rimskie1 = "I,II,III,IV,V,VI,VII,VIII,IX,X";
        String[] rimskie1Split = rimskie1.split(",");

        String rimskie2 = "X,XX,XXX,XL,L,LX,LXX,LXXX,XC,C";
        String[] rimskie2Split = rimskie2.split(",");

        String[] result = new String[1];

        // Мат. операция и вывод римских цифр
        int multiplication = (((rimskie(substrings(inputStr))[0])+1) * ((rimskie(substrings(inputStr))[1])+1));
        int intPartMult = (multiplication / 10)-1;
        int semPartMult = (multiplication % 10)-1;
        int semPart = (((rimskie(substrings(inputStr))[0]+1) + (rimskie(substrings(inputStr))[1]+1))-10);

        for (String s : rimskie1Split) {
            for (String value : rimskie1Split) {
                if ((substrings(inputStr)[0].equals(s)) && (substrings(inputStr)[1].equals(value))) {

                    // "+"
                    if (findSign(inputStr)[0] == 0) {
                        if (((rimskie(substrings(inputStr))[0]+1) + (rimskie(substrings(inputStr))[1]+1)) > 10) {
                            result[0] = (rimskie1Split[9] + rimskie1Split[semPart-1]);
                            return result;
                        } else if ((rimskie(substrings(inputStr))[0] + rimskie(substrings(inputStr))[1]) <= 10) {
                            result[0] = (rimskie1Split[(rimskie(substrings(inputStr))[0] + rimskie(substrings(inputStr))[1])+1]);
                            return result;
                        }
                    }

                    // "-"
                    else if (findSign(inputStr)[0] == 1) {
                        result[0] = (rimskie1Split[(rimskie(substrings(inputStr))[0] - rimskie(substrings(inputStr))[1] - 1)]);
                        return result;
                    }

                    // "/"
                    else if (findSign(inputStr)[0] == 2) {
                        result[0] = (rimskie1Split[(rimskie(substrings(inputStr))[0] - 2) / (rimskie(substrings(inputStr))[1] + 1)]);
                        return result;
                    }

                     // "*"
                    else if (findSign(inputStr)[0] == 3) {
                        if (multiplication <= 10) {
                            result[0] = rimskie1Split[(multiplication - 1)];
                            return result;
                        } else {
                            result[0] = rimskie2Split[(intPartMult)] + rimskie1Split[(semPartMult)];
                            return result;
                        }
                    }
                }
                else {
                    result[0] = "Недопустимое выражение";
                }
            }
        }
        return result;
    }

    // Результат математических операций с Арабскими числами
    public int [] arabskieResult(String[] substrings) {

        int[] result = new int[1];

                    if (findSign(inputStr)[0] == 0) {
                        result[0] = (Integer.parseInt(substrings(inputStr)[0]) + Integer.parseInt(substrings(inputStr)[1]));
                        return result;
                    } else if (findSign(inputStr)[0] == 1) {
                        result[0] = (Integer.parseInt(substrings(inputStr)[0]) - Integer.parseInt(substrings(inputStr)[1]));
                        return result;
                    } else if (findSign(inputStr)[0] == 2) {
                        result [0] = (Integer.parseInt(substrings(inputStr)[0]) / Integer.parseInt(substrings(inputStr)[1]));
                        return result;
                    } else if (findSign(inputStr)[0] == 3) {
                        result [0] = (Integer.parseInt(substrings(inputStr)[0]) * Integer.parseInt(substrings(inputStr)[1]));
                        return result;
                    }
        return result;
        }

       public void showRimResult ()  {

           String rimskie1 = "I,II,III,IV,V,VI,VII,VIII,IX,X";
           String[] rimskie1Split = rimskie1.split(",");

            if (findSign(inputStr)[1]==0) {
                System.out.print("Отсутствует знак");
            }
             if (findSign(inputStr)[1]!=0) {

                 for (String value : rimskie1Split) {
                     for (String s : rimskie1Split) {
                         if ((substrings(inputStr)[0].equals(value)) & (substrings(inputStr)[1].equals(s)))
                             System.out.println(rimskieResult(rimskie(substrings(inputStr)))[0]);
                     }
                 }
            }
        }

       public void showArabResult ()  {

           String arabskie = "-10,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,10";
           String [] arabskieSplit = arabskie.split(",");

            if (findSign(inputStr)[1]!=0) {

                for (String s : arabskieSplit) {
                    for (String value : arabskieSplit) {
                        if ((substrings(inputStr)[0].equals(s)) & (substrings(inputStr)[1].equals(value)))
                            System.out.println(arabskieResult(substrings(inputStr))[0]);
                    }
                }
            }
       }
}


