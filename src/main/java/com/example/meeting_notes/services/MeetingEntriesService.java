package com.example.meeting_notes.services;

import com.example.meeting_notes.dao.SummarizedMeetingEntry;
import com.example.meeting_notes.model.MeetingTranscriptEntry;
import com.example.meeting_notes.repository.MeetingEntryRepository;
import com.example.meeting_notes.repository.SummarizedMeetingEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingEntriesService {
    @Autowired
    MeetingEntryRepository meetingEntryRepository;

    @Autowired
    SummarizedMeetingEntryRepository summarizedMeetingEntryRepository;

    public List<MeetingTranscriptEntry> getMeetingEntriesForMeeting(Long id){
        return meetingEntryRepository.findByMeetingId(id).stream().map(MeetingTranscriptEntry::fromEntity).collect(Collectors.toList());
    }

    public List<SummarizedMeetingEntry> getSummarizedMeetingEntriesForMeeting(Long id){
        return summarizedMeetingEntryRepository.findByEntryMeetingId(id);
    }

    public List<SummarizedMeetingEntry> addSummarizedMeetingEntries(List<SummarizedMeetingEntry> entries){
        return summarizedMeetingEntryRepository.saveAll(entries);
    }

}
