package com.example.fetchcodingtest;

public class Item {
    private int id;
    private int listId;
    private String name;

    public String getId() {
        return String.valueOf(id);
    }

    public String getListID() {
        return String.valueOf(listId);
    }

    public int getIdAsInt() {
        return id;
    }

    public int getListIdAsInt() {
        return listId;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setListID(int listID) {
        this.listId = listID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
