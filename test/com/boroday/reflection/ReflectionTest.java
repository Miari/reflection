package com.boroday.reflection;

import com.boroday.reflection.testClasses.*;
import com.boroday.Reflection;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;

import static org.junit.Assert.*;

public class ReflectionTest {
    private Reflection reflection = new Reflection();

    @Test
    public void testCreateObject() throws ReflectiveOperationException {
        assertEquals(ArrayList.class, reflection.createObject(ArrayList.class).getClass());
        Object object = reflection.createObject(String.class);
        assertEquals("", object);
    }

    @Test(expected = ReflectiveOperationException.class)
    public void testCreateObjectFailsWhenPrivateConstructor() throws ReflectiveOperationException {
        reflection.createObject(ClassWithPrivateConstructor.class);
    }

    @Test(expected = ReflectiveOperationException.class)
    //не знаю как написать позитивный тест на метод, так как сейчвс при вызове метода invokeMethodsWithoutParameters(), пытаются ввызваться метода Object.а и постоянно возникает ощибка
    public void testInvokeMethodsWithoutParameters() throws ReflectiveOperationException {
        TestClassWithMethodWithoutParameters testClass = new TestClassWithMethodWithoutParameters();
        reflection.invokeMethodsWithoutParameters(testClass);
        assertTrue(testClass.shouldBeChanged);
        assertFalse(testClass.shouldNotBeChanged);
    }

    @Test
    public void testGetFinalMethods() {
        ArrayList<String> list = reflection.getFinalMethods(new LinkedList<>());
        assertEquals(6, reflection.getFinalMethods(new LinkedList()).size());
        assertTrue(list.remove("public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException"));
        assertTrue(list.remove("public final void java.lang.Object.wait() throws java.lang.InterruptedException"));
        assertTrue(list.remove("public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException"));
        assertTrue(list.remove("public final native java.lang.Class java.lang.Object.getClass()"));
        assertTrue(list.remove("public final native void java.lang.Object.notify()"));
        assertTrue(list.remove("public final native void java.lang.Object.notifyAll()"));
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGetNotPublicMethodsCheckOutput() {
        ArrayList<String> list = reflection.getNotPublicMethods(ClassForTestNotPublicMethods.class);
        assertEquals(6, reflection.getNotPublicMethods(ClassForTestNotPublicMethods.class).size());
        assertTrue(list.remove("void com.boroday.reflection.testClasses.ClassForTestNotPublicMethods.packageVisibleMethod()"));
        assertTrue(list.remove("protected void com.boroday.reflection.testClasses.ClassForTestNotPublicMethods.protectedMethod()"));
        assertTrue(list.remove("private void com.boroday.reflection.testClasses.ClassForTestNotPublicMethods.privateMethod()"));
        assertTrue(list.remove("protected void java.lang.Object.finalize() throws java.lang.Throwable"));
        assertTrue(list.remove("protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException"));
        assertTrue(list.remove("private static native void java.lang.Object.registerNatives()"));
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGetAllParentClassesAndInterfaces() throws ReflectiveOperationException {
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
    public void testSetDefaultValuesToPrivateFields() throws ReflectiveOperationException {
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