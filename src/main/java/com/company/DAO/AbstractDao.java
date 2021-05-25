package com.company.DAO;

import com.company.song.Song;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractDao<T extends Song> implements Dao<T>{

    protected File file;

    public AbstractDao(String fileName)
    {
        this.file = new File(fileName);
    }

    public abstract List<T> read() throws JAXBException, IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
    public abstract void write(List<T> list) throws JAXBException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
