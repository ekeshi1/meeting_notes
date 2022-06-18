package com.example.meeting_notes.controllers;

import com.example.meeting_notes.dao.MeetingTopic;
import com.example.meeting_notes.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = {"https://meeting-notes-frontend.herokuapp.com","http://localhost:3000"})
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping(value = "/topics/distinct")
    public ArrayList<MeetingTopic> getDistinctMeetingTopics(){
        return topicService.getDistinctTopicsForFiltering();
    }

    @GetMapping(value = "/topics/{topicId}/relevant")
    public String voteRelevantTopic(@PathVariable Long topicId){
        System.out.println("Came here");
        topicService.voteRelevantTopic(topicId);
        return "Success";
    }

    @GetMapping(value = "/topics/{topicId}/not_relevant")
    public String voteNotRelevantTopic(@PathVariable Long topicId){
        topicService.voteNotRelevantTopic(topicId);
        return "Success";
    }
}
