package com.csi0n;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class DataBaseDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.csi0n.searchjob.database.dao");
        addFuLiModel(schema);
        addJobType(schema);
        addCityAndAreaModel(schema);
        new DaoGenerator().generateAll(schema, "G:/Web_Github/Job/SearchJobApp4/src/main/java-gen");
    }

    private static void addFuLiModel(Schema schema) {
        Entity fuli = schema.addEntity("FuLi");
        fuli.addIntProperty("Fid").notNull();
        fuli.addStringProperty("name").notNull();
    }

    private static void addCityAndAreaModel(Schema schema) {
        Entity city = schema.addEntity("City");
        city.addIntProperty("Cid").primaryKey();
        city.addStringProperty("name");

        Entity area = schema.addEntity("Area");
        area.addIntProperty("Aid").primaryKey();
        area.addStringProperty("name");

        Property cityId = area.addIntProperty("Cid").getProperty();
        area.addToOne(city, cityId);

        city.addToMany(area, cityId).setName("areas");
    }

    private static void addJobType(Schema schema) {
        Entity jobType = schema.addEntity("JobType");
        jobType.addIntProperty("Jid");
        jobType.addStringProperty("name");
    }

}
