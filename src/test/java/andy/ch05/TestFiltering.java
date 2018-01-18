package andy.ch05;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.summarizingInt;

/**
 * @Author: andy.lv
 * @Date: created on 2018/1/17
 * @Description:
 */
public class TestFiltering {

    @Test
    public void distinct() {
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 2, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .limit(1)
                .forEach(System.out::println);
    }

    @Test
    public void map() {
        List<String> words = Arrays.asList("Hello", "world", "welcome");
        words.stream()
                .map(String::length)
                .forEach(System.out::println);
    }

    @Test
    public void flatMap() {
        List<String> words = Arrays.asList("hello", "world", "welcome");
        words.stream()
                .map(s -> s.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void pair() {
        List<Integer> num1 = Arrays.asList(1, 3, 5);
        List<Integer> num2 = Arrays.asList(2, 4);
        num1.stream()
                .flatMap(i -> num2.stream().map((j) -> "(" + i + ", " + j + ")"))
                .forEach(System.out::println);
    }

    @Test
    public void anyMatch() {
        List<Integer> nums = Arrays.asList(1, 2, 3);
        if(nums.stream().anyMatch(i -> i % 2 == 0))
            System.out.println("find out even number");
    }

    @Test
    public void allMatch() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        if(nums.stream().allMatch(i -> i < 10))
            System.out.println("all nums less than 10");
    }

    @Test
    public void noneMatch() {
        List<Integer> nums = Arrays.asList(1, 3, 5, 7, 9);
        if(nums.stream().noneMatch(i -> i % 2 == 0))
            System.out.println("all are odd nums");
    }

    @Test
    public void findAny() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        nums.stream()
                .filter(i -> i % 2 == 0)
                .findAny()
                .ifPresent(num -> System.out.println(num));
        ;

        nums.stream()
                .filter(i -> i > 4)
                .findAny()
                .ifPresent(num -> System.out.println(num));

    }

    @Test
    public void findFirst() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        nums.stream()
                .map(x -> x * x)
                .filter(i -> i % 3 == 0)
                .findFirst()
                .ifPresent(num -> System.out.println(num));
    }

    @Test
    public void reduce() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        nums.stream()
                .reduce(Integer::sum)
                .ifPresent(System.out::println);

        List<Integer> num = Arrays.asList(7);
        num.stream()
                .reduce(Integer::sum)
                .ifPresent(System.out::println);

        num.stream()
                .reduce(Integer::max)
                .ifPresent(System.out::println);

        List<String> words = Arrays.asList("hello", "world", "welcome");
        words.stream()
                .map(d -> 1)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
    }

    @Test
    public void sortYear() {
        List<Integer> years = Arrays.asList(2001, 2002, 2010, 2004, 2003, 2009);
        years.stream()
                .sorted(comparing((Integer i) -> i).reversed())
                .forEach(System.out::println);

    }

    @Test
    public void max() {
        List<Integer> years = Arrays.asList(1992, 1993, 1991, 2001);
        years.stream()
                .reduce(Integer::max)
                .ifPresent(System.out::println);
    }

    @Test
    public void min() {
        List<Integer> years = Arrays.asList(2001, 1999, 2010);
        years.stream()
                .reduce(Integer::min)
                .ifPresent(System.out::println);
    }

    @Test
    public void rangeClosed() {
        long count = IntStream.rangeClosed(1, 100)
        .filter(i -> i % 2 == 0)
        .count()
        ;
        System.out.println(count);
    }

    @Test
    public void range() {
        long count = IntStream.range(1, 100)
                .filter(i -> i % 2 == 0)
                .count();
        System.out.println(count);
    }

    @Test
    public void triple() {
        IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap( a -> IntStream.rangeClosed(a, 100).mapToObj(b -> new double[]{a, b, Math.sqrt(a*a + b*b)}).filter(t -> t[2] % 1 == 0))
                .forEach(t -> System.out.println((int)t[0] + ", " + (int)t[1] + ", " + (int)t[2]));
    }

    @Test
    public void streamsFromValue() {
        Stream<String> valus = Stream.of("hello", "world", "welcome");
        valus.forEach(System.out::println);
    }

    @Test
    public void streamFromArrays() {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int sum = Arrays.stream(nums).sum();
        System.out.println(sum);
    }

    @Test
    public void streamsFromFile() throws IOException {
        String filePath = this.getClass().getResource("data.txt").getFile();
        File file = new File(filePath);
        Stream<String> lines = Files.lines(Paths.get(file.getAbsolutePath()), Charset.defaultCharset());
        long uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();
        System.out.println(uniqueWords);

        uniqueWords = Files.lines(Paths.get(file.getAbsolutePath()), Charset.defaultCharset())
                .map(line -> line.split(" "))
                .flatMap(Arrays::stream)
                .distinct()
                .count();
        System.out.println(uniqueWords);
    }

    @Test
    public void infiniteStreams() {
        Stream.iterate(new int[] {0, 1}, t -> new int[] {t[1], t[0] + t[1]})
                .limit(10)
                .forEach(t -> System.out.println(t[0] + ", " + t[1]));
    }

    @Test
    public void infiniteStreams_generate() {
        IntSupplier is = new IntSupplier() {
            int prev = 0;
            int current = 1;
            @Override
            public int getAsInt() {
                int result = prev;
                prev = current;
                current = result + prev;
                return result;
            }
        };
        IntStream.generate(is).limit(10)
                .forEach(System.out::println);
    }

    @Test
    public void collectMax() {
        Arrays.asList(1, 2, 3, 4, 5)
                .stream()
                .collect(maxBy(Comparator.comparingInt((Integer i) -> i)))
        .ifPresent(System.out::println);
    }

    @Test
    public void collectMin() {
        Arrays.asList("welcome", "hello", "world")
                .stream()
                .collect(minBy(comparing((s) -> s)))
                .ifPresent(System.out::println);
    }

    @Test
    public void summingInt() {
        int tlen = Arrays.asList("hello", "world", "welcome")
                .stream()
                .collect(Collectors.summingInt(String::length));
        System.out.println(tlen);
    }
}
