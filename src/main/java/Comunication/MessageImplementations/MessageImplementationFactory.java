package Comunication.MessageImplementations;

import Comunication.MessageImplementations.IClientMessageImplementation;
import Comunication.MessageImplementations.Server.PublicKeySender;

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

    public static <T> T createMessageImplementationInstance(Class<T> type, String className) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        var c= PublicKeySender.class; //Main.getClassBySimpleName(className);
        var obj = (T) c.newInstance();
        return obj;
    }


    private static List<Class<?>> findClassesImplementingInterface(Class<?> interfaceClass) {
        List<Class<?>> classes = new ArrayList<>();
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);
        System.out.println("classpathEntries: " + classpathEntries.length);
        for (String entry : classpathEntries) {
            System.out.println("entry: " + entry);
            File file = new File(entry);
            if (file.isDirectory()) {
                List<Class<?>> foundClasses = findClassesInDirectory(file, interfaceClass);
                classes.addAll(foundClasses);
            }
        }

        return classes;
    }

    private static List<Class<?>> findClassesInDirectory(File directory, Class<?> interfaceClass)  {
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
                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName(className);
                    } catch (ClassNotFoundException e) {

                    }
                    if (interfaceClass.isAssignableFrom(clazz) && !clazz.isInterface()) {
                        classes.add(clazz);
                    }
                }
            }
        }

        return classes;
    }
}
