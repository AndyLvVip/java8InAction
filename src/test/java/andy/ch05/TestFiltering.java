package andy.ch05;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

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
}
