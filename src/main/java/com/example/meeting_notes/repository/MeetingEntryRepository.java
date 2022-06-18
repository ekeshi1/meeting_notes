package com.example.meeting_notes.repository;

import com.example.meeting_notes.dao.MeetingTopic;
import com.example.meeting_notes.dao.MeetingsEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingEntryRepository extends JpaRepository<MeetingsEntryEntity, Long> {
    List<MeetingsEntryEntity> findByMeetingId(Long meetingId);

}
