package com.example.meeting_notes.repository;

import com.example.meeting_notes.dao.MeetingTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface TopicRepository extends JpaRepository<MeetingTopic, Long> {

    ArrayList<MeetingTopic> findByTopicMeetingId(Long meetingId);
}
