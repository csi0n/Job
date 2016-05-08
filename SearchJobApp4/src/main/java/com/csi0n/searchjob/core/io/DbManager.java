package com.csi0n.searchjob.core.io;

import android.database.sqlite.SQLiteDatabase;

import com.csi0n.searchjob.app.App;
import com.csi0n.searchjob.core.string.Constants;
import com.csi0n.searchjob.database.dao.Area;
import com.csi0n.searchjob.database.dao.AreaDao;
import com.csi0n.searchjob.database.dao.City;
import com.csi0n.searchjob.database.dao.CityDao;
import com.csi0n.searchjob.database.dao.DaoMaster;
import com.csi0n.searchjob.database.dao.DaoSession;
import com.csi0n.searchjob.database.dao.FuLi;
import com.csi0n.searchjob.database.dao.FuLiDao;
import com.csi0n.searchjob.database.dao.JobType;
import com.csi0n.searchjob.database.dao.JobTypeDao;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by csi0n on 5/6/16.
 */
public class DbManager {
    static DaoSession daoSession;
    static SQLiteDatabase db;
    static DaoMaster.DevOpenHelper helper;
    static DaoMaster daoMaster;

    public static void initDb() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        helper = new DaoMaster.DevOpenHelper(App.getInstance(), Constants.DB_NAME, null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    static DaoSession getDaoSession() {
        if (daoSession != null)
            return daoSession;
        else
            throw new RuntimeException("please init DbManager!");
    }

    static SQLiteDatabase getDb() {
        if (db != null)
            return db;
        else
            throw new RuntimeException("please init DbManager");
    }

    public static void insertArea(Area area) {
        getDaoSession().getAreaDao().insert(area);
    }

    public static void insertCity(City city) {
        getDaoSession().getCityDao().insert(city);
    }

    public static void insertFuLi(FuLi fuLi) {
        getDaoSession().getFuLiDao().insert(fuLi);
    }

    public static void insertJobType(JobType jobType) {
        getDaoSession().insert(jobType);
    }

    public static void dropAllTable() {
        AreaDao.dropTable(getDb(), true);
        CityDao.dropTable(getDb(), true);
        FuLiDao.dropTable(getDb(), true);
        JobTypeDao.dropTable(getDb(), true);
    }
    public static void createAllTable(){
        AreaDao.createTable(getDb(), true);
        CityDao.createTable(getDb(), true);
        FuLiDao.createTable(getDb(), true);
        JobTypeDao.createTable(getDb(), true);
    }
    public static List<City> searchCityByPinYin(String key){
        Query query=getDaoSession().getCityDao().queryBuilder()
                .where(CityDao.Properties.Pinyin.like(key))
                .orderAsc(CityDao.Properties.Cid)
                .build();
        QueryBuilder.LOG_SQL=true;
        QueryBuilder.LOG_VALUES=true;
        return query.list();
    }
    public static List<City> getAllCity(){
      return getDaoSession().getCityDao().queryBuilder().list();
    }
}
