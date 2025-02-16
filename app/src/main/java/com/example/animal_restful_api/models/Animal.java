package com.example.animal_restful_api.models;

import java.util.Map;

public class Animal {
    private String name;
    private String[] locations;
    private Map<String, String> characteristics;


    public String getName() { return name; }
    public String[] getLocations() { return locations; }

    public Map<String, String> getCharacteristics() { return characteristics; }


}
