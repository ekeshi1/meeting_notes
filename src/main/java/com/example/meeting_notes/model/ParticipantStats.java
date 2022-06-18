package com.example.meeting_notes.model;


public class ParticipantStats {
    String participantName;
    String email;
    Long minutes;
    Integer impactScore;

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public Integer getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(Integer impactScore) {
        this.impactScore = impactScore;
    }

    public ParticipantStats(String participantName, String email, Long minutes, Integer impactScore) {
        this.participantName = participantName;
        this.email = email;
        this.minutes = minutes;
        this.impactScore = impactScore;
    }
}
