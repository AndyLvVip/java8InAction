package andy.ch06;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;

public class TestPrimeNumber {

    private static boolean isPrime(int num) {
        int sqrtNum = (int) Math.sqrt(num);
        return IntStream.rangeClosed(2, sqrtNum)
                .noneMatch(i -> num % i == 0);
    }

    private static boolean isPrime(List<Integer> primeList, Integer num) {
        int sqrtNum = (int) Math.sqrt(num);
        return takeWhile(primeList, i -> i <= sqrtNum).stream().noneMatch(p -> num % p == 0);
    }

    public static <A> List<A> takeWhile(List<A> list, Predicate<A> pred) {
        int i = 0;
        for(A item : list) {
            if(!pred.test(item))
                return list.subList(0, i);
            i++;
        }
        return list;
    }

    @Test
    public void testPrime() {
        Assert.assertFalse(isPrime(1));
    }

    @Test
    public void testCustomPrime() {
        Assert.assertFalse(isPrime(Collections.emptyList(), 1));
    }

    @Test
    public void testIsPrime() {
        Assert.assertTrue(isPrime(3));
        Assert.assertFalse(isPrime(10));
    }

    @Test
    public void partitioningPrime() {
        IntStream.rangeClosed(1, 20)
                .boxed()
                .collect(partitioningBy(TestPrimeNumber::isPrime))
                .forEach((k, v) -> System.out.println(k + "->" + v));
    }

    private void partitioningByPrime(int nums) {
        IntStream.rangeClosed(2, nums)
                .boxed()
                .collect(partitioningBy(TestPrimeNumber::isPrime))
        .forEach((k, v) -> System.out.println(k + "->" + v.size()));
    }

    private void customPartitionByPrime(int nums) {
        IntStream.rangeClosed(2, nums)
                .boxed()
                .collect(new CustomPrimeCollector())
        .forEach((k, v) -> System.out.println(k + "->" + v.size()));
    }

    public static class CustomPrimeCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return () -> new HashMap<Boolean, List<Integer>>() {
                {
                    put(true, new ArrayList<Integer>());
                    put(false, new ArrayList<Integer>());
                }
            };
        }

        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (map, num) -> {
                map.get(isPrime(map.get(true), num)).add(num);
            };
        }

        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
            return (map, map2) -> {
                map.get(true).addAll(map2.get(true));
                map.get(false).addAll(map2.get(false));
                return map;
            };
        }

        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
        }
    }

    @Test
    public void testPrimePartition() {
        int nums = 1_000_000;
        long start = System.currentTimeMillis();
        partitioningByPrime(nums);
        System.out.println("partitionByPrime times:" + (System.currentTimeMillis() - start) + "ms");
        start = System.currentTimeMillis();
        customPartitionByPrime(nums);
        System.out.println("customPartitionByPrime times:" + (System.currentTimeMillis() - start) + "ms");
    }

    @Test
    public void numberOfProcessors() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
