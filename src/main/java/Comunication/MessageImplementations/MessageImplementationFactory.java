package Comunication.MessageImplementations;

import Comunication.MessageImplementations.IClientMessageImplementation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Message Implementation Factory is used to create instances of classes that implement the IClientMessageImplementation interface or the IServerMessageImplementation interface.
 * It is used to create instances of the message implementations that are used to handle the messages that are received from the client or the server.
 * it gets the implementation class name from the message type and then creates an instance of that class. using reflection.
 *
 */
public class MessageImplementationFactory {
    public static <T> T createMessageImplementationInstance(Class<T> type, String className) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = findClassesImplementingInterface(type);

        for (Class<?> clazz : classes) {
            if (clazz.getSimpleName().equals(className)) {
                try {
                    return (T) clazz.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    throw new RuntimeException("Error creating instance of class", e);
                }
            }
        }
        throw new IllegalArgumentException("Class with name " + className + " not found");
    }


    private static List<Class<?>> findClassesImplementingInterface(Class<?> interfaceClass) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);

        for (String entry : classpathEntries) {
            File file = new File(entry);
            if (file.isDirectory()) {
                List<Class<?>> foundClasses = findClassesInDirectory(file, interfaceClass);
                classes.addAll(foundClasses);
            }
        }

        return classes;
    }

    private static List<Class<?>> findClassesInDirectory(File directory, Class<?> interfaceClass) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClassesInDirectory(file, interfaceClass));
            } else {
                String fileName = file.getName();
                if (fileName.endsWith(".class")) {
                    String className = fileName.substring(0, fileName.length() - 6);
                    Class<?> clazz = Class.forName(className);
                    if (interfaceClass.isAssignableFrom(clazz) && !clazz.isInterface()) {
                        classes.add(clazz);
                    }
                }
            }
        }

        return classes;
    }
}
