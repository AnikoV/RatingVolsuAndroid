package com.example.anishchenko.ratingvolsu.beans;

import java.util.HashMap;

public class BaseStudentBean {

    public String Name;
    public HashMap<String, String> Predmet;

    public BaseStudentBean(String name, HashMap<String,String> predmet)
    {
        Name = name;
        Predmet = predmet;
    }

    public BaseStudentBean(){};
}
