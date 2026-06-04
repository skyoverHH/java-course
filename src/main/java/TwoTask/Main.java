package TwoTask;

import ClassForTwoTask.Employee;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        System.out.println("Задача 1");
        List<Integer> firstListInt = List.of(5, 2, 10, 9, 4, 3, 10, 1, 13);

        int answerFirstTask = firstListInt.stream()
                        .sorted(Comparator.reverseOrder())
                        .skip(2)
                        .findFirst()
                        .orElse(0);

        System.out.println(answerFirstTask);

        List<Integer> secondListInt = List.of(5, 2, 10, 9, 4, 3, 10, 1, 13);

        System.out.println("Задача 2");
        //Сложно с точки зрения алгоритма сортировки)
        int answerSecondTask = secondListInt.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(0);

        System.out.println(answerSecondTask);

        Employee firstWorker = new Employee("Alex", 22, "Engineer");
        Employee secondWorker = new Employee("Denis", 23, "Engineer");
        Employee thirdWorker = new Employee("Alex", 21, "Engineer");
        Employee fourthWorker = new Employee("Alex", 24, "Manager");

        List<Employee> employeeList = List.of(firstWorker, secondWorker, thirdWorker, fourthWorker);

        System.out.println("Задача 3");
        List<String> engineerNames = employeeList.stream()
                .filter(employee -> "Engineer".equals(employee.getJobTitle()))
                .sorted(Comparator.comparingInt(Employee::getAge).reversed())
                .limit(3)
                .map(Employee::getSecondName)
                .toList();

        System.out.println(engineerNames);

        System.out.println("Задача 4");
        Double avgAge = employeeList.stream()
                .filter(employee -> "Engineer".equals(employee.getJobTitle()))
                .mapToInt(Employee::getAge)
                .average()
                .orElse(0);

        System.out.println(avgAge);

        System.out.println("Задача 5");
        List<String> maxLength= List.of("sadasdasdasdasdasdadsdasd", "dwdw", "dw");

        String longestWord = maxLength.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");

        System.out.println(longestWord);

        System.out.println("Задача 6");

        String spaceWord = "dwdw dwdw ff bb ff bb lk oe gr";

        Map<String, Integer> answer = Arrays.stream(spaceWord.split("\\s+"))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        HashMap::new,
                        Collectors.summingInt(word -> 1)
                ));

        System.out.println(answer.toString());

        System.out.println("Задача 7");

        List<String> stringLine = List.of("dwdw", "dwdw", "dcd", "qqe", "aaa", "vfdvf");

        stringLine.stream()
                .sorted(Comparator.comparingInt(String::length)
                        .thenComparing(word -> word))
                .forEach(System.out::println);

        String[] lines = {
            "ok kek dd aa dwd",
            "yes nono opap ddd lol",
            "opa dd l mm wewewewewew"
        };
        System.out.println("Здача 8");

        String word = Arrays.stream(lines)
            .flatMap(line -> Arrays.stream(line.split("\\s+")))
            .max(Comparator.comparingInt(String::length))
            .orElse("");

        System.out.println(word);

    }

}