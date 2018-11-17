package com.example.amanat.rcuproject;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class MyDeserializer<Worldpopulation> implements JsonDeserializer<Worldpopulation> {
    @Override
    public Worldpopulation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement worldPopulation = json.getAsJsonObject().get("worldpopulation");
        return new Gson().fromJson(worldPopulation, typeOfT);
    }
}
