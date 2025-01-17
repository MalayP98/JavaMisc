package com.misc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateRandomIntList {

    public static List<Integer> randomSortedInts(int size, int min, int max){
        List<Integer> randomSortedInts = generate(size, min, max);
        randomSortedInts.sort(Comparator.naturalOrder());
        return randomSortedInts;
    }

    public static List<Integer> generate(int size, int min, int max){
        List<Integer> randomInts = new ArrayList<>();
        for(int i=0; i<size; i++){
            randomInts.add(ThreadLocalRandom.current().nextInt(min, max));
        }
        return randomInts;
    }

    public static void main(String[] args){
        System.out.println(generate(7, 1, 30));
    }
}

