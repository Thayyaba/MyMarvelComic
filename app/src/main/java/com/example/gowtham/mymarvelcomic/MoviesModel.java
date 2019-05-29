package com.example.gowtham.mymarvelcomic;

import java.util.List;

public class MoviesModel {
    String comic_id;
    String name;
    String thumbNail;
    String description;
    String modified;
    List<String> comicList;
    List<String> storiesList;
    public MoviesModel(String comic_id, String name, String thumbNail, String description, List<String> comicList, List<String> storiesList, String modifiedDate) {
        this.comic_id=comic_id;
        this.name=name;
        this.thumbNail=thumbNail;
        this.description=description;
        this.modified=modifiedDate;
        this.comicList=comicList;
        this.storiesList=storiesList;
    }

    public String getComic_id() {
        return comic_id;
    }

    public void setComic_id(String comic_id) {
        this.comic_id = comic_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public List<String> getComicList() {
        return comicList;
    }

    public void setComicList(List<String> comicList) {
        this.comicList = comicList;
    }

    public List<String> getStoriesList() {
        return storiesList;
    }

    public void setStoriesList(List<String> storiesList) {
        this.storiesList = storiesList;
    }
}
