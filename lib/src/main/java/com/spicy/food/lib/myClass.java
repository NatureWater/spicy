package com.spicy.food.lib;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class myClass {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "data");
        addNews(schema);
        new DaoGenerator().generateAll(schema, "../app/src/main/java/com/spicy/food");
    }

    private static void addNews(Schema schema) {
        Entity news = schema.addEntity("Diary");
        news.addIdProperty();
        news.addStringProperty("title").notNull();
        news.addStringProperty("time").notNull();
        news.addStringProperty("content");
    }
}
