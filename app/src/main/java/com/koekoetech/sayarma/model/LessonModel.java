package com.koekoetech.sayarma.model;

import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LessonModel extends RealmObject implements Serializable, Pageable {

    @PrimaryKey
    private int LessonId;
    private int Breakpoint;
    private String Type;
    private String Level;
    private String SubChapter;
    private String LessonTitle;

    public int getLessonId() {
        return LessonId;
    }

    public void setLessonId(int lessonId) {
        LessonId = lessonId;
    }

    public int getBreakpoint() {
        return Breakpoint;
    }

    public void setBreakpoint(int breakpoint) {
        Breakpoint = breakpoint;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getSubChapter() {
        return SubChapter;
    }

    public void setSubChapter(String subChapter) {
        SubChapter = subChapter;
    }

    public String getLessonTitle() {
        return LessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        LessonTitle = lessonTitle;
    }
}
