package com.boroday.reflection.testClasses;

public class TestClassWithMethodWithoutParameters {
    public boolean shouldBeChanged = false;
    public boolean shouldNotBeChanged = false;

    public void methodWithoutParameters(){
        shouldBeChanged = true;
    }

    public void methodWithoutParameters(String arg){
        shouldNotBeChanged = true;
    }
}
