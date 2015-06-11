package com.example.anishchenko.ratingvolsu.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class BasePredmetBean implements Serializable {
    private static final long serialVersionUID = 5579593377770992188L;

    @DatabaseField
    public String Name;
    @DatabaseField
    public String Type;
    @DatabaseField(id = true)
    public String id;

    public BasePredmetBean(String name, String type, String id) {
        Name = name;
        Type = type;
        this.id = id;
    }

    public BasePredmetBean() {
    }
}
