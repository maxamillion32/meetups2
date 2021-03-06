package com.dusanjovanov.meetups3.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by duca on 29/12/2016.
 */

public class Group implements Serializable{

    private int id;
    private String name;
    private User admin;
    private int memberCount;
    private Meeting nextMeeting;
    private int currentMeetingCount;
    private List<User> members;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getAdmin() {
        return admin;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public Meeting getNextMeeting() {
        return nextMeeting;
    }

    public int getCurrentMeetingCount() {
        return currentMeetingCount;
    }

    public List<User> getMembers() {
        return members;
    }
}
