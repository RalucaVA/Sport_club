package com.example.fxdemos;


import java.io.Serializable;

public class Football extends Sport implements TeamSport, Serializable {
    public Football(String name,String place, String equipment, int id) {
        super(name, place, equipment, id);
    }

    @Override
    public boolean isTeamSport() {
        return false;
    }

}
