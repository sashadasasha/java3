package com.geekbrains.java3;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	    //task1 check
        String[] stringArr = {"one", "two", "three", "four", "five"};
        changePositionOfElements(stringArr, 1, 3);
        showArray(stringArr);
        Integer[] intArr = {1, 2, 3, 4, 5};
        changePositionOfElements(intArr, 2, 4);
        showArray(intArr);
        //task2 check
        ArrayList<String> stringList = toArrayList(stringArr);
        showArrayList(stringList);
        ArrayList<Integer> intList = toArrayList(intArr);
        showArrayList(intList);
        //task3 check
        Box<Orange> orangeBox = new Box<>();
        Box<Apple> appleBox = new Box<>();
        orangeBox.add(new Orange());
        orangeBox.add(new Orange());
        appleBox.add(new Apple());
        appleBox.add(new Apple());
        appleBox.add(new Apple());
        System.out.printf("orange box weight = %.2f%n", orangeBox.getWeight());
        System.out.printf("apple box weight = %.2f%n", appleBox.getWeight());
        System.out.printf("weight the same (1) = %s%n", orangeBox.compare(appleBox));
        System.out.printf("weight the same (2) = %s%n", appleBox.compare(orangeBox));
        orangeBox.add(new Orange());
        System.out.printf("weight the same (1) = %s%n", orangeBox.compare(appleBox));
        System.out.printf("weight the same (2) = %s%n", appleBox.compare(orangeBox));
        Box<Orange> newOrangeBox = new Box<>();
        newOrangeBox.add(new Orange());
        orangeBox.moveTo(newOrangeBox);
        System.out.printf("old orange box weight = %.2f%n", orangeBox.getWeight());
        System.out.printf("new orange box weight = %.2f%n", newOrangeBox.getWeight());

    }

    //task1

    public static <T> void changePositionOfElements(T[] arr, int elementOnePosition, int elementTwoPosition) {
        T temp = arr[elementOnePosition];
        arr[elementOnePosition] = arr[elementTwoPosition];
        arr[elementTwoPosition] = temp;
    }

    public static <T> void showArray (T[] arr) {
        for (T element : arr) {
            System.out.println(element);
        }
    }

    //task2

    private static <T> ArrayList<T> toArrayList(T[] arr) {
        return new ArrayList<>(Arrays.asList(arr));
    }

    public static <T> void showArrayList (ArrayList<T> arrList) {
        for (T element : arrList) {
            System.out.println(element);
        }
    }
}
