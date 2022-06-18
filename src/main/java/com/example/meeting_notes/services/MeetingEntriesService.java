package com.example.meeting_notes.services;

import com.example.meeting_notes.dao.MeetingsEntryEntity;
import com.example.meeting_notes.model.MeetingTranscriptEntry;
import com.example.meeting_notes.repository.MeetingEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingEntriesService {
    @Autowired
    MeetingEntryRepository meetingEntryRepository;

    public List<MeetingTranscriptEntry> getMeetingEntriesForMeeting(Long id){
        return meetingEntryRepository.findByMeetingId(id).stream().map(MeetingTranscriptEntry::fromEntity).collect(Collectors.toList());
    }
}
