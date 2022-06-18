package com.example.meeting_notes.controllers;


import com.example.meeting_notes.dao.MeetingTopic;
import com.example.meeting_notes.dao.MeetingsEntity;
import com.example.meeting_notes.dto.AddMeetingDTO;
import com.example.meeting_notes.model.MeetingTranscriptEntry;
import com.example.meeting_notes.services.MeetingService;
import com.example.meeting_notes.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class MeetingsController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    TopicService topicService;

    @PostMapping(value = "/meetings")
    public MeetingsEntity addTranscriptToDb(@RequestBody AddMeetingDTO meetingDTO) throws IOException {
        return meetingService.addNewMeeting(meetingDTO);
    }

    @GetMapping(value = "/meetings")
    public ArrayList<MeetingsEntity> getAllMeetings(){
        return meetingService.getAllMeetings();
    }

    @GetMapping(value = "/meetings/{meetingId}/topics")
    public ArrayList<MeetingTopic> getMeetingTopicsById(@PathVariable Long meetingId) {
        return topicService.getAllTopicsByMeetingId(meetingId);
    }

    @GetMapping(value = "/topics/distinct")
    public ArrayList<MeetingTopic> getDistinctMeetingTopics(){
        return topicService.getDistinctTopicsForFiltering();
    }
}
