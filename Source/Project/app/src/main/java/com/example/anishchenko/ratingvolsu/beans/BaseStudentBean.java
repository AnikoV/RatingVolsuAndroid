package com.example.anishchenko.ratingvolsu.beans;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.HashMap;

@DatabaseTable
public class BaseStudentBean {

    @DatabaseField
    public String Name;
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    public HashMap<String, String> Predmet;
    @DatabaseField
    public String markId;

    public BaseStudentBean(String name, HashMap<String, String> predmet, String markId) {
        Name = name;
        Predmet = predmet;
        this.markId = markId;
    }

    public BaseStudentBean() {
    }
}
