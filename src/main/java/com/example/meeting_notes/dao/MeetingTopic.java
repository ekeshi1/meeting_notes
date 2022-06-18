package com.example.meeting_notes.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class MeetingTopic {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    public String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="meeting_id", nullable=false)
    private MeetingsEntity topicMeeting;

    public MeetingTopic() {
    }

    public MeetingTopic(Long id, String name, MeetingsEntity topicMeeting) {
        this.id = id;
        this.name = name;
        this.topicMeeting = topicMeeting;
    }

    public MeetingTopic(String name, MeetingsEntity topicMeeting) {
        this.name = name;
        this.topicMeeting = topicMeeting;
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

    public MeetingsEntity getTopicMeeting() {
        return topicMeeting;
    }

    public void setTopicMeeting(MeetingsEntity topicMeeting) {
        this.topicMeeting = topicMeeting;
    }
}

