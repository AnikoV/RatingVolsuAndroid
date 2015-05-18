package com.example.anishchenko.ratingvolsu.beans;

import java.util.ArrayList;


public abstract class BaseBean<T> {

    public ArrayList<T> result;
    public String error;

    public ArrayList<T> getResult () {
        return result;
    }

    public void setResult (ArrayList<T> result) {
        this.result = result;
    }

    public String getError () {
        return error;
    }

    public void setError (String error) {
        this.error = error;
    }

}
