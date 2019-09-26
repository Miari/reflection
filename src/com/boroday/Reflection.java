package com.boroday;

import java.lang.reflect.*;
import java.util.ArrayList;

public class Reflection<T> {

    public T createObject(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return clazz.getConstructor().newInstance();
    }

    public int invokeMethods(Object object) throws InvocationTargetException, IllegalAccessException {
        Class clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        int count = 0;
        for (Method method : methods) {
            if (method.getParameterCount() == 0) {
                method.setAccessible(true);
                method.invoke(object);
                count++;
                method.setAccessible(false);
            }
        }
        return count;
    }

    public ArrayList<String> getFinalMethods(Object object) {
        ArrayList<String> strings = new ArrayList<>();
        Class clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (Modifier.isFinal(method.getModifiers())) {
                strings.add(method.toString());
            }
        }
        return strings;
    }

    public ArrayList<String> getNotPublicMethods(Class clazz) {
        ArrayList<String> strings = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (!Modifier.isPublic(method.getModifiers())) {
                strings.add(method.toString());
            }
        }
        return strings;
    }

    public ArrayList<Class> getAllAncestorsAndInterfaces(Class clazz) {
        ArrayList<Class> arrayList = new ArrayList<>();
        defineAncestors(clazz, arrayList);
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class clazzes : interfaces) {
            arrayList.add(clazzes);
        }
        return arrayList;
    }

    private ArrayList<Class> defineAncestors(Class clazz, ArrayList arrayList) {
        Class newClazz = clazz.getSuperclass();
        arrayList.add(newClazz);
        if (newClazz.getSimpleName().equals("Object")) {
            return arrayList;
        } else {
            defineAncestors(newClazz, arrayList);
        }
        return arrayList;
    }

    public Object setNullValues(Object object) throws IllegalAccessException {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
                Type type = field.getType();
                if (type.getTypeName().equals("int")) { // наверняка можно сделать проще, но незнаю как
                    field.setInt(object, 0);
                } else if (type.getTypeName().equals("short")) {
                    field.setShort(object, (short) 0);
                } else if (type.getTypeName().equals("long")) {
                    field.setLong(object, 0);
                } else if (type.getTypeName().equals("double")) {
                    field.setDouble(object, 0.0);
                } else if (type.getTypeName().equals("float")) {
                    field.setFloat(object, 0.0f);
                } else if (type.getTypeName().equals("boolean")) {
                    field.setBoolean(object, false);
                } else if (type.getTypeName().equals("byte")) {
                    field.setByte(object, (byte) 0);
                } else if (type.getTypeName().equals("char")) {
                    field.setChar(object, '\u0000');
                } else {
                    field.set(object, null);
                }
                field.setAccessible(false);
            }
        }
        return object;
    }
}
