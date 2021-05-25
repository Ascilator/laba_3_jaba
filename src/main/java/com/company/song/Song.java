package com.company.song;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import static java.util.regex.Pattern.matches;

@XmlRootElement
@XmlSeeAlso({Live.class, Single.class})
public abstract class Song {
    @JsonProperty("songName")
    protected String songName;
    @JsonProperty("singer")
    protected String singer;
    @JsonProperty("duration")
    protected int duration;
    @JsonProperty("placeInChart")
    protected int placeInChart;

    public Song(){};

    public Song(String songName, String singer, int duration, int placeInChart) {
        this.songName = songName;
        this.singer = singer;
        this.duration = duration;
        this.placeInChart = placeInChart;
    }
    public abstract String Log();
    public String Log_2() {
        String pos;
        if (this.placeInChart == 1000) {
            pos = "не входит в топ.";
        } else {
            pos = "place in chart #" + String.valueOf(this.placeInChart);
        }
        return (this.singer + " - " + this.songName + "\t--\t" + " " + pos);
    }

    public boolean check()
    {
        if(matches( "^[0-9]+$", Integer.toString(duration)) == false) {
            System.out.println("Длительность не прошла валидацию");
            return false;
        }
        if(matches( "^[0-9]+$", Integer.toString(placeInChart)) == false) {
            System.out.println("Место в чате не прошло валидацию");
            return false;
        }
        return true;
    }

    @XmlElement
    public String getSongName() {
        return songName;
    }

    @XmlElement
    public String getSinger() {
        return singer;
    }

    @XmlElement
    public int getDuration() {
        return duration;
    }

    @XmlElement
    public int getPlaceInChart() {
        return placeInChart;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPlaceInChart(int placeInChart) {
        this.placeInChart = placeInChart;
    }
}
