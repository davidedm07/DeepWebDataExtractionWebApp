package model;

import java.util.List;

public class Album {
    private String artist;
    private String title;
    private String description;
    private List<String> tracklist;

    public Album(String artist,String title,String description,List<String> tracklist) {
        this.artist = artist;
        this.description= description;
        this.title = title;
        this.tracklist = tracklist;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTracklist() {
        return tracklist;
    }

    public void setTracklist(List<String> tracklist) {
        this.tracklist = tracklist;
    }
}
