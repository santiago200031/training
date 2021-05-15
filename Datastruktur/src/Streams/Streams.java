package src.Streams;

import java.text.Bidi;
import java.util.*;
import java.util.stream.Collectors;

public class Streams {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();

        for (int i = 1; i <= 10; i++) {
            queue.add(i);
            stack.add(i);
        }


        List<Integer> collect = queue.parallelStream()
                .map(x -> x * x)
                .collect(Collectors.toList())
                .parallelStream()
                .filter(x -> x % 2 == 0)
                .collect(Collectors.toList());
        collect.parallelStream().forEach(System.out::println);
        List<Integer> integers = stack.stream().toList();

        HashMap<Integer, HashMap<Integer, Float>> hashMap = new HashMap<>();
        HashMap<Integer, Float> hashMap1 = new HashMap<>();
        HashMap<Integer, Float> hashMap2 = new HashMap<>();
        HashMap<Integer, Float> hashMap3 = new HashMap<>();

        hashMap1.put(2, 3F);
        hashMap1.put(4, 2F);
        hashMap1.put(6, 5F);
        hashMap2.put(2, 3F);
        hashMap2.put(4, 2F);
        hashMap2.put(6, 5F);
        hashMap3.put(2, 3F);
        hashMap3.put(4, 2F);
        hashMap3.put(6, 5F);

        hashMap.put(1, hashMap1);
        hashMap.put(2, hashMap2);
        hashMap.put(3, hashMap3);
        LinkedHashMap linkedHashMap = new LinkedHashMap();

        System.out.println(linkedHashMap);

    }
}
