package com.example.meeting_notes.services;

import com.example.meeting_notes.dao.MeetingTopic;
import com.example.meeting_notes.dao.MeetingsEntity;
import com.example.meeting_notes.dao.MeetingsEntryEntity;
import com.example.meeting_notes.dao.SummarizedMeetingEntry;
import com.example.meeting_notes.dto.AddMeetingDTO;
import com.example.meeting_notes.mail.EMail;
import com.example.meeting_notes.mail.MailSenderService;
import com.example.meeting_notes.model.MeetingTranscriptEntry;
import com.example.meeting_notes.model.ParticipantStats;
import com.example.meeting_notes.repository.MeetingEntryRepository;
import com.example.meeting_notes.repository.MeetingsRepository;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;

@Service
public class MeetingService {

    @Autowired
    MeetingsRepository meetingsRepository;

    @Autowired
    MeetingEntryRepository meetingEntryRepository;
    String fileName = "src/ITAC_Transscript.docx";
    @Autowired
    MeetingEntriesService meetingEntriesService;
    @Autowired
    TopicService topicService;

    @Autowired
    MailSenderService mailSenderService;

    public static HashMap<String, String> userEmails;

    static {
        userEmails = new HashMap<>();
        userEmails.put("Erald Keshi","eraldo.keshi@hotmail.com");
        userEmails.put("Sergi M. Rodriguez", "sergimartinezrodriguez@gmail.com");
        userEmails.put("Steve Esposito", "stevespositos@gmail.com");
    }
    @Transactional
    public MeetingsEntity addNewMeeting(AddMeetingDTO meetingDTO) throws IOException {
        ArrayList<MeetingTranscriptEntry> transcriptEntryList;


            String[] textLines = meetingDTO.getRawText().split("\\r?\\n");
            transcriptEntryList = new ArrayList<>();
            for(int i=0;i<textLines.length;i+=3){
                String durationString = textLines[i];
                String personName = textLines[i+1];
                String personPhrases = textLines[i+2];
                System.out.println(personPhrases);
                MeetingTranscriptEntry meetingTranscriptEntry = new MeetingTranscriptEntry(personName,durationString,personPhrases);
                transcriptEntryList.add(meetingTranscriptEntry);
            }


        MeetingsEntity meetingsEntity = new MeetingsEntity();

        ArrayList<MeetingsEntryEntity> meetingsEntryEntityList = (ArrayList<MeetingsEntryEntity>) transcriptEntryList.stream().map(x -> x.toEntity()).map(x->x.withMeeting(meetingsEntity)).collect(Collectors.toList());
        for(int i=0;i<meetingsEntryEntityList.size(); i++){
            MeetingsEntryEntity entry = meetingsEntryEntityList.get(i);
            if(userEmails.containsKey(entry.getName())){
                entry.setEmail(userEmails.get(entry.getName()));
            }
        }

        Set<MeetingsEntryEntity> meetingsEntryEntitySet = new LinkedHashSet(meetingsEntryEntityList);

        meetingsEntity.setMeetingName(meetingDTO.getMeetingName());
        meetingsEntity.setProjectName(meetingDTO.getProjectName());
        meetingsEntity.setDurationInMs(transcriptEntryList.get(transcriptEntryList.size()-1).getEndMs());


        List<String> meetingTopicsStr = dummyGetTopics();

        Set<MeetingTopic> meetingTopics = meetingTopicsStr.stream().map( topicName -> new MeetingTopic(topicName, meetingsEntity)).collect(Collectors.toSet());
        meetingsEntity.setRawTranscript(meetingDTO.getRawText());
        meetingsRepository.save(meetingsEntity);
        meetingEntryRepository.saveAll(meetingsEntryEntitySet);
        meetingEntriesService.addSummarizedMeetingEntries(Arrays.asList(new SummarizedMeetingEntry("adasdasdasdas",meetingsEntity)));
        Set<MeetingTopic> storedTopics = topicService.saveAll(meetingTopics);
        meetingsEntity.setMeetingTopics(storedTopics);
        sendEmailsToMeetingParticipants(meetingsEntity.getMeetingName(),meetingTopics, meetingsEntryEntityList);


        return meetingsEntity;

    }

