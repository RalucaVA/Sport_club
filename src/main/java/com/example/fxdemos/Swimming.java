package com.example.fxdemos;

import java.io.Serializable;

public class Swimming extends Sport implements IndoorSport, Serializable {


    public Swimming(String name, String place, String equipment, int id) {
        super(name, place, equipment, id);
    }

    @Override
    public boolean isIndoorSport() {
        return false;
    }

    @Override
    public boolean isTeamSport() {
        return false;
    }


}
