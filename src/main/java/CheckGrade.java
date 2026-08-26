public class CheckGrade {
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
}

// Задача 4