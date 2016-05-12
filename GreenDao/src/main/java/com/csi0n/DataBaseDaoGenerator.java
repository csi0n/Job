package com.csi0n;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class DataBaseDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(2, "com.csi0n.searchjob.database.dao");
        addFuLiModel(schema);
        addJobType(schema);
        addCityAndAreaModel(schema);
        addKeyValue(schema);
        //new DaoGenerator().generateAll(schema,"/Users/csi0n/Desktop/Dev/Job/SearchJobApp4/src/main/java-gen/com/csi0n/searchjob/database/dao");
        new DaoGenerator().generateAll(schema, "G:/Web_Github/Job/SearchJobApp4/src/main/java-gen");
    }

    private static void addFuLiModel(Schema schema) {
        Entity fuli = schema.addEntity("FuLi");
        fuli.addIntProperty("Fid").notNull();
        fuli.addStringProperty("name").notNull();
    }

    private static void addCityAndAreaModel(Schema schema) {
        Entity city = schema.addEntity("City");
        city.addLongProperty("Cid").primaryKey();
        city.addStringProperty("name");
        city.addStringProperty("pinyin");

        Entity area = schema.addEntity("Area");
        area.addLongProperty("Aid").primaryKey();
        area.addStringProperty("name");
        area.addStringProperty("pinyin");

        Property cityId = area.addLongProperty("Cid").getProperty();
        area.addToOne(city, cityId);

        city.addToMany(area, cityId).setName("areas");
    }

    private static void addKeyValue(Schema schema){
        Entity searchKey = schema.addEntity("KeyValue");
        searchKey.addStringProperty("key");
        searchKey.addStringProperty("value");
    }
    private static void addJobType(Schema schema) {
        Entity jobType = schema.addEntity("JobType");
        jobType.addIntProperty("Jid");
        jobType.addStringProperty("name");
    }

}
