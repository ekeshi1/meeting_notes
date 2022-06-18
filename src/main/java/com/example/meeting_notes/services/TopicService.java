package com.example.meeting_notes.services;

import com.example.meeting_notes.dao.MeetingTopic;
import com.example.meeting_notes.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.function.*;
@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    public ArrayList<MeetingTopic> addAllTopics(ArrayList<MeetingTopic> meetingTopicList){
        return (ArrayList<MeetingTopic>) topicRepository.saveAll(meetingTopicList);
    }

    public ArrayList<MeetingTopic> getAllTopicsByMeetingId(Long meetingId){
        return (ArrayList<MeetingTopic>)  topicRepository.findByTopicMeetingId(meetingId);
    }

    public ArrayList<MeetingTopic> getDistinctTopicsForFiltering(){
        List<MeetingTopic> meetingTopics = topicRepository.findAll();
        ArrayList<MeetingTopic> distinctMeetingTopics = (ArrayList<MeetingTopic>) meetingTopics.stream().filter(distinctByKey(topic -> topic.getName())).collect(Collectors.toList());

        return distinctMeetingTopics;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public Set<MeetingTopic> saveAll(Set<MeetingTopic> meetingTopics){
        return new HashSet<>(topicRepository.saveAll(meetingTopics));

    }

    @Transactional
    public void voteRelevantTopic(Long topicId){
        topicRepository.addRelevantVote(topicId);
    }

    @Transactional
    public void voteNotRelevantTopic(Long topicId){
        topicRepository.addNotRelevantVote(topicId);
    }
}
