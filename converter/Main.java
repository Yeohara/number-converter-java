package converter;

import java.util.Scanner;



public class Main {

    public static String integerConverter(int radix_trg, int radix_src, String number) {
        StringBuilder str = new StringBuilder();
        if (radix_trg == radix_src && radix_src == 1) {
            int x = Integer.parseInt(number);
            str.append("1".repeat(Integer.toString(x).length()));
            return str.toString();
        }
        if (radix_src == 1) {
            int sum = 0;
            int x = Integer.parseInt(number);
            for (int i = 0; i < Integer.toString(x).length(); i++, sum++) ;
            int x_10 = Integer.parseInt(String.valueOf(sum), 10);
            str.append(Integer.toString(x_10, radix_trg));
        } else if (radix_trg == 1) {
            int x = Integer.parseInt(number, radix_src);
            str.append("1".repeat(Math.max(0, Integer.parseInt(String.valueOf(x), radix_src))));
        } else {
            int x = Integer.parseInt(number, radix_src);
            str.append(Integer.toString(x, radix_trg));
        }

        return str.toString();
    }

    public static String fractionalConverter(int radix_src, int radix_trg, String number) {
        StringBuilder str = new StringBuilder();
        String fract = number.substring(number.indexOf("."));
        number = "0" + fract;
        double temp;

        if (radix_src != 10) {
            temp = baseto10(number, radix_src);

        } else {
            temp = Double.parseDouble(number);
        }
        base10toany(temp, radix_trg, str);

        return str.substring(0,Math.min(5, str.length()));
    }

    private static double baseto10(String number, int radix_src) {
        double temp = 0;

        for (int i = 2, power = 0; i < number.length(); i++) {
            if (Character.getNumericValue(number.charAt(i)) >= 0) {
                temp += (double) Character.getNumericValue(number.charAt(i)) / Math.pow(radix_src, ++power);
            }
        }

        return temp;
    }

    private static void base10toany(double temp, int radix_trg, StringBuilder str) {
        double res = temp * radix_trg;
        for (int i = 0, k; i < Double.toString(temp).length(); i++) {
            str.append(Character.forDigit(Double.valueOf(res).intValue(), radix_trg));
            k = Double.valueOf(res).intValue();
            res = (res - k) * radix_trg;
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int radix_src;
        if (scanner.hasNextInt()) {
            radix_src = scanner.nextInt();
        } else {
            System.out.println("There is an error in input data");
            return;
        }

        String __ = scanner.nextLine();
        String number;
        if (scanner.hasNext()) {
            number = scanner.nextLine();
        } else {
            System.out.println("There is an error in input data");
            return;
        }

        int radix_trg;
        if (scanner.hasNextInt()) {
            radix_trg = scanner.nextInt();
        } else {
            System.out.println("There is an error in input data");
            return;
        }

        if ((1 > radix_src || radix_src > 36) || (1 > radix_trg || radix_trg > 36)) {
            System.out.println("There is an error in input data");
            return;
        }

        boolean fractional = true;
        if (number.indexOf('.') == -1 || radix_src == 1) {
            fractional = false;
        }
        if (!fractional) {
            System.out.println(integerConverter(radix_trg, radix_src, number));
        } else {
            StringBuilder str = new StringBuilder();
            str.append(integerConverter(radix_trg, radix_src, number.substring(0, number.indexOf('.'))));
            str.append(".");
            str.append(fractionalConverter(radix_src, radix_trg, number));
            System.out.println(str);
        }
    }
}
