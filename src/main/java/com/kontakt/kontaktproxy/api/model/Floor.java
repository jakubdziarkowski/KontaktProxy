package com.kontakt.kontaktproxy.api.model;

public class Floor {
    private int id;
    private int level;
    private String name;
    private Properties properties;
    private ImageXyGeojson imageXyGeojson;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public ImageXyGeojson getImageXyGeojson() {
        return imageXyGeojson;
    }

    public void setImageXyGeojson(ImageXyGeojson imageXyGeojson) {
        this.imageXyGeojson = imageXyGeojson;
    }
}
