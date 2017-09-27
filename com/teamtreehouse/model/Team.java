package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;

public class Team implements Comparable<Team>{
    public String mTeamName;
    public String mCoach;
    public List<Player> mTeamPlayers;

    public Team (String teamName, String coach) {
        mTeamName = teamName;
        mCoach = coach;
        mTeamPlayers = new ArrayList<>();
    }

    @Override
    public int compareTo(Team other) {
        if (equals(other)) {
            return 0;
        }
        return mTeamName.compareTo(other.mTeamName);
    }

}
