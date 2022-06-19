package com.example.meeting_notes.dao;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class SummarizedMeetingEntry {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Column
    String text;


    @ManyToOne
    @JoinColumn(name="meeting_id", nullable=false)
    @JsonIgnore
    private MeetingsEntity entryMeeting;

    public SummarizedMeetingEntry(String text, MeetingsEntity entryMeeting) {
        this.text = text;
        this.entryMeeting = entryMeeting;
    }

    public SummarizedMeetingEntry() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MeetingsEntity getMeeting() {
        return entryMeeting;
    }

    public void setMeeting(MeetingsEntity meeting) {
        this.entryMeeting = meeting;
    }
}
