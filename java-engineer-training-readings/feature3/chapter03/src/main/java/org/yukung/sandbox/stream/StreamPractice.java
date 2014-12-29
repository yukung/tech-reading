package org.yukung.sandbox.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamPractice {
    public static void main(String[] args) throws Exception {
        new StreamPractice().run();
    }

    private void run() throws Exception {
        printEven();
        printSeparator();
        printStudentsName();
        printSeparator();
        printMaxValue();
        printSeparator();
        Path path = Paths.get("./");
        System.out.println(containsTextFile(path));
        printSeparator();
        List<Emp> emps = Arrays.asList(new Emp(500), new Emp(1000), new Emp(800));
        empsOfHighIncome(emps).forEach(System.out::println);
        printSeparator();
        Student[] students = new Student[]{new Student("山田太郎"), new Student("鈴木花子"), new Student("佐藤一郎")};
        System.out.println(toStringByName(students));
        printSeparator();
    }

    private String toStringByName(Student[] students) {
        return Arrays.stream(students)
                .map(Student::getName)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private List<Emp> empsOfHighIncome(List<Emp> emps) {
        return emps.stream()
                .filter(emp -> emp.getIncome() >= 800)
//                .collect(ArrayList::new,
//                        ArrayList::add,
//                        ArrayList::addAll);
                .collect(Collectors.toList());  // Collectors クラスは collect メソッドを使う際のユーティリティクラス
    }

    private boolean containsTextFile(Path dir) throws IOException {
        return Files.walk(dir)
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .anyMatch(name -> name.endsWith(".txt"));
    }

    private void printMaxValue() {
        Path path = Paths.get("./data.txt");
        try (Stream<String> s = Files.lines(path)) {
            OptionalInt maxOpt = s.mapToInt(Integer::parseInt).max();
            maxOpt.ifPresent(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printStudentsName() {
        List<Student> students = Arrays.asList(new Student("Ichiro"), new Student("Jiro"), new Student("Saburo"));
        students.stream()
                .map(Student::getName)
                .forEach(System.out::println);
    }

    private void printSeparator() {
        System.out.println("-------------------------------");
    }

    private void printEven() {
        IntStream.rangeClosed(1, 10)
                .filter(n -> n % 2 == 0)
                .forEach(System.out::println);
    }

    private static class Emp {
        private int income;

        public int getIncome() {
            return income;
        }

        public void setIncome(int income) {
            this.income = income;
        }

        public Emp(int income) {

            this.income = income;
        }

        @Override
        public String toString() {
            return "Emp{" +
                    "income=" + income +
                    '}';
        }
    }

    private static class Student {
        private String name;

        public Student(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
