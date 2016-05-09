package com.csi0n.searchjob.business.pojo.event.ext;

import com.csi0n.searchjob.business.pojo.event.BaseEvent;
import com.csi0n.searchjob.database.dao.City;

/**
 * Created by csi0n on 5/8/16.
 */
public class ChooseCityEvent extends BaseEvent {
    public City chooseCity;

    public ChooseCityEvent(City chooseCity) {
        this.chooseCity = chooseCity;
    }
}
