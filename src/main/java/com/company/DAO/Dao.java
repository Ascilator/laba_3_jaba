package com.company.DAO;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface Dao <T>{
    public List<T> read() throws JAXBException, IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
    public void write(List<T> list) throws JAXBException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
