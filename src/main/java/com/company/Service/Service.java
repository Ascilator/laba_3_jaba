package com.company.Service;

import com.company.App;
import com.company.DAO.CsvDao;
import com.company.DAO.Dao;
import com.company.DAO.JsonDao;
import com.company.DAO.XMLDao;
import com.company.SongsList;
import com.company.song.Single;
import com.company.song.Song;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Service<T extends Song> {
    private SongsList<T> songList = new SongsList<>();
    private Dao<Song> daoJson = JsonDao.create("playlist3.json", App.class);
    //private Dao<Song> daoCsv = CsvDao.create("playlist5.csv", App.class);
    //private Dao<Song> daoXml = XMLDao.create("playlist3.xml", Song.class, App.class);
    public Service() throws NoSuchMethodException, IOException, JAXBException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        songList.setList((List<T>) daoJson.read());

        //songsList.setList(daoCsv.read());
        //daoCsv.write(songsList.getList());

        //
        //songsList.setList(daoXml.read());
        //daoXml.write(songsList.getList());
    }
    public Service(SongsList<T> songList) {
        this.songList = songList;
    }
    public SongsList<T> getSongList() {
        return songList;
    }
    public void setSongList(SongsList<T> songList) {
        this.songList = songList;
    }
    public void sortSongs() {
        songList.sortByChart();
    }

    public void addData(Song song) throws InvocationTargetException, IOException, IllegalAccessException, NoSuchMethodException, JAXBException {
        songList.add((T) song);
        daoJson.write((List<Song>) songList.getList());
    }
    public void editData(Song song, int index) throws InvocationTargetException, IOException, IllegalAccessException, NoSuchMethodException, JAXBException {
        songList.getList().get(index).setDuration(song.getDuration());
        songList.getList().get(index).setSinger(song.getSinger());
        songList.getList().get(index).setSongName(song.getSongName());
        songList.getList().get(index).setPlaceInChart(song.getPlaceInChart());

        daoJson.write((List<Song>) songList.getList());

    }
    public void eraseData(int index) throws InvocationTargetException, IOException, IllegalAccessException, NoSuchMethodException, JAXBException {
        songList.eraseSong(index);
        daoJson.write((List<Song>) songList.getList());
    }
}
