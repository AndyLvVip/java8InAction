package andy.ch05;

import org.junit.Test;

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

    public void flatMap() {
        List<String> words = Arrays.asList("hello", "world", "welcome");

    }
}
