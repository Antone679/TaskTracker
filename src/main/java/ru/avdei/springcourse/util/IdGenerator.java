package ru.avdei.springcourse.util;

public class IdGenerator {
    private static int newId;

    public static int generateId(){
        newId++;
        return newId;
    }

}
