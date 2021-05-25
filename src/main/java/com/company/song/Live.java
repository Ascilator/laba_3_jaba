package com.company.song;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static java.util.regex.Pattern.matches;

@XmlRootElement
public class Live extends Song {
    @JsonProperty("date")
    private String date;
    @JsonProperty("place")
    private String place;

    public Live()
    {
        super();
    }

    public Live(String songName, String singer, int duration, int placeInChart, String date, String place) {
        super(songName, singer, duration, placeInChart);
        this.date = date;
        this.place = place;
    }

    @Override
    public boolean check() {
        if(matches( "([1-9]|[1-3][0-9])\\s[а-я]+\\s([0-1][0-9][0-9][0-9]|[2][0][0-2][0-9])\\s[а-я]+", date) == false) {
            System.out.println("Год не прошло валидацию");
            return false;
        }
        super.check();
        return true;
    }

    @XmlElement()
    public String getDate() {
        return date;
    }

    @XmlElement()
    public String getPlace() {
        return place;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String Log() {
        String secs;
        if (this.duration % 60 < 10) {
            secs = "0" + this.duration % 60;
        } else {
            secs = String.valueOf(this.duration % 60);
        }
        String pos;
        if (this.placeInChart == 1000) {
            pos = "не входит в топ.";
        } else {
            pos = "place in chart #" + String.valueOf(this.placeInChart);
        }
        return ("Single:\n" + this.singer + " - " + this.songName + "\n\t" + this.duration / 60 + ":" + secs + ", " + pos + "\nДата выхода:"+ this.date +"\n Место: " + this.place + "\n");

    }
}
