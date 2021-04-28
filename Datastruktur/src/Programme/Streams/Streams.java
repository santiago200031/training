package Programme.Streams;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

public class Streams {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();

        for (int i = 1; i <= 10; i++) {
            queue.add(i);
            stack.add(i);
        }


        /*
 List<Integer> collect = queue.parallelStream().map(x -> x * x).collect(Collectors.toList()).parallelStream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        collect.parallelStream().forEach(System.out::println);
        List<Integer> integers = stack.stream().toList();
         */

    }
}
