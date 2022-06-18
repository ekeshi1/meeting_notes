package com.example.meeting_notes.dao;

import javax.persistence.*;

@Entity
public class MeetingsEntryEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    @Column
    Long startMS;

    @Column
    Long endMs;

    @ManyToOne
    @JoinColumn(name="meeting_id", nullable=false)
    private MeetingsEntity meeting;

    public MeetingsEntryEntity() {
    }

    public MeetingsEntryEntity(String name, Long startMS, Long endMs) {
        this.name = name;
        this.startMS = startMS;
        this.endMs = endMs;
    }

    public MeetingsEntity getMeeting() {
        return meeting;
    }

    public void setMeeting(MeetingsEntity meeting) {
        this.meeting = meeting;
    }

    @Override
    public String toString() {
        return "MeetingsEntryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startMS=" + startMS +
                ", endMs=" + endMs +
                ", meeting=" + meeting +
                '}';
    }

    public MeetingsEntryEntity withMeeting(MeetingsEntity meetingsEntity){
        this.meeting = meetingsEntity;
        return this;
    }
}
