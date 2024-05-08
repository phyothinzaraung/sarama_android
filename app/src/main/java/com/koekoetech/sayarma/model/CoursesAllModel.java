package com.koekoetech.sayarma.model;

import java.util.ArrayList;

public class CoursesAllModel {

    private ArrayList<LevelModel> levels;
    private ArrayList<ChapterModel> chapters;
    private ArrayList<SubChapterModel> subChapters;
    private ArrayList<SectionCardsModel> SectionCards;

    public ArrayList<LevelModel> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<LevelModel> levels) {
        this.levels = levels;
    }

    public ArrayList<ChapterModel> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<ChapterModel> chapters) {
        this.chapters = chapters;
    }

    public ArrayList<SubChapterModel> getSubChapters() {
        return subChapters;
    }

    public void setSubChapters(ArrayList<SubChapterModel> subChapters) {
        this.subChapters = subChapters;
    }

    public ArrayList<SectionCardsModel> getSectionCards() {
        return SectionCards;
    }

    public void setSectionCards(ArrayList<SectionCardsModel> sectionCards) {
        SectionCards = sectionCards;
    }
}
