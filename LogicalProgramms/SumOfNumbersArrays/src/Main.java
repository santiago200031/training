import java.util.*;

import static java.util.Arrays.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Array lenght: ");
        int lenghtArrays = input.nextInt();

        System.out.print("Value to Find: ");
        int valueToFind = input.nextInt();

        ArrayList array1 = new ArrayList();
        ArrayList array2 = new ArrayList();
        for (int i = 0; i < lenghtArrays; i++) {
            array1.add((int) (Math.random() * 10));
            array2.add((int) (Math.random() * 10));
        }

        array1.stream().forEach(i -> {
            System.out.print(i + " ");
        });

        System.out.println();

        array2.stream().forEach(i -> {
            System.out.print(i + " ");
        });
        System.out.println();
        findSumOfArraysWhereTheSumIs(valueToFind, array1, array2);


        System.out.println();


        input.close();
    }

    public static void findSumOfArraysWhereTheSumIs(int valueToFind, ArrayList<Integer> list1, ArrayList<Integer> list2) {
        list1 = sortList(list1);

        Stack<Integer> stack = new Stack<>();
        int sum;
        int n = list1.size() - 1;
        int m = 0;
        int numberOfList1, numberOfList2 = 0;
        while (n >= 0 && m < list2.size()) {
            numberOfList1 = list1.get(n);
            numberOfList2 = list2.get(m);
            sum = numberOfList1 + numberOfList2;
            if (sum == valueToFind) {
                stack.push(m);
                stack.push(n);
                m++;
            } else {
                if (sum > valueToFind) {
                    m++;
                } else {
                    n--;
                }
            }
        }
        printStack(stack);
    }

    private static ArrayList<Integer> sortList(ArrayList<Integer> list) {
        int num = list.get(0);
        int n = list.size();
        for (int i = 1; i < n; i++) {
            if (num > list.get(i)) {
                int temp = list.get(i);
                list.add(i, num);
                list.add(i - 1, temp);
            }
            n--;
            num = list.get(0);
            i = 1;
        }
        System.out.println("SortedList: " + list.toString());
        return list;
    }

    private static void printStack(Stack<Integer> stack) {
        int length = stack.size();
        for (int i = 0; i < length; i = i + 2) {
            System.out.println(stack.pop() + "  " + stack.pop());
        }
    }
}
