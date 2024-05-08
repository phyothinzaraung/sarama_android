package com.koekoetech.sayarma.model;

import com.koekoetech.sayarma.helper.Pageable;

public class ChapterScoreModel implements Pageable {

    private ScoreModel score;
    private String title;
    private String levelID;

    public ScoreModel getScore() {
        return score;
    }

    public void setScore(ScoreModel score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }
}
