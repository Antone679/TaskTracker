package ru.avdei.springcourse.util;

public class IdGenerator {
    private static int newId;

    public static int generateId(){
        newId++;
        return newId;
    }
    public static void setGeneratorId(int count){
        newId = count;
    }

    public static int getCurrentId() {
        return newId;
    }
}
