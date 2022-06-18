package com.example.meeting_notes.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class MeetingsEntryEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    @Column
    String email;

    @Column
    Long startMS;

    @Column
    Long endMs;

    @Lob
    @Column
    String text;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="meeting_id", nullable=false)
    private MeetingsEntity meeting;

    public MeetingsEntryEntity() {
    }

    public MeetingsEntryEntity(String name, Long startMS, Long endMs, String text) {
        this.name = name;
        this.startMS = startMS;
        this.endMs = endMs;
        this.text = text;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartMS() {
        return startMS;
    }

    public void setStartMS(Long startMS) {
        this.startMS = startMS;
    }

    public Long getEndMs() {
        return endMs;
    }

    public void setEndMs(Long endMs) {
        this.endMs = endMs;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
