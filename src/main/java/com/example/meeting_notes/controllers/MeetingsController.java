package com.example.meeting_notes.controllers;


import com.example.meeting_notes.dao.MeetingTopic;
import com.example.meeting_notes.dao.MeetingsEntity;
import com.example.meeting_notes.dao.MeetingsEntryEntity;
import com.example.meeting_notes.dao.SummarizedMeetingEntry;
import com.example.meeting_notes.dto.AddMeetingDTO;
import com.example.meeting_notes.mail.EMail;
import com.example.meeting_notes.mail.MailSenderService;
import com.example.meeting_notes.model.MeetingTranscriptEntry;
import com.example.meeting_notes.model.ParticipantStats;
import com.example.meeting_notes.services.MeetingEntriesService;
import com.example.meeting_notes.services.MeetingService;
import com.example.meeting_notes.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin(origins = {"https://meeting-notes-frontend.herokuapp.com","http://localhost:3000"})
public class MeetingsController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    TopicService topicService;

    @Autowired
    MeetingEntriesService meetingEntriesService;

    @Autowired
    MailSenderService mailSenderService;

    @PostMapping(value = "/meetings")
    public MeetingsEntity addTranscriptToDb(@RequestBody AddMeetingDTO meetingDTO) throws IOException {
        return meetingService.addNewMeeting(meetingDTO);
    }

    @GetMapping(value = "/meetings/{meetingId}/summarizedEntries")
    public List<SummarizedMeetingEntry> getSummarizedEntriesForMeetingId(@PathVariable Long meetingId){
        System.out.println("CAMEEE HEREEE");
        List<SummarizedMeetingEntry> entryList = meetingEntriesService.getSummarizedMeetingEntriesForMeeting(meetingId);
        return entryList;
    }

    @GetMapping(value = "/meetings/{meetingId}/entries")
    List<MeetingTranscriptEntry> getEntriesForMeetingID(@PathVariable Long meetingId){
        System.out.println(meetingId);
        return meetingEntriesService.getMeetingEntriesForMeeting(meetingId);
    }

    @GetMapping(value = "/meetings")
    public ArrayList<MeetingsEntity> getAllMeetings(){
        return meetingService.getAllMeetings();
    }

    @GetMapping(value = "/meetings/{meetingId}/topics")
    public ArrayList<MeetingTopic> getMeetingTopicsById(@PathVariable Long meetingId) {
        return topicService.getAllTopicsByMeetingId(meetingId);
    }



    @GetMapping(value = "/meetings/{meetingId}/participantStats")
    public List<ParticipantStats> getParticipantStatsForMeeting(@PathVariable Long meetingId){
        return meetingService.getParticipantStatsForMeeting(meetingId);
    }


}
