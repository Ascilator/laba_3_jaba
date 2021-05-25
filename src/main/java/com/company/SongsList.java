package com.company;

import com.company.song.Song;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
public class SongsList<T extends Song> {

    private List<T> list;

    public SongsList()
    {
        list = new ArrayList<>();
    }

    @XmlElement
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    public void eraseSong(int index) {
        this.list.remove(index);
    }
    public void printSongs() {
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).Log_2());
        }
    }

    public void sortByChart() {
        Collections.sort(list, new Comparator<Song>() {
            @Override
            public int compare(Song s1, Song s2) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                if(s1.getPlaceInChart() > s2.getPlaceInChart()) {
                    return 1;
                }
                if(s1.getPlaceInChart() < s2.getPlaceInChart()) {
                    return -1;
                }
                if(s1.getPlaceInChart() == s2.getPlaceInChart()) {
                    if(s1.getSinger().compareTo(s2.getSinger()) > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                return 0;
            }
        });


        printSongs();
    }


    public void add(T song) {
        list.add(song);
    }
    public int averageDur() {
        int summ = 0;
        for(int i = 0; i < list.size(); i++) {
            summ += list.get(i).getDuration();
        }
        String secs;
        if (summ/list.size() % 60 < 10) {
            secs = "0" + summ/list.size() % 60;
        } else {
            secs = String.valueOf(summ/list.size() % 60);
        }
        System.out.println(summ/list.size()/60 + ":" + secs);
        return summ/list.size()/60;
    }

    public int allDur() {
        int summ = 0;
        for(int i = 0; i < list.size(); i++) {
            summ += list.get(i).getDuration();
        }
        String secs;
        if (summ % 60 < 10) {
            secs = "0" + summ % 60;
        } else {
            secs = String.valueOf(summ % 60);
        }
        System.out.println(summ/60 + ":" +secs);
        return (summ/60);
    }

    public void soutSinger() {
        System.out.println("Пожалуйста введите имя исполнителя");
        Scanner scanner = new Scanner(System.in);
        String singer = scanner.nextLine();
        boolean yosNo = false;
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i).getSinger().compareTo(singer) == 0) {
                yosNo = true;
                System.out.println(list.get(i).Log());
            }
        }
        if (!yosNo) {
            System.out.println("По вашему запросу ничего не найдено :( \n");
        }
    }

    public static void thanks() {
        System.out.println("(⌒‿⌒)");
    }

}
