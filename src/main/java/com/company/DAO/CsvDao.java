package com.company.DAO;

import com.company.song.Song;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.regex.Pattern.matches;

public class CsvDao<T extends Song> extends AbstractDao<T>{

    private Scanner scanner;
    private Scanner lineScanner;
    private PrintWriter printWriter;

    private CsvDao(String fileName)
    {
        super(fileName);
    }

    public static <T> Dao<T> create(String fileName, Class<?> aClass)
    {
        return (Dao<T>) Proxy.newProxyInstance(
                aClass.getClassLoader(),
                new Class[]{Dao.class},
                new LoggingProxyHandler<>(new CsvDao(fileName))
        );
    }

    private Object readNativeObject(Class<?> tClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        return tClass.getConstructor(String.class).newInstance(lineScanner.next());
    }

    private Object readObject(Object object) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {

        Class<?> tClass = Class.forName(lineScanner.next());
        if (tClass.getPackage().equals(Integer.class.getPackage())){
            return readNativeObject(tClass);
        }
        if (object == null){
            object = tClass.getConstructor().newInstance();
        }
        if (!tClass.getSuperclass().equals(Object.class)){
            readObject(object);
        }
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields){
            String fieldName = field.getName();
            StringBuilder setterName = new StringBuilder().append("set")
                    .append(Character.toUpperCase(fieldName.charAt(0)))
                    .append(fieldName.substring(1));
            Method setter = tClass.getMethod(setterName.toString(), field.getType());
            setter.invoke(object, readObject(null));
        }
        return object;

    }

    @Override
    public List<T> read() throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        List<T> list = new ArrayList<>();
        scanner = new Scanner(file);
        while (scanner.hasNextLine()){
            lineScanner = new Scanner(scanner.nextLine()).useDelimiter(",");
            list.add((T) readObject(null));
        }
        return list;
    }

    private void writeNativeObject(Object object, Class<?> tClass)
    {
        printWriter.print(tClass.getCanonicalName());
        printWriter.print(",");
        printWriter.print(object);
    }

    private void writeObject(Object object, Class<?> tClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        if (tClass.getPackage().equals(Integer.class.getPackage())){
            writeNativeObject(object, tClass);
            return;
        }
        printWriter.print(tClass.getCanonicalName());
        if (!tClass.getSuperclass().equals(Object.class)){
            printWriter.print(",");
            writeObject(object, tClass.getSuperclass());
        }
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields){
            String fieldName = field.getName();
            printWriter.print(",");
            StringBuilder getterName = new StringBuilder();
            if (field.getType().equals(Boolean.class) || field.getType().getName().equals("boolean")){
                getterName.append("is");
            }
            else {
                getterName.append("get");
            }
            getterName.append(Character.toUpperCase(fieldName.charAt(0)))
                    .append(fieldName.substring(1));
            Method getter = tClass.getMethod(getterName.toString());
            Object getterResult = getter.invoke(object);
            writeObject(getterResult, getterResult.getClass());
        }
    }

    @Override
    public void write(List<T> list) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        printWriter = new PrintWriter(file);
        for (T object : list){
            writeObject(object, object.getClass());
            printWriter.println();
        }
        printWriter.close();
    }
}
