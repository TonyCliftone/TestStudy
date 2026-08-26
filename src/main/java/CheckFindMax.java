public class CheckFindMax {
    public static int findMax (int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
}


// Задача 9: разработать метод с сигнатурой publiс static public int findMax(int[] arr). Метод находит и возвращает самое большое число в переданном массиве.