package com.company;

import com.company.DAO.*;
import com.company.Service.Service;
import com.company.song.Live;
import com.company.song.Single;
import com.company.song.Song;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public App() throws JAXBException, IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ServiceLayerException {

        new LogFile();

        SongsList<Song> songsList = new SongsList<>();



        Service<Song> songService = new Service<Song>();



        for (Song song : songsList.getList()) {
            song.check();
        }


        Scanner scanner = new Scanner(System.in);
        int menuItem = -1;
        while (true) {
            System.out.println("Наше русское православное меню:");
            System.out.println("1. Вывести список песен");
            System.out.println("2. Вывести по убыванию позиции в чарте");
            System.out.println("3. Вывести среднюю длину композиции");
            System.out.println("4. Узнать длину плейлиста");
            System.out.println("5. Выбрать песни по исполнителю");
            System.out.println("6. Сказать спасибо автору");
            System.out.println("7. Добавить песню");
            System.out.println("8. Отредактирвоать песню");
            System.out.println("9. Удалить песню");


            System.out.println("======================================");
            System.out.println("10. Выйти");
            menuItem = scanner.nextInt();
            if (menuItem == 1) {
                System.out.println("==============Плейлист=================");
                List<Song> songs = songService.getSongList().getList();
                for (int i = 0; i < songs.size(); i++) {
                    System.out.println(songs.get(i).Log());
                }
                System.out.println("=======================================");
            } else if (menuItem == 2) {
                songsList.sortByChart();
            } else if (menuItem == 3) {
                songsList.averageDur();
            } else if (menuItem == 4) {
                songsList.allDur();
            } else if (menuItem == 5) {
                songsList.soutSinger();
            } else if (menuItem == 6) {
                SongsList.thanks();
            } else if (menuItem == 7) {
                System.out.println("Введите данные песни");

                System.out.println("Название:");
                String name = scanner.next();
                System.out.println("Певец:");
                String singer = scanner.next();
                System.out.println("Длительность:");
                int duration = scanner.nextInt();
                System.out.println("Место в чарте:");
                int placeInChart = scanner.nextInt();
                System.out.println("Лейбл:");
                String lable = scanner.next();

                Single song = new Single(name, singer, duration, placeInChart, lable);
                if (song.check()) {
                    songService.addData(song);
                } else {
                    throw new ServiceLayerException("Неправильные данные");
                }


            } else if (menuItem == 8) {
                System.out.println("Введите номер песни");
                int index = scanner.nextInt();
                if(index >= songService.getSongList().getList().size() || index < 0) {
                    throw new ServiceLayerException("Неправильный индекс");
                } else {
                    Single song = (Single) songService.getSongList().getList().get(index);
                    song.Log();
                    System.out.println("Введите номер песни");
                    System.out.println("Название:");
                    String name = scanner.next();
                    System.out.println("Певец:");
                    String singer = scanner.next();
                    System.out.println("Длительность:");
                    int duration = scanner.nextInt();
                    System.out.println("Место в чарте:");
                    int placeInChart = scanner.nextInt();
                    System.out.println("Лейбл:");
                    String lable = scanner.next();

                    Single editSong = new Single(name, singer, duration, placeInChart, lable);
                    if (editSong.check()) {
                        songService.editData(editSong, index);
                    } else {
                        throw new ServiceLayerException("Неправильные данные");
                    }
                }
            } else if (menuItem == 9) {
                System.out.println("Введите номер песни");
                int index = scanner.nextInt();
                if(index >= songService.getSongList().getList().size() || index < 0) {
                    throw new ServiceLayerException("Неправильный индекс");
                } else {
                    songService.eraseData(index);
                }
            }else if (menuItem == 9) {
                break;
            }
        }


    }
}
