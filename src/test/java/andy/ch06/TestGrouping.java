package andy.ch06;

import org.junit.Test;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * @Author: andy.lv
 * @Date: created on 2018/1/19
 * @Description:
 */
public class TestGrouping {

    public static class Animal{
        private int age;
        private double price;
        private String name;
        private Type type;
        private boolean male;

        public boolean isMale() {
            return male;
        }

        public void setMale(boolean male) {
            this.male = male;
        }

        public static Stream<Animal> ofDummy() {
            return Stream.of(
                    new Animal(1, 100.01, "CAT1", Type.CAT, true),
                    new Animal(5, 10.55, "CAT2", Type.CAT, false),
                    new Animal(10, 50.22, "CAT3", Type.CAT, true),
                    new Animal(1, 20.25, "DOG1", Type.DOG, false),
                    new Animal(5, 30.33, "DOG2", Type.DOG, true),
                    new Animal(10, 100.10, "DOG3", Type.DOG, false)
            );
        }

        public enum Price {
            CHEAP,
            LOW,
            EXPENSIVE,
            ;
        }

        public enum Age {
            YOUNG,
            MIDDLE,
            OLD,
            ;
        }

        public enum Type {
            CAT,
            DOG,
            ;
        }

        public Animal(int age, double price, String name, Type type, boolean male) {
            this.age = age;
            this.price = price;
            this.name = name;
            this.type = type;
            this.male = male;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return String.format("{name: %s, price: %.2f, age: %d, type: %s, gender: %s}", name, price, age, type, male ? "Male" : "Female");
        }
    }

    @Test
    public void groupingByType() {
        Animal.ofDummy().collect(groupingBy(Animal::getType))
                .forEach((k, v) -> System.out.println(v));
    }

    @Test
    public void groupByTypeAndThenAge() {
        Animal.ofDummy().collect(groupingBy(Animal::getType,
                groupingBy((animal) -> {
                    if(animal.getAge() > 1) {
                        if(animal.getAge() > 5)
                            return Animal.Age.OLD;
                        else return Animal.Age.MIDDLE;
                    }else {
                        return Animal.Age.YOUNG;
                    }
                })))
                .forEach((k, v) -> System.out.println(v));
    }

    @Test
    public void groupByTypeAndThenCount() {
        Animal.ofDummy().collect(groupingBy(Animal::getType, counting()))
        .forEach((k, v) -> System.out.println(k + "->" + v));
    }

    @Test
    public void groupByTypeAndThenAgeAndThenFetchMax() {
        Animal.ofDummy().collect(groupingBy(Animal::getType, groupingBy((animal) -> {
            if(animal.getAge() > 1) {
                if(animal.getAge() > 5)
                    return Animal.Age.OLD;
                else return Animal.Age.MIDDLE;
            }else {
                return Animal.Age.YOUNG;
            }
        },
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Animal::getPrice)), Optional::get)

        ))).forEach((k, v) -> System.out.println(k + "->" + v));
    }

    @Test
    public void partitioningBy() {
        Animal.ofDummy().collect(Collectors.partitioningBy(Animal::isMale))
                .forEach((k, v) -> System.out.println(k + "->" + v));
    }

    @Test
    public void partitioningAndThenFindMax() {
        Animal.ofDummy().collect(Collectors.partitioningBy(Animal::isMale,
                collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Animal::getAge)), Optional::get)
                )).forEach((k, v) -> System.out.println(k + "->" + v));
    }
}
