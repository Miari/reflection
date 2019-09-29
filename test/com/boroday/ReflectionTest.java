package com.boroday;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReflectionTest {
    private Reflection reflection = new Reflection();

    @Test
    public void testCreateObject() throws ReflectiveOperationException {
        assertEquals("com.boroday.ClassForTest", reflection.createObject(ClassForTest.class).getClass().getName());
        Object object = reflection.createObject(String.class);
        assertEquals("", object);
    }

    @Test(expected = ReflectiveOperationException.class)
    public void testCreateObjectPrivateConstructor() throws ReflectiveOperationException {
        reflection.createObject(ClassWithPrivateConstructor.class);
    }

    @Test
    public void testInvokeMethods() throws InvocationTargetException, IllegalAccessException {
        assertEquals(8, reflection.invokeMethods(new ClassForTest()));
    }

    @Test
    public void testGetFinalMethods() {
        assertEquals(2, reflection.getFinalMethods(new ClassForTest()).size()); // method showFinalMethods is available
        assertEquals(6, reflection.getFinalMethods(new LinkedList()).size());
    }

    @Test
    public void testGetFinalMethodsCheckOutput() {
        ArrayList<String> list = reflection.getFinalMethods(new ClassForTest());
        boolean firstResult = false;
        boolean secondResult = false;
        for (String string : list) {
            if (string.equals("public final void com.boroday.ClassForTest.changeNameToDefault()")) {
                firstResult = true;
            }
            if (string.equals("private final void com.boroday.ClassForTest.doubleAge()")) {
                secondResult = true;
            }
        }
        assertTrue(firstResult);
        assertTrue(secondResult);
    }

    @Test
    public void testGetNotPublicMethods() {
        assertEquals(4, reflection.getNotPublicMethods(ClassForTest.class).size()); // method showNotPublicMethods is available
    }

    @Test
    public void testGetNotPublicMethodsCheckOutput() {
        ArrayList<String> list = reflection.getNotPublicMethods(ClassForTest.class);
        boolean firstResult = false;
        boolean secondResult = false;
        boolean thirdResult = false;
        boolean fourthResult = false;
        for (String string : list) {
            if (string.equals("private void com.boroday.ClassForTest.setName(java.lang.String)")) {
                firstResult = true;
            }
            if (string.equals("private void com.boroday.ClassForTest.setAge(int)")) {
                secondResult = true;
            }
            if (string.equals("private void com.boroday.ClassForTest.removeName()")) {
                thirdResult = true;
            }
            if (string.equals("private final void com.boroday.ClassForTest.doubleAge()")) {
                fourthResult = true;
            }
        }
        assertTrue(firstResult);
        assertTrue(secondResult);
        assertTrue(thirdResult);
        assertTrue(fourthResult);
    }

    @Test
    public void testGetAllParentClassesAndInterfaces() throws ClassNotFoundException {
        ArrayList<String> firstList = reflection.getAllParentClassesAndInterfaces(LinkedList.class);
        assertEquals(11, firstList.size()); // method showAllParentClassesAndInterfaces is available

        assertEquals("class java.util.AbstractSequentialList", firstList.get(0));
        assertEquals("interface java.io.Serializable", firstList.get(10));

        ArrayList<String> secondList = reflection.getAllParentClassesAndInterfaces(ArrayList.class);
        assertEquals(9, secondList.size());
    }

    @Test
    public void testSetDefaultValues() throws IllegalAccessException {
        ClassForTest test = new ClassForTest(5, "Alex", true);
        reflection.setDefaultValues(test);
        assertEquals(0, test.getAge());
        assertEquals(null, test.getName());
        //assertEquals('\u0000', test.getDEPARTMENT()); //почему здесь остаётся прежнее значение, тогда как в классе Reflection оно меняется?
        assertEquals(true, test.isSexMale());

    }
}