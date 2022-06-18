package com.example.meeting_notes.dao;


import java.util.ArrayList;
import java.util.Set;

import javax.persistence.*;

@Entity
public class MeetingsEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    String projectName;
    String meetingName;
    String rawTranscript;

    @OneToMany(mappedBy="meeting", cascade = CascadeType.ALL)
    private Set<MeetingsEntryEntity> meetingEntries;

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
}
