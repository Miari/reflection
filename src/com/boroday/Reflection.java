package com.boroday;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;

public class Reflection {

    public Object createObject(Class<?> clazz) throws ReflectiveOperationException {
        try {
            return clazz.getConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e.getMessage());
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new ReflectiveOperationException(e.getMessage());
        }
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

    public void showFinalMethods(Object object) {
        ArrayList<String> strings = getFinalMethods(object);
        for (String string : strings) {
            System.out.println(string);
        }
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
        boolean methodExists = false;
        Method[] inheritedMethods = clazz.getMethods();
        for (Method inheritedMethod : inheritedMethods) {
            if (Modifier.isFinal(inheritedMethod.getModifiers())) {
                for (String string : strings) {
                    if (string.equals(inheritedMethod.toString())) {
                        methodExists = true;
                    }
                }
                if (!methodExists) {
                    strings.add(inheritedMethod.toString());
                }
            }
        }
        return strings;
    }

    public void showNotPublicMethods(Class clazz) {
        ArrayList<String> strings = getNotPublicMethods(clazz);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public ArrayList<String> getNotPublicMethods(Class clazz) {
        ArrayList<String> strings = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (!Modifier.isPublic(method.getModifiers())) {
                strings.add(method.toString());
            }
        }

        Method[] inheritedMethods = clazz.getMethods();
        for (Method inheritedMethod : inheritedMethods) {
            boolean methodExists = false;
            if (!Modifier.isPublic(inheritedMethod.getModifiers())) {
                for (String string : strings) {
                    if (string.equals(inheritedMethod.toString())) {
                        methodExists = true;
                    }
                }
                if (!methodExists) {
                    strings.add(inheritedMethod.toString());
                }
            }
        }
        return strings;
    }

    public void showAllParentClassesAndInterfaces(Class clazz) throws ClassNotFoundException {
        ArrayList<String> arrayList = getAllParentClassesAndInterfaces(clazz);
        for (String string : arrayList) {
            System.out.println(string);
        }
    }

    public ArrayList<String> getAllParentClassesAndInterfaces(Class clazz) throws ClassNotFoundException {
        ArrayList<String> arrayList = new ArrayList<>();

        defineParentClasses(clazz, arrayList); // getAllParentClasses

        Class<?>[] interfaces = clazz.getInterfaces();
        defineInterfaces(interfaces, arrayList);

        return arrayList;
    }

    private ArrayList<String> defineParentClasses(Class clazz, ArrayList<String> arrayList) {
        Class superClass = clazz.getSuperclass();
        if (superClass == null) {
            return arrayList;
        }
        arrayList.add(superClass.toString());
        defineParentClasses(superClass, arrayList);
        return arrayList;
    }

    private ArrayList<String> defineInterfaces(Class[] interfaces, ArrayList<String> arrayList) throws ClassNotFoundException {
        for (Class interfaze : interfaces) {
            if (interfaze == null) {
                return arrayList;
            }
            String type = interfaze.getTypeName();
            boolean interfaceExists = false;
            for (String string : arrayList) {
                if (string.equals("interface " + type)) {
                    interfaceExists = true;
                }
            }
            if (!interfaceExists) {
                arrayList.add("interface " + type);
            }
            Class<?> theClass = Class.forName(type);
            Class<?>[] nextInterfaces = theClass.getInterfaces();
            defineInterfaces(nextInterfaces, arrayList);
        }
        return arrayList;
    }

    public Object setDefaultValues(Object object) throws IllegalAccessException {
        Class clazz = object.getClass();
        ArrayList<Field> fields = new ArrayList<>();

        Field[] declaredFields = clazz.getDeclaredFields();
        Collections.addAll(fields, declaredFields);

        Field[] nonDeclaredFields = clazz.getFields();
        for (Field nonDeclaredField : nonDeclaredFields) {
            boolean fieldExists = false;
            for (Field field : fields) {
                if (nonDeclaredField.equals(field)) {
                    fieldExists = true;
                }
            }
            if (!fieldExists) {
                fields.add(nonDeclaredField);
            }
        }

        for (Field field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                if (byte.class.equals(type) || int.class.equals(type) || short.class.equals(type) || long.class.equals(type)) {
                    field.set(object, 0);
                } else if (double.class.equals(type)) {
                    field.setDouble(object, 0.0);
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
            }
        }
        return object;
    }
}
