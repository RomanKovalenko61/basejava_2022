package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.partitioningBy;

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
        final Integer[] sum = {0};
        Map<Boolean, List<Integer>> collect = integers.stream().peek(e -> sum[0] += e)
                .collect(partitioningBy(x -> x % 2 == 0));
        System.out.println("sum all numbers = " + sum[0]);
        return collect.get(sum[0] % 2 != 0);
    }
}
