package com.example.fxdemos;

import java.io.Serializable;

public class Basketball extends Sport implements TeamSport, IndoorSport, Serializable {

    public Basketball(String name, String place, String equipment, int id) {
        super(name, place, equipment, id);
    }

    @Override
    public boolean isIndoorSport() {
        return true;
    }

    @Override
    public boolean isTeamSport() {
        return true;
    }


}
