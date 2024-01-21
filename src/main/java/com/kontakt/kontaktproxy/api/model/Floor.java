package com.kontakt.kontaktproxy.api.model;

import java.util.Map;

public class Floor {
    private int id;
    private int level;
    private String name;
    private Map<String, Object> properties;
    private Map<String, Object> imageXyGeojson;

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

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getImageXyGeojson() {
        return imageXyGeojson;
    }

    public void setImageXyGeojson(Map<String, Object> imageXyGeojson) {
        this.imageXyGeojson = imageXyGeojson;
    }
}
