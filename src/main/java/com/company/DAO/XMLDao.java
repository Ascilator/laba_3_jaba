package com.company.DAO;

import com.company.SongsList;
import com.company.song.Live;
import com.company.song.Single;
import com.company.song.Song;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static java.util.regex.Pattern.matches;

public class XMLDao<T extends Song> extends AbstractDao<T>{

    private JAXBContext context;
    private Marshaller marshaller;
    Class<T> aClass;

    private XMLDao(String fileName, Class<T> aClass) throws JAXBException
    {
        super(fileName);
        context = JAXBContext.newInstance(aClass, SongsList.class);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }

    public static <T> Dao<T> create(String fileName, Class<T> tClass, Class<?> aClass) throws JAXBException
    {
        return (Dao<T>) Proxy.newProxyInstance(
                aClass.getClassLoader(),
                new Class[]{Dao.class},
                new LoggingProxyHandler<>(new XMLDao(fileName, tClass))
        );
    }

    @Override
    public List<T> read() throws JAXBException{
        return ((SongsList<T>) context.createUnmarshaller().unmarshal(file)).getList();
    }

    @Override
    public void write(List<T> list) throws JAXBException{
        SongsList<T> metaList = new SongsList<>();
        metaList.setList(list);
        marshaller.marshal(metaList, file);
    }
}
