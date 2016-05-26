package com.sendhil.haircuttracker.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sendhilgrandhi on 26/05/16.
 */

public class HaircutModel extends RealmObject {

    @PrimaryKey
    private long dateStamp;

    public HaircutModel() {
    }

    public long getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(long dateStamp) {
        this.dateStamp = dateStamp;
    }
}
