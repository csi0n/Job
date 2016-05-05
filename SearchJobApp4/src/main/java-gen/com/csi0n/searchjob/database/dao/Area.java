package com.csi0n.searchjob.database.dao;

import com.csi0n.searchjob.database.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "AREA".
 */
public class Area {

    private Long Aid;
    private String name;
    private Long Cid;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient AreaDao myDao;

    private City city;
    private Long city__resolvedKey;


    public Area() {
    }

    public Area(Long Aid) {
        this.Aid = Aid;
    }

    public Area(Long Aid, String name, Long Cid) {
        this.Aid = Aid;
        this.name = name;
        this.Cid = Cid;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAreaDao() : null;
    }

    public Long getAid() {
        return Aid;
    }

    public void setAid(Long Aid) {
        this.Aid = Aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCid() {
        return Cid;
    }

    public void setCid(Long Cid) {
        this.Cid = Cid;
    }

    /** To-one relationship, resolved on first access. */
    public City getCity() {
        Long __key = this.Cid;
        if (city__resolvedKey == null || !city__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CityDao targetDao = daoSession.getCityDao();
            City cityNew = targetDao.load(__key);
            synchronized (this) {
                city = cityNew;
            	city__resolvedKey = __key;
            }
        }
        return city;
    }

    public void setCity(City city) {
        synchronized (this) {
            this.city = city;
            Cid = city == null ? null : city.getCid();
            city__resolvedKey = Cid;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
