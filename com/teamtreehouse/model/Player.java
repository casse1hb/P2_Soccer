package com.teamtreehouse.model;

import java.io.Serializable;
import java.util.Comparator;


public class Player implements Comparator<Player>, Comparable<Player>, Serializable {
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private int heightInInches;
    private boolean previousExperience;
    private boolean available;
    public Player() { }

    public Player(String firstName, String lastName, int heightInInches, boolean previousExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.heightInInches = heightInInches;
        this.previousExperience = previousExperience;
        available = true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public boolean isPreviousExperience() {
        return previousExperience;
    }

    public boolean getAvailablity() {
        return available;
    }

    public void toggleAvailability() {
        if (available) {
            available = false;
        } else {
            available = true;
        }
    }

    @Override
    public int compareTo(Player other) {
        // We always want to sort by last name then first name
        if (equals(other)) {
            return 0;
        }
        int lastNameComp = lastName.compareTo(other.lastName);
        if (lastNameComp == 0) {
            return firstName.compareTo(other.firstName);
        }
        return lastNameComp;
    }



    @Override
    public int compare(Player o1, Player o2) {
        if (o1.equals(o2)) {
            return 0;
        }
        if (o1.getHeightInInches() > (o2.getHeightInInches())) {
            return 1;
        }
        return -1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (heightInInches != player.heightInInches) return false;
        if (previousExperience != player.previousExperience) return false;
        if (!firstName.equals(player.firstName)) return false;
        return lastName.equals(player.lastName);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + heightInInches;
        result = 31 * result + (previousExperience ? 1 : 0);
        return result;
    }
}
