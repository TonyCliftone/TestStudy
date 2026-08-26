import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CheckRemoveSpecificName {
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
}

// Задача 12: разработать метод с сигнатурой publiс static List<String> removeSpecificName(List<String> list, String nameToRemove). Метод принимает список и имя, которое нужно исключить. Возвращает новый список, не содержащий указанного имени.