public class CheckHasBug {
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
}

// Задача 7: разработать метод с сигнатурой publiс static boolean hasBug(String[] messages). Метод принимает массив строк и возвращает true, если хотя бы одна строка в массиве равна Bug. Сравнение можно выполнять без учёта регистра.