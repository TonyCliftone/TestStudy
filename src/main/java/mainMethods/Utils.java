package mainMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utils {
    //Вынос всех реализованных методов в один класс, для удобства
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    public static String getEvenInRange(int start, int end) {
        StringBuilder sb = new StringBuilder();

        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (!sb.isEmpty()) {
                    sb.append(" ");
                }
                sb.append(i);
            }
        }
        return sb.toString();
    }

    public static String checkAccess(int age) {
        if (age > 18) {
            return "Allowed";
        }
        else {
            return "Denied";
        }
    }

    public static String blastOff(int start) {
        if (start <1) {
            throw new IllegalArgumentException("start должен быть >=1");
        }

        StringBuilder result = new StringBuilder();

        for (int i = start; i >=1; i--) {
            result.append(i);
            if (i >1) {
                result.append(" ");
            }
        }
        result.append(" Поехали!");
        return result.toString();
    }

    public static double calcAverage(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        for (int num : list) {
            sum += num;
        }
        return (double) sum / list.size();
    }

    public static int findMax (int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public static String getGrade(int score){
        if (score <0 || score >100) {
            return "Error";
        }
        else if (score <=20) {
            return "E";
        }
        else if (score <=40) {
            return "D";
        }
        else if (score <=60) {
            return "C";
        }
        else if (score <=80) {
            return "B";
        }
        else {
            return "A";
        }
    }

    public static boolean hasBug(String[] messages) {
        if (messages == null) {
            return false;
        }
        for (String message : messages) {
            if (message != null && message.equalsIgnoreCase("Bug")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPositive(int n) {
        return n >= 0 ? true : false;
    }

    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        if (list == null) {
            return null;
        }

        List<String> result = new ArrayList<>();
        for (String name : list) {
            if (!Objects.equals(name, nameToRemove)) {
                result.add(name);
            }
        }
        return result;
    }

    public static String[] reverse(String[] arr) {
        int length = arr.length;
        String[] reversed = new String[length];

        for (int i = 0; i < length; i++) {
            reversed[i] = arr[length - 1 - i];
        }
        return reversed;
    }

    public static int sumToN(int n) {
        int sum = 0;
        for (int i = 1; i <=n; i++) {
            sum += i;
        }
        return sum;
    }
}
