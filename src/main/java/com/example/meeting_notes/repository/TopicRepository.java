package com.example.meeting_notes.repository;

import com.example.meeting_notes.dao.MeetingTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface TopicRepository extends JpaRepository<MeetingTopic, Long> {

    ArrayList<MeetingTopic> findByTopicMeetingId(Long meetingId);

    @Modifying
    @Query("UPDATE MeetingTopic t set t.relevantCount = t.relevantCount + 1 WHERE t.id = :id\n")
    void addRelevantVote(Long id);
    @Modifying
    @Query("UPDATE MeetingTopic t set t.notRelevantCount = t.notRelevantCount + 1 WHERE t.id = :id\n")
    void addNotRelevantVote(Long id);

}
