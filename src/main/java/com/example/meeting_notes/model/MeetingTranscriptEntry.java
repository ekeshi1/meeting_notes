package com.example.meeting_notes.model;

import com.example.meeting_notes.dao.MeetingsEntryEntity;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MeetingTranscriptEntry {
 String name;
 long startMs;
 long endMs;
 String text;

    public MeetingTranscriptEntry(String name, String durationString, String text) {
        this.name = name;
        this.startMs = getStartMinuteFromDurationString(durationString);
        this.endMs = getEndMinuteFromDurationString(durationString);
        this.text = text;
    }

    public MeetingTranscriptEntry(String name, Long startMs, Long endMs, String text) {
        this.name = name;
        this.startMs = startMs;
        this.endMs = endMs;
        this.text = text;
    }

    public  long getEndMinuteFromDurationString(String durationString) {
        String startPart = durationString.split(" --> ")[1];
        String startPartWithoutMs = startPart.split("\\.")[0];
        Integer h = Integer.parseInt(startPartWithoutMs.split(":")[0]);
        int m = Integer.parseInt(startPartWithoutMs.split(":")[1]);
        int s = Integer.parseInt(startPartWithoutMs.split(":")[2]);
        int ms = Integer.parseInt(startPart.split("\\.")[1]);
        this.endMs = TimeUnit.HOURS.toMillis(h) + TimeUnit.MINUTES.toMillis(m) + TimeUnit.SECONDS.toMillis(s) + ms;     // 1 day to milliseconds.h

        return this.endMs;

    }

    public long getStartMinuteFromDurationString(String durationString) {
        String startPart = durationString.split(" --> ")[0];
        String startPartWithoutMs = startPart.split("\\.")[0];
        Integer h = Integer.parseInt(startPartWithoutMs.split(":")[0]);
        int m = Integer.parseInt(startPartWithoutMs.split(":")[1]);
        int s = Integer.parseInt(startPartWithoutMs.split(":")[2]);
        int ms = Integer.parseInt(startPart.split("\\.")[1]);
        this.startMs = TimeUnit.HOURS.toMillis(h) + TimeUnit.MINUTES.toMillis(m) + TimeUnit.SECONDS.toMillis(s) + ms;     // 1 day to milliseconds.h

        return this.startMs;
    }

    public MeetingTranscriptEntry(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartMs() {
        return startMs;
    }

    public void setStartMs(long startMs) {
        this.startMs = startMs;
    }

    public long getEndMs() {
        return endMs;
    }

    public void setEndMs(long endMs) {
        this.endMs = endMs;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toCsvRow() {
        return Stream.of(name,text)
                .map(value -> value.replaceAll("\"", "\"\""))
                .map(value -> Stream.of("\"", ",").anyMatch(value::contains) ? "\"" + value + "\"" : value)
                .collect(Collectors.joining(","));
    }

    public MeetingsEntryEntity toEntity(){
        return new MeetingsEntryEntity(name, startMs, endMs, text);
    }
    public static MeetingTranscriptEntry fromEntity(MeetingsEntryEntity entryEntity){
        return new MeetingTranscriptEntry(entryEntity.getName(), entryEntity.getStartMS(), entryEntity.getEndMs(), entryEntity.getText());
    }
}
