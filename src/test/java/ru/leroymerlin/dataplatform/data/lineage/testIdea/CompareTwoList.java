package ru.leroymerlin.dataplatform.data.lineage.testIdea;

import java.util.Arrays;
import java.util.List;

public class CompareTwoList {
    public static void main(String[] args) {
        List first = Arrays.asList(1, 3, 4, 6, 8);
        List second = Arrays.asList(8, 1, 6, 3, 4);
        List third = Arrays.asList(1, 3, 3, 6, 6);
        System.out.println(first.containsAll(second));
    }
}
