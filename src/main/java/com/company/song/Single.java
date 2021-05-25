package com.company.song;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Single extends Song {
    @JsonProperty("studio")
    private String studio;

    public Single(String songName, String singer, int duration, int placeInChart, String studio) {
        super(songName, singer, duration, placeInChart);
        this.studio = studio;
    }

    public Single()
    {
        super();
    }

    @Override
    public boolean check() {
        super.check();
        return true;
    }

    @XmlElement
    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
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
        return ("Live:\n" + this.singer + " - " + this.songName + "\n\t" + this.duration / 60 + ":" + secs + ", " + pos + "\n Студия звукозаписи: " + this.studio + "\n");

    }

}
