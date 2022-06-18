package com.example.meeting_notes.controllers;


import com.example.meeting_notes.dao.MeetingsEntity;
import com.example.meeting_notes.dto.AddMeetingDTO;
import com.example.meeting_notes.model.MeetingTranscriptEntry;
import com.example.meeting_notes.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class MeetingsController {

    @Autowired
    MeetingService meetingService;

    @PostMapping(value = "/meetings")
    public MeetingsEntity addTranscriptToDb(@RequestBody AddMeetingDTO meetingDTO) throws IOException {
        return meetingService.addNewMeeting(meetingDTO);
    }
}
