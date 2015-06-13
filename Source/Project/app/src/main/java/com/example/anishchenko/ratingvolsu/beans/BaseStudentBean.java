package com.example.anishchenko.ratingvolsu.beans;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.LinkedHashMap;

@DatabaseTable
public class BaseStudentBean {

    @DatabaseField
    public String Name;
    @DatabaseField(id = true)
    public String id;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    public LinkedHashMap<String, String> Predmet;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    public LinkedHashMap<String, String[]> all_marks;
    @DatabaseField
    public String markId;

    public BaseStudentBean(String name, LinkedHashMap<String, String> predmet, String markId, String id) {
        Name = name;
        Predmet = predmet;
        this.markId = markId;
        this.id = id;
    }

    public BaseStudentBean() {
    }
}
