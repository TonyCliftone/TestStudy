public class CheckReverse {
    public static String[] reverse(String[] arr) {
        int length = arr.length;
        String[] reversed = new String[length];

        for (int i = 0; i < length; i++) {
            reversed[i] = arr[length - 1 - i];
        }
        return reversed;
    }
}

// Задача 10: разработать метод с сигнатурой publiс static String[] reverse(String[] arr). Метод возвращает новый массив, в котором элементы исходного массива расположены в обратном порядке. Например, {“One”, “Two”, “Zero”} -> {“Zero”, “Two”, “One}.