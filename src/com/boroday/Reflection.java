package com.boroday;

import java.lang.reflect.*;
import java.util.*;

public class Reflection {

    public Object createObject(Class<?> clazz) throws ReflectiveOperationException {
        try {
            return clazz.getConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public HashSet<Method> invokeMethodsWithoutParameters(Object object) throws ReflectiveOperationException {
        Class<?> clazz = object.getClass();
        HashSet<Method> listOfMethods = new HashSet<>();
        Collections.addAll(listOfMethods, clazz.getDeclaredMethods());

        Class<?> parentClass = clazz.getSuperclass();
        if (parentClass != null) {
            listOfMethods.addAll(invokeMethodsWithoutParameters(createObject(parentClass)));
        }

        for (Method method : listOfMethods) {
            if (method.getParameterCount() == 0) {
                try {
                    method.setAccessible(true);
                    method.invoke(object);
                    method.setAccessible(false);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new ReflectiveOperationException(e.getCause());
                }
            }
        }
        return listOfMethods;
    }

    public void printFinalMethods(Object object) {
        ArrayList<String> strings = getFinalMethods(object);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public ArrayList<String> getFinalMethods(Object object) {
        Class<?> clazz = object.getClass();
        return getFinalMethodsOfClass(clazz);
    }

    private ArrayList<String> getFinalMethodsOfClass(Class<?> clazz) {
        ArrayList<String> methodNames = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (Modifier.isFinal(method.getModifiers())) {
                methodNames.add(method.toString());
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            methodNames.addAll(getFinalMethodsOfClass(superClass));
        }
        return methodNames;
    }

    public void printNotPublicMethods(Class<?> clazz) {
        ArrayList<String> strings = getNotPublicMethods(clazz);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public ArrayList<String> getNotPublicMethods(Class<?> clazz) {
        ArrayList<String> methodNames = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (!Modifier.isPublic(method.getModifiers())) {
                methodNames.add(method.toString());
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            methodNames.addAll(getNotPublicMethods(superClass));
        }
        return methodNames;
    }

    public void printAllParentClassesAndInterfaces(Class<?> clazz) throws ReflectiveOperationException {
        try {
            ArrayList<Class> listOfInterfaces = getAllParentClassesAndInterfaces(clazz);
            for (Class classToPrint : listOfInterfaces) {
                System.out.println(classToPrint.getName());
            }
        } catch (ReflectiveOperationException e) {
            throw new ReflectiveOperationException(e.getCause());
        }

    }

    public ArrayList<Class> getAllParentClassesAndInterfaces(Class<?> clazz) throws ReflectiveOperationException {
        HashSet<Class> listOfClasses = new HashSet<>();
        getParentClasses(clazz, listOfClasses);

        Class<?>[] interfaces = clazz.getInterfaces();
        try {
            getInterfaces(interfaces, listOfClasses);
        } catch (ReflectiveOperationException e) {
            throw new ReflectiveOperationException(e.getCause());
        }
        return new ArrayList<>(listOfClasses);
    }

    private void getParentClasses(Class<?> clazz, HashSet<Class> listOfClasses) {
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            listOfClasses.add(superClass);
            getParentClasses(superClass, listOfClasses);
        }
    }

    private void getInterfaces(Class<?>[] interfaces, HashSet<Class> listOfClasses) throws ReflectiveOperationException {
        for (Class<?> interfaze : interfaces) {
            if (interfaze != null) {
                String type = interfaze.getTypeName();
                try {
                    Class<?> theClass = Class.forName(type);
                    listOfClasses.add(theClass);
                    Class<?>[] nextInterfaces = theClass.getInterfaces();
                    getInterfaces(nextInterfaces, listOfClasses);
                } catch (ClassNotFoundException e) {
                    throw new ReflectiveOperationException(e.getCause());
                }
            }
        }
    }

    public void setDefaultValuesToPrivateFields(Object object) throws ReflectiveOperationException {
        Class<?> clazz = object.getClass();
        ArrayList<Field> fieldsOfClass = getAllPrivateFields(clazz);
        for (Field field : fieldsOfClass) {
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                try {
                    if (byte.class.equals(type) || int.class.equals(type) || short.class.equals(type) || long.class.equals(type)) {
                        field.set(object, (byte) 0);
                    } else if (float.class.equals(type) || double.class.equals(type)) {
                        field.setFloat(object, 0.0f);
                    } else if (boolean.class.equals(type)) {
                        field.set(object, false);
                    } else if (char.class.equals(type)) {
                        field.set(object, '\u0000');
                    } else {
                        field.set(object, null);
                    }
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    throw new ReflectiveOperationException(e.getCause());
                }
            }
        }
    }

    private ArrayList<Field> getAllPrivateFields(Class clazz) {
        ArrayList<Field> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        Collections.addAll(list, fields);

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            list.addAll(getAllPrivateFields(superClass));
        }
        return list;
    }

}
