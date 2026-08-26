import java.util.List;

public class CheckCalcAverage {
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
}


//Задача 11: разработать метод с сигнатурой publiс static calcAverage(List<Integer> list). Метод вычисляет и возвращает среднее арифметическое всех чисел в списке.