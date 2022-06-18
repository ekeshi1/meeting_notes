package com.example.meeting_notes;

import com.example.meeting_notes.model.MeetingTranscriptEntry;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestParsing {





    @Test
    public void testDurationParsing(){
        String durationString = "0:0:0.0 --> 0:0:3.290";

        MeetingTranscriptEntry meetingTranscriptEntry = new MeetingTranscriptEntry("", durationString, "");
        assertEquals(0,meetingTranscriptEntry.getStartMinuteFromDurationString(durationString));
        assertEquals(3290,meetingTranscriptEntry.getEndMinuteFromDurationString(durationString));
    }

}



