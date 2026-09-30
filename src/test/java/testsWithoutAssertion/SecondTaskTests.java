package testsWithoutAssertion;

import mainMethods.Utils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class SecondTaskTests {

    @BeforeEach
    public void beforeMethod() {
        System.out.println("========================\nTest method start");
    }

    @AfterEach
    public void afterMethod() {
        System.out.println("Test method end\n========================");
    }

    //Первый метод isEven (Простой тест №1)
    @ParameterizedTest
    @Tag("failed") // так как оставил упавшим
    @CsvSource({
            "2, true",
            "4, true",
            "0, true",
            "-6, true",
            "1, false",
            "7, false",
            "-3, false",
            "12, true",
            "9, true", // для наглядности проставлено true чтоб получить месседж об ошибке.
            "10, true"
    })
    public void isEvenTest(int n, boolean expected) {
        assertEquals(expected, Utils.isEven(n), "isEven(" + n + ")");
    }

    //Второй метод checkAccess (Простой тест №2)
    @RepeatedTest(10)
    @Tag("main")
    public void isCheckAccessTest() {
        int age = (int) (Math.random() * 100);

        String expected = (age > 18) ? "Allowed" : "Denied"; // чтоб упали можно сделать обратку (age < 18) получим месседжи по каждому запуску теста

        assertEquals(expected, Utils.checkAccess(age), "checkAccess(" + age + ")");

    }

    //Третий метод checkPositive (Простой тест №3)
    @RepeatedTest(10)
    @Tag("main")
    public void isPositiveTest() {
        int min = -50;
        int max = 50;
        int n = (int) (Math.random() * ((max - min) + 1)) + min;

        boolean expected = n >= 0; // для фейла меняем тут
        boolean actual = Utils.isPositive(n);

        assertEquals(expected, actual, "checkAccess(" + n + ")");
    }

    //Четвертый метод CheckGrade (параметризированный с источником №1)

    static IntStream numbersFrom0To100() {
        return IntStream.rangeClosed(0, 100);
    }

    @ParameterizedTest
    @Tag("main")
    @MethodSource("numbersFrom0To100")
    public void isGradeTest(int score) {
        String expected;
        if (score < 0 || score > 100) expected = "Error";
        else if (score <= 20) expected = "E"; // для фейла меняем местами буквы в ожидаемом
        else if (score <= 40) expected = "D";
        else if (score <= 60) expected = "C";
        else if (score <= 80) expected = "B";
        else expected = "A";
        String actual = Utils.getGrade(score);

        assertEquals(expected, actual, "isGrade(" + score + ")");
    }

    //Пятый метод CheckBlastOff (Простой тест №4)

    @RepeatedTest(10)
    @Tag("main")
    public void testBlastOff() {
        int start = (int) (Math.random() * 10) + 1;
        StringBuilder result = new StringBuilder();

        for (int i = start; i >=1; i--) {
            result.append(i);
            if (i >1) {
                result.append(" ");
            }
        }
        result.append(" Поехали!"); // иронично можно в ожидаемом тут добавить еще один символ чтоб попасть в текст ошибки ассерта:)
        String expected = result.toString();
        String actual = Utils.blastOff(start);

        assertEquals(expected, actual, "blastOff(" + start + ")");
    }

    //Шестой метод CheckSumTon (репитед тест №1)

    @RepeatedTest(10)
    @Tag("main")
    public void testSumToN() {
        int n = (int) (Math.random() * 10);
        long expected = 0; // для фейла можно в ожидаемом поставить значение больше чем сумма 10 чисел из списка предложенного в math.random.
        for (int i = 1; i <= n; i++) expected += i;
        int actual = Utils.sumToN(n);

        assertEquals(expected, actual, "sumToN(" + n + ")");
    }

    //Седьмой метод CheckHasBug (параметризированный с источником №2)

    @ParameterizedTest
    @Tag("main")
    @CsvFileSource(resources = "/has-bug-data.csv") // для фейла тут где жук проставляем фолс в цсвшке
    public void testHasBug(String input, boolean expected) {
        String[] arr = input.isEmpty() ? new String[0] : input.split(",");
        boolean actual = Utils.hasBug(arr);

        assertEquals(expected, actual, "hasBug(" + Arrays.toString(arr) + ")");
    }

    //Восьмой метод CheckEvenInRange (параметризированный с источником №3)

    @ParameterizedTest
    @Tag("main")
    @CsvSource({
            "0, 5",
            "-1, 6",
            "2, 8",
            "3, 7",
            "1, 3",
            "1, 9",
            "6, 66",
            "3, 33",
            "1, 2",
            "3, 9",
    })
    public void testCheckEvenInRange(int start, int end) {
        StringBuilder sb = new StringBuilder();

        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (!sb.isEmpty()) {
                    sb.append(" "); // для ошибки ассерта например поломать метод формирования для экзептед тут например добавить символ к пробелу
                }
                sb.append(i);
            }
        }

        String expected = sb.toString();
        String actual = Utils.getEvenInRange(start, end);

        assertEquals(expected, actual, "checkEvenInRange(" + start + "," + end + ")");
    }

    //Девятый метод CheckFindMax (репитед тест №2)

    @RepeatedTest(10)
    @Tag("main")
    public void testCheckFindMax() {
        int size = (int) (Math.random() * 5) +1;
        int[] arr = new int[size];

        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(1000) - 500; // числа от -500 до 499
        }

        int expected = arr[0]; // для фейла изменить массив, иногда будет падать при репите
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > expected) {
                expected = arr[i];
            }
        }
        int actual = Utils.findMax(arr);

        assertEquals(expected, actual, "checkFindMax(" + Arrays.toString(arr) + ")");

    }

    //Десятый метод CheckReverse (репитед тест №3)

    @RepeatedTest(10)
    @Tag("main")
    public void testCheckReverse(){
        String[][] testWords = {
                {"One", "Two", "Three"},
                {"Four", "Five", "Six"},
                {"Seven", "Eight", "Nine"},
        };

        String[] input = testWords[ThreadLocalRandom.current().nextInt(testWords.length)];

        String[] expected = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            expected[i] = input[input.length - 1 - i];
        }
        String[] actual = Utils.reverse(input);

        assertArrayEquals(expected, actual, "reverse(" + Arrays.toString(input) + ")"); // как выяснилось для фейла можно выбрать метод не ассерта массивов а инта
    }

    //Одинадцатый метод CheckCalcAverage (параметризированный метод №4)

    @ParameterizedTest
    @Tag("main")
    @CsvSource(delimiter = ';', value = {
            "1,2,3,4,5; 3.0",
            "10,20; 15.0",
            "-5,0,5; 0.0",
            "7; 7.0",
            "2,4,6,8; 5.0" // для фейла пишем в экспектед тут например 6
    })
    public void testCheckCalcAverage(String numbers, double expected) {
        List<Integer> list = Arrays.stream(numbers.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        double actual = Utils.calcAverage(list);

        assertEquals(expected, actual, "calcAverage(" + list + ")");
    }

    //Двенадцатый метод CheckRemoveSpecificName (Репитед тест №4)

    @RepeatedTest(10)
    @Tag("main")
    public void testCheckRemoveSpecificName() {
            List<String> list = Arrays.asList("Gosha", "Tosha", "Alesha", "Aboba", "Neaboba");
            String toRemove = "Alesha";
            List<String> expected = new ArrayList<>();
            for (String s : list) {
                if (!s.equals(toRemove)) expected.add(s); // для поломки вместо адд делаем ремув, будут отличаться ожидаемый актуальный
            }
            List<String> actual = Utils.removeSpecificName(list, toRemove);

            assertEquals(expected, actual, "removeSpecificName(" + list + ", " + toRemove + ")");
        }
}

