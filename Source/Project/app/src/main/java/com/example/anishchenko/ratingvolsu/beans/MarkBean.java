package com.example.anishchenko.ratingvolsu.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by m00n on 11.06.2015.
 */
@DatabaseTable
public class MarkBean {

    @DatabaseField(id = true)
    private String id;

    public MarkBean(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MarkBean() {}
}
