package testsWithoutAssertion;

import mainMethods.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
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
    @Test
    public void isEvenTest() {
        int n = (int) (Math.random() * 100);

        boolean expected = (n % 2 == 0); //для попадания в else правим expected
        boolean actual = Utils.isEven(n);

        if (actual == expected) {
            System.out.println("TEST PASSED: isEven(" + n + ") = " + actual);
        }
        else {
            System.out.println("TEST FAILED: isEven(" + n + ") = " + actual + ", expected " + expected);
        }
    }

    //Второй метод checkAccess (Простой тест №2)
    @Test
    public void isCheckAccessTest() {
        int age = (int) (Math.random() * 100);

        String expected = (age > 18) ? "Allowed" : "Denied"; //для попадания в else правим expected например больше 101
        String actual = Utils.checkAccess(age);

        if (actual.equals(expected)) {
            System.out.println("TEST PASSED: checkAccess(" + age + ") = " + actual);
        }
        else {
            System.out.println("TEST FAILED: isEven(" + age + ") = " + actual + ", expected " + expected);
        }
    }

    //Третий метод checkPositive (Простой тест №3)
    @Test
    public void isPositiveTest() {
        int min = -50;
        int max = 50;
        int n = (int) (Math.random() * ((max - min) + 1)) + min;

        boolean expected = n >= 0; // для фейла меняем тут
        boolean actual = Utils.isPositive(n);

        if (actual == expected) {
            System.out.println("TEST PASSED: isPositive(" + n + ") = " + actual);
        }
        else {
            System.out.println("TEST FAILED: isPositive(" + n + ") = " + actual + ", expected " + expected);
        }
    }

    //Четвертый метод CheckGrade (параметризированный с источником №1)

    static IntStream numbersFrom0To100() {
        return IntStream.rangeClosed(0, 100);
    }

    @ParameterizedTest
    @MethodSource("numbersFrom0To100")
    public void isGradeTest(int score) {
        String expected;
        if (score < 0 || score > 100) expected = "Error";
        else if (score <= 20) expected = "E";
        else if (score <= 40) expected = "D";
        else if (score <= 60) expected = "C";
        else if (score <= 80) expected = "B";
        else expected = "A";
        String actual = Utils.getGrade(score);

        if(actual.equals(expected)) {
            System.out.println("TEST PASSED: isGrade(" + score + ") = " + actual);
        }
        else {
            System.out.println("TEST FAILED: isGrade(" + score + ") = " + actual + ", expected " + expected);
        }
    }

    //Пятый метод CheckBlastOff (Простой тест №4)

    @Test
    public void testBlastOff() {
        int start = (int) (Math.random() * 10);
        StringBuilder result = new StringBuilder();

        for (int i = start; i >=1; i--) {
            result.append(i);
            if (i >1) {
                result.append(" ");
            }
        }
        result.append(" Поехали!");
        String expected = result.toString();
        String actual = Utils.blastOff(start);

        if (actual.equals(expected)) {
            System.out.println("TEST PASSED: blastOff(" + start + ") = " + actual);
        } else {
            System.out.println("TEST FAILED: blastOff(" + start + ") = " + actual + ", expected " + expected);
        }
    }

    //Шестой метод CheckSumTon (репитед тест №1)

    @RepeatedTest(5)
    public void testSumToN() {
        int n = (int) (Math.random() * 10);
        long expected = 0; // для фейла можно в ожидаемом поставить значение больше чем сумма 10 чисел из списка предложенного в math.random.
        for (int i = 1; i <= n; i++) expected += i;
        int actual = Utils.sumToN(n);

        if (actual == expected) {
            System.out.println("TEST PASSED: sumToN(" + n + ") = " + actual);
        } else {
            System.out.println("TEST FAILED: sumToN(" + n + ") = " + actual + ", expected " + expected);
        }
    }

    //Седьмой метод CheckHasBug (параметризированный с источником №2)

    @ParameterizedTest
    @CsvFileSource(resources = "/has-bug-data.csv")
    public void testHasBug(String input, boolean expected) {
        String[] arr = input.isEmpty() ? new String[0] : input.split(",");
        boolean actual = Utils.hasBug(arr);
        if (actual == expected) {
            System.out.println("TEST PASSED: hasBug(" + Arrays.toString(arr) + ") = " + actual);
        } else {
            System.out.println("TEST FAILED: hasBug(" + Arrays.toString(arr) + ") = " + actual + ", expected " + expected);
        }
    }

    //Восьмой метод CheckEvenInRange (параметризированный с источником №3)

    @ParameterizedTest
    @CsvSource({
            "0, 5",
            "-1, 6",
            "2, 8"
    })
    public void testCheckEvenInRange(int start, int end){
        StringBuilder sb = new StringBuilder();

        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (!sb.isEmpty()) {
                    sb.append(" ");
                }
                sb.append(i);
            }
        }

        String expected = sb.toString();
        String actual = Utils.getEvenInRange(start, end);

        if (actual.equals(expected)) {
                System.out.println("TEST PASSED: checkEvenInRange(" + start + "," + end + ") = " + actual);
            } else {
                System.out.println("TEST FAILED: checkEvenInRange(" + start + "," + end + ") = " + actual + ", expected " + expected);
            }
        }

    //Девятый метод CheckFindMax (репитед тест №2)

    @RepeatedTest(5)
    public void testCheckFindMax() {
        int size = (int) (Math.random() * 5);
        int[] arr = new int[size];

        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(1000) - 500; // числа от -500 до 499
        }

        int expected = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > expected) {
                expected = arr[i];
            }
        }
        int actual = Utils.findMax(arr);

            if (actual == expected) {
                System.out.println("TEST PASSED: checkFindMax(" + Arrays.toString(arr) + ") = " + actual);
            } else {
                System.out.println("TEST FAILED: checkFindMax(" + Arrays.toString(arr) + ") = " + actual + ", expected " + expected);
            }
    }

    //Десятый метод CheckReverse (репитед тест №3)

    @RepeatedTest(3)
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

        if (Arrays.equals(actual, expected)) {
            System.out.println("TEST PASSED: reverse(" + Arrays.toString(input) + ") = " + Arrays.toString(actual));
        } else {
            System.out.println("TEST FAILED: reverse(" + Arrays.toString(input) + ") = " + Arrays.toString(actual) + ", expected " + Arrays.toString(expected));
        }
    }

    //Одинадцатый метод CheckCalcAverage (параметризированный метод №4)

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1,2,3,4,5; 3.0",
            "10,20; 15.0",
            "-5,0,5; 0.0",
            "7; 7.0",
            "2,4,6,8; 5.0"
    })
    public void testCheckCalcAverage(String numbers, double expected) {
        List<Integer> list = Arrays.stream(numbers.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        double actual = Utils.calcAverage(list);

        if (Math.abs(actual - expected) < 0.0001) {
            System.out.println("TEST PASSED: calcAverage(" + list + ") = " + actual);
        } else {
            System.out.println("TEST FAILED: calcAverage(" + list + ") = " + actual + ", expected " + expected);
        }
    }

    //Двенадцатый метод CheckRemoveSpecificName (Репитед тест №4)

    @RepeatedTest(3)
    public void testCheckRemoveSpecificName() {
            List<String> list = Arrays.asList("Gosha", "Tosha", "Alesha", "Aboba", "Neaboba");
            String toRemove = "Alesha";
            List<String> expected = new ArrayList<>();
            for (String s : list) {
                if (!s.equals(toRemove)) expected.add(s);
            }
            List<String> actual = Utils.removeSpecificName(list, toRemove);
            if (actual.equals(expected)) {
                System.out.println("TEST PASSED: removeSpecificName(" + list + ", " + toRemove + ") -> " + actual);
            } else {
                System.out.println("TEST FAILED: removeSpecificName(" + list + ", " + toRemove + ") -> " + actual + ", expected " + expected);
            }
        }
}

