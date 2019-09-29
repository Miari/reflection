package com.boroday;

public class ClassForTest {
    private int age;
    private String name;
    public boolean sexMale;
    final private char DEPARTMENT = 'U';
    final public double DEFAULT_SALARY = 1500.8;

    public ClassForTest() {
        this.age = 0;
        this.name = "InitialName";
        this.sexMale = true;
    }

    public ClassForTest(int age, String name, boolean sexMale) {
        this.age = age;
        this.name = name;
        this.sexMale = sexMale;
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
        return DEPARTMENT;
    }

    public double getDEFAULT_SALARY() {
        return DEFAULT_SALARY;
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
