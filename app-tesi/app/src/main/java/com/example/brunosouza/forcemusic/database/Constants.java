package com.example.brunosouza.forcemusic.database;

public class Constants {
    //COLUMNS
    static final String ROW_ID="id";
    static final String DATA = "data";
    static final String HORA = "hora";

    //DB PROPERTIES
    static final String DB_NAME="b_DB";
    static final String TB_NAME="b_TB";
    static final int DB_VERSION='1';

    //CREATE TABLE
    static final String CREATE_TB="CREATE TABLE b_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "data TEXT NOT NULL, hora TEXT NOT NULL);";

    //DROP_TB
    static final String DROP_TB = "DROP TABLE IF EXISTS " + TB_NAME;
}
