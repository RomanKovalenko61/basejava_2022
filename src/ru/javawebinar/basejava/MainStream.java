package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));

        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            integers.add(i);
        }
        System.out.println(oddOrEven(integers));
        List<Integer> integers1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            integers1.add(i);
        }
        System.out.println(oddOrEven(integers1));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (x, y) -> x * 10 + y);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(0, Integer::sum);
        System.out.println("sum all numbers = " + sum);
        if (sum % 2 == 0) {
            return integers.stream().filter(x -> x % 2 != 0).collect(Collectors.toList());
        } else {
            return integers.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        }
    }
}
