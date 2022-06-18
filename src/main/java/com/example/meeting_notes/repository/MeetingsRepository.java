package com.example.meeting_notes.repository;

import com.example.meeting_notes.dao.MeetingsEntity;
import com.example.meeting_notes.dao.MeetingsEntryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingsRepository extends CrudRepository<MeetingsEntity, Long> {


}
