package org.example;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Http {
    private final JsonNode entity;

    public Http() {
        JsonNode entity;
        try {
            entity = getResponse();
        } catch (IOException e) {
            entity = new ObjectMapper().missingNode();
        }
        this.entity = entity;
    }


    public JsonNode getResponse() throws IOException {
        FileWorker fileWorker = new FileWorker();
        return fileWorker.getData();
    }


    public Pair<String, String> cockTail(String command) {
        String r = command.substring(1);
        if(!entity.path(r).isMissingNode()){

            JsonNode data = entity.path(r);
            String name = data.path("Name").asText();
            String base = data.path("Base").asText();
            String strong=data.path("Strong").asText();
            String ingredients = data.path("Ingredients").asText();
            String additionalIngredients = data.path("Additional ingredients").asText();
            String howToMake = data.path("How to make").asText();
            String description = data.path("Description").asText();
            String message =  name + "\nБаза: " + base +"\n"+strong+ "\nИгредиенты: " + ingredients + "\nДополнительные ингредиенты: "
                    + additionalIngredients + "\nКак готовить: " + howToMake + "\nОписание: " + description;
            String photo = data.path("Pic").asText();
            return Pair.of(photo,message);
        }

        return Pair.of("https://img.fotokonkurs.ru/cache/photo_1000w/photos/2019/12/9/2860cd51043e86d521c709e1013bd78d/edbf499dc50d65c2e777da0b77045fdf.jpeg", "Такого коктейля нет");
    }
}
