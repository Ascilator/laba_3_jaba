package com.company.DAO;

import com.company.song.Live;
import com.company.song.Single;
import com.company.song.Song;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static java.util.regex.Pattern.matches;

public class JsonDao<T extends Song> extends AbstractDao<T>{

    private ObjectMapper mapper;

    private JsonDao(String fileName)
    {
        super(fileName);
        mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
    }

    public static <T> Dao<T> create(String fileName, Class<?> aClass)
    {
        return (Dao<T>) Proxy.newProxyInstance(
                aClass.getClassLoader(),
                new Class[]{Dao.class},
                new LoggingProxyHandler<>(new JsonDao(fileName))
        );
    }

    @Override
    public List<T> read() throws IOException{
        return mapper.readValue(file, new TypeReference<ArrayList<T>>() {});
    }

    @Override
    public void write(List<T> list) throws IOException{
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
    }
}
