package com.boroday.reflection.testClasses;

public class ClassForTest {
    private final static char DEFAULT_DEPARTMENT = 'U';

    private int age;
    private String name;
    public boolean sexMale;
    private final char department;
    public final double default_salary = 1500.8;

    public ClassForTest() {
        this (0, "InitialName", true, DEFAULT_DEPARTMENT);
    }

    public ClassForTest(int age, String name, boolean sexMale, char department) {
        this.age = age;
        this.name = name;
        this.sexMale = sexMale;
        this.department = department;
    }

    public int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public boolean isSexMale() {
        return sexMale;
    }

    public char getDEPARTMENT() {
        return department;
    }

    public double getDEFAULT_SALARY() {
        return default_salary;
    }

    private void removeName() {
        this.name = "NoName";
    }

    final private void doubleAge() {
        age = age * 2;
    }

    final public void changeNameToDefault() {
        name = "DefaultName";
    }
}
