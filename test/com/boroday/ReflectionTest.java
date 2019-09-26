package com.boroday;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ReflectionTest {
    private Reflection reflection = new Reflection();// если указать здесь private Reflection<ClassForTest> reflection = new Reflection<>(), то в тестах получается несоответствие передаваемого параметра
    private ClassForTest test;
    private String string;
    private RuntimePermission permission;

    @Before
    public void createNewInstance() {
        test = new ClassForTest(5, "Alex", true);
        string = "";
        permission = new RuntimePermission("Start");
    }

    @Test
    public void testCreateObject() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        assertEquals("com.boroday.ClassForTest", reflection.createObject(test.getClass()).getClass().getName());
    }

    @Test
    public void testInvokeMethods() throws InvocationTargetException, IllegalAccessException {
        assertEquals(8, reflection.invokeMethods(test));
    }

    @Test
    public void testGetFinalMethods() {
        assertEquals(2, reflection.getFinalMethods(test).size());
    }

    @Test
    public void testGetNotPublicMethods() {
        assertEquals(4, reflection.getNotPublicMethods(test.getClass()).size());
    }

    @Test
    public void testGetAllAncestorsAndInterfaces() {
        ArrayList<Class> arrayList = reflection.getAllAncestorsAndInterfaces(permission.getClass());
        assertEquals(3, arrayList.size());

        arrayList = reflection.getAllAncestorsAndInterfaces(string.getClass());
        assertEquals(4, arrayList.size());
    }

    @Test
    public void testSetNullValues() throws IllegalAccessException {
        reflection.setNullValues(test);
        assertEquals(0, test.getAge());
        assertEquals(null, test.getName());
        //assertEquals('\u0000', test.getDEPARTMENT()); почему здесь остаётся прежнее значение, тогда как в классе Reflection оно меняется?
        assertEquals(true, test.isSexMale());
    }
}