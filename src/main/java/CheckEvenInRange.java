public class CheckEvenInRange {
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
}

//Задача 8: разработать метод с сигнатурой publiс static getEvenInRange(int start, int end). Метод принимает границы диапазона и возвращает строку, состоящую только из чётных чисел внутри этого промежутка (включая границы), разделённых пробелом. Перед первым и после последнего числа пробел не ставится. Например: (2, 5) -> “2 4”