    public void sendEmailsToMeetingParticipants(String meetingName,Set<MeetingTopic> topics, List<MeetingsEntryEntity> meetingTranscriptEntries){

        Map<String,String> distinctParticipantsWithEmail = meetingTranscriptEntries.stream().collect(Collectors.toMap(entry-> entry.getEmail(), entry-> entry.getName(), (mail1, mail2) -> mail1));
        for(Map.Entry<String,String> participantWithEmail: distinctParticipantsWithEmail.entrySet()){
            if(Objects.isNull(participantWithEmail.getKey()))
                continue;

            EMail eMail = new EMail();
            eMail.setFrom("erald97@ut.ee");
            eMail.setTo(participantWithEmail.getKey());
            eMail.setSubject("New meeting notes for meeting: "+ meetingName);
            Map model = new HashMap<String,Object>();
            model.put("user", participantWithEmail.getValue());
            model.put("topics", topics);
            final String baseUrl =
                    ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            model.put("baseUrl", baseUrl);
            eMail.setModel(model);

            mailSenderService.sendMeetingSummaryEmail(eMail);

        }
    }




    public List<String> dummyGetTopics(){
        List<String> dummyTopicsToChoose = Arrays.asList("architecture","Server Ordering","Cross team dependency followup","Api agreement", "Leftovers","Database query implementation");
        Collections.shuffle(dummyTopicsToChoose);
        return dummyTopicsToChoose.subList(0,4);
    }

    public String meetingAsCsv() throws IOException {
        String transcriptFile = "ITAC_Transscript.docx";

        try (XWPFDocument doc = new XWPFDocument(
                Files.newInputStream(Paths.get(transcriptFile)))) {

            XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);


            String docText = xwpfWordExtractor.getText();
            String[] textLines = docText.split("\\r?\\n");
            ArrayList<MeetingTranscriptEntry> transcriptEntryList = new ArrayList<>();
            for(int i=0;i<textLines.length;i+=3){
                String durationString = textLines[i];
                String personName = textLines[i+1];
                String personPhrases = textLines[i+2];
                MeetingTranscriptEntry meetingTranscriptEntry = new MeetingTranscriptEntry(personName,personPhrases);
                transcriptEntryList.add(meetingTranscriptEntry);
            }

            String recordAsCsv = transcriptEntryList.stream()
                    .map(MeetingTranscriptEntry::toCsvRow)
                    .collect(Collectors.joining(System.getProperty("line.separator")));
            System.out.println(transcriptEntryList.size());

            Files.write( Paths.get("transcript.csv"), recordAsCsv.getBytes());
            return recordAsCsv;
        }
    }

    public ArrayList<MeetingsEntity> getAllMeetings() {
        return (ArrayList<MeetingsEntity>) meetingsRepository.findAll();
    }

    public List<ParticipantStats> getParticipantStatsForMeeting(Long id){
        List<MeetingTranscriptEntry> meetingEntriesForMeeting = meetingEntriesService.getMeetingEntriesForMeeting(id);

        List<ParticipantStats> participantStats = meetingEntriesForMeeting.stream()
                .collect(Collectors.groupingBy(r -> r.getName()))
                .entrySet()
                .stream()
                .map(x-> toStats(x.getKey(), x.getValue()))
                .collect(Collectors.toList());

        return participantStats;

    }

    private <R> ParticipantStats toStats(String name, List<MeetingTranscriptEntry> meetingTranscriptEntries) {
        long totalMsSpoken=0;

        for(MeetingTranscriptEntry entry: meetingTranscriptEntries){
            totalMsSpoken+=entry.getEndMs()-entry.getStartMs();
        }
        ParticipantStats stats = new ParticipantStats(name,null, totalMsSpoken, 0);
        return stats;
    }
}
