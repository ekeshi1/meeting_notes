package com.example.meeting_notes.dao;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

import javax.persistence.*;

@Entity
public class MeetingsEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    String projectName;
    String meetingName;
    @Lob
    String rawTranscript;

    @JsonIgnore
    @OneToMany(mappedBy="meeting")
    private Set<MeetingsEntryEntity> meetingEntries;

    @OneToMany(mappedBy = "topicMeeting")
    private Set<MeetingTopic> meetingTopics;

    @OneToMany(mappedBy = "entryMeeting")
    @JsonIgnore
    private Set<SummarizedMeetingEntry> meetingSummarizedEntries;

    private Long durationInMs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getRawTranscript() {
        return rawTranscript;
    }

    public void setRawTranscript(String rawTranscript) {
        this.rawTranscript = rawTranscript;
    }

    public Set<MeetingsEntryEntity> getMeetingEntries() {
        return meetingEntries;
    }

    public void setMeetingEntries(Set<MeetingsEntryEntity> meetingEntries) {
        this.meetingEntries = meetingEntries;
    }

    public Long getDurationInMs() {
        return durationInMs;
    }

    public void setDurationInMs(Long durationInMs) {
        this.durationInMs = durationInMs;
    }

    public Set<MeetingTopic> getMeetingTopics() {
        return meetingTopics;
    }

    public void setMeetingTopics(Set<MeetingTopic> meetingTopics) {
        this.meetingTopics = meetingTopics;
    }

    public Set<SummarizedMeetingEntry> getMeetingSummarizedEntries() {
        return meetingSummarizedEntries;
    }

    public void setMeetingSummarizedEntries(Set<SummarizedMeetingEntry> meetingSummarizedEntries) {
        this.meetingSummarizedEntries = meetingSummarizedEntries;
    }


}
