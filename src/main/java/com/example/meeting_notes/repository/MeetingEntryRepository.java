package com.example.meeting_notes.repository;

import com.example.meeting_notes.dao.MeetingsEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingEntryRepository extends JpaRepository<MeetingsEntryEntity, Long> {
}
