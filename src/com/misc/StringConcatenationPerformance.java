package com.misc;

import java.util.*;

public class StringConcatenationPerformance {

    public static void main(String[] args) {
        List<String> strList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            String randomString = UUID.randomUUID().toString();
            randomString = randomString.replace("_", " ");
            strList.add(randomString);
        }
        System.out.println("Concat time: " + Utils.measurePerformance(() -> stringConcat(strList)));
        System.out.println("Builder time: " + Utils.measurePerformance(() -> stringBuilder(strList)));
    }

    public static void stringConcat(List<String> strList) {
        String str = "";
        for (String randomStr : strList) {
            str += randomStr + " ";
        }
    }

    public static void stringBuilder(List<String> strList) {
        StringBuilder strBuilder = new StringBuilder();
        for (String randomStr : strList) {
            strBuilder.append(randomStr).append(" ");
        }
    }
}
