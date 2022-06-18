package com.example.meeting_notes.services;

import com.example.meeting_notes.dao.MeetingsEntity;
import com.example.meeting_notes.dao.MeetingsEntryEntity;
import com.example.meeting_notes.dto.AddMeetingDTO;
import com.example.meeting_notes.model.MeetingTranscriptEntry;
import com.example.meeting_notes.repository.MeetingEntryRepository;
import com.example.meeting_notes.repository.MeetingsRepository;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.Set;
@Service
public class MeetingService {

    @Autowired
    MeetingsRepository meetingsRepository;

    @Autowired
    MeetingEntryRepository meetingEntryRepository;
    String fileName = "src/ITAC_Transscript.docx";
    @Transactional
    public MeetingsEntity addNewMeeting(AddMeetingDTO meetingDTO) throws IOException {
        ArrayList<MeetingTranscriptEntry> transcriptEntryList;


            String[] textLines = meetingDTO.getRawText().split("\\r?\\n");
            transcriptEntryList = new ArrayList<>();
            for(int i=0;i<textLines.length;i+=3){
                String durationString = textLines[i];
                String personName = textLines[i+1];
                String personPhrases = textLines[i+2];
                MeetingTranscriptEntry meetingTranscriptEntry = new MeetingTranscriptEntry(personName,durationString,personPhrases);
                transcriptEntryList.add(meetingTranscriptEntry);
            }


        MeetingsEntity meetingsEntity = new MeetingsEntity();

        ArrayList<MeetingsEntryEntity> meetingsEntryEntityList = (ArrayList<MeetingsEntryEntity>) transcriptEntryList.stream().map(x -> x.toEntity()).map(x->x.withMeeting(meetingsEntity)).collect(Collectors.toList());
        Set<MeetingsEntryEntity> meetingsEntryEntitySet = new LinkedHashSet(meetingsEntryEntityList);
        System.out.println(meetingsEntryEntitySet);

        meetingsEntity.setMeetingName(meetingDTO.getMeetingName());
        meetingsEntity.setProjectName(meetingDTO.getProjectName());
        meetingsEntity.setDurationInMs(transcriptEntryList.get(transcriptEntryList.size()-1).getEndMs());


        meetingsRepository.save(meetingsEntity);
        meetingEntryRepository.saveAll(meetingsEntryEntitySet);
        return meetingsEntity;


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

}
