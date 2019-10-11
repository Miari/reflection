package com.boroday.reflection;

import com.boroday.reflection.testClasses.ClassForTest;
import com.boroday.Reflection;
import com.boroday.reflection.testClasses.ClassWithDifferentDataTypes;
import com.boroday.reflection.testClasses.ClassWithPrivateConstructor;
import com.boroday.reflection.testClasses.TestClassWithMethodWithoutParameters;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.Assert.*;

public class ReflectionTest {
    private Reflection reflection = new Reflection();

    @Test
    public void testCreateObject() throws ReflectiveOperationException {
        assertEquals(ClassForTest.class, reflection.createObject(ClassForTest.class).getClass());
        Object object = reflection.createObject(String.class);
        assertEquals("", object);
    }

    @Test(expected = ReflectiveOperationException.class)
    public void testCreateObjectFailsWhenPrivateConstructor() throws ReflectiveOperationException {
        reflection.createObject(ClassWithPrivateConstructor.class);
    }

    @Test
    public void testInvokeMethodsWithoutParameters() throws InvocationTargetException, IllegalAccessException {
        TestClassWithMethodWithoutParameters testClass = new TestClassWithMethodWithoutParameters();
        reflection.invokeMethodsWithoutParameters(testClass);
        assertTrue(testClass.shouldBeChanged);
        assertFalse(testClass.shouldNotBeChanged);
    }

    @Test
    public void testGetFinalMethods() {
        ArrayList<String> list = reflection.getFinalMethods(new ClassForTest());
        assertEquals(2, reflection.getFinalMethods(new ClassForTest()).size());
        // assertEquals(6, reflection.getFinalMethods(new LinkedList()).size());
        assertTrue(list.remove("public final void com.boroday.reflection.testClasses.ClassForTest.changeNameToDefault()"));
        assertTrue(list.remove("private final void com.boroday.reflection.testClasses.ClassForTest.doubleAge()"));
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGetNotPublicMethodsCheckOutput() {
        ArrayList<String> list = reflection.getNotPublicMethods(ClassForTest.class);
        assertEquals(4, reflection.getNotPublicMethods(ClassForTest.class).size());
        assertTrue(list.remove("private void com.boroday.reflection.testClasses.ClassForTest.setName(java.lang.String)"));
        assertTrue(list.remove("private void com.boroday.reflection.testClasses.ClassForTest.setAge(int)"));
        assertTrue(list.remove("private void com.boroday.reflection.testClasses.ClassForTest.removeName()"));
        assertTrue(list.remove("private final void com.boroday.reflection.testClasses.ClassForTest.doubleAge()"));
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGetAllParentClassesAndInterfaces() throws ClassNotFoundException {
        ArrayList<Class> arrayList = reflection.getAllParentClassesAndInterfaces(LinkedList.class);
        assertEquals(11, arrayList.size());
        assertTrue(arrayList.remove(AbstractSequentialList.class));
        assertTrue(arrayList.remove(AbstractList.class));
        assertTrue(arrayList.remove(AbstractCollection.class));
        assertTrue(arrayList.remove(Object.class));
        assertTrue(arrayList.remove(List.class));
        assertTrue(arrayList.remove(Collection.class));
        assertTrue(arrayList.remove(Iterable.class));
        assertTrue(arrayList.remove(Deque.class));
        assertTrue(arrayList.remove(Queue.class));
        assertTrue(arrayList.remove(Cloneable.class));
        assertTrue(arrayList.remove(Serializable.class));
        assertTrue(arrayList.isEmpty());
    }

    @Test
    public void testSetDefaultValuesToPrivateFields() throws IllegalAccessException {
        ClassWithDifferentDataTypes testClass = new ClassWithDifferentDataTypes();
        reflection.setDefaultValuesToPrivateFields(testClass);
        assertEquals(0, testClass.getPrivateByte());
        assertEquals(5, testClass.getPublicByte());
        assertEquals(0, testClass.getPrivateInteger());
        assertEquals(5, testClass.getPublicInteger());
        assertEquals(0, testClass.getPrivateShort());
        assertEquals(5, testClass.getPublicShort());
        assertEquals(0, testClass.getPrivateLong());
        assertEquals(5, testClass.getPublicLong());
        assertEquals(0.0, testClass.getPrivateDouble(), 0);
        assertEquals(5.0, testClass.getPublicDouble(), 0);
        assertEquals(0.0, testClass.getPrivateFloat(), 0);
        assertEquals(5.0, testClass.getPublicFloat(), 0);
        assertEquals(0, testClass.getPrivateChar());
        assertEquals('a', testClass.getPublicChar());
        assertFalse(testClass.getPrivateBoolean());
        assertTrue(testClass.getPublicBoolean());
        assertNull(testClass.getPrivateString());
        assertEquals("Hi!", testClass.getPublicString());
    }
}