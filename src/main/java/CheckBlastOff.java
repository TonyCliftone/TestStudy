public class CheckBlastOff {
    public static String blastOff(int start) {
        if (start <1) {
            return "Поехали!";
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
}

// Задача 5
