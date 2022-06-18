package com.example.meeting_notes.repository;


import com.example.meeting_notes.dao.SummarizedMeetingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummarizedMeetingEntryRepository extends JpaRepository<SummarizedMeetingEntry, Long> {

    List<SummarizedMeetingEntry> findByEntryMeetingId(Long meetingId);

}
