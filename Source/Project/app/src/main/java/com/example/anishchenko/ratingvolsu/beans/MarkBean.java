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
    @DatabaseField
    private String titile;
    @DatabaseField
    private String subtitle;
    @DatabaseField
    private String selectedFacultet;
    @DatabaseField
    private String selectedGroup;
    @DatabaseField
    private String selectedSemestr;
    @DatabaseField
    private String selectedStudent;
    @DatabaseField
    private String savedStudent;
    @DatabaseField
    private boolean isFavorite;

    public MarkBean(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MarkBean() {
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSelectedFacultet() {
        return selectedFacultet;
    }

    public void setSelectedFacultet(String selectedFacultet) {
        this.selectedFacultet = selectedFacultet;
    }

    public String getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(String selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public String getSelectedSemestr() {
        return selectedSemestr;
    }

    public void setSelectedSemestr(String selectedSemestr) {
        this.selectedSemestr = selectedSemestr;
    }

    public String getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(String selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public String getSavedStudent() {
        return savedStudent;
    }

    public void setSavedStudent(String savedStudent) {
        this.savedStudent = savedStudent;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
