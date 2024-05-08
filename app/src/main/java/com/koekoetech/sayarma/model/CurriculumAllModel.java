package com.koekoetech.sayarma.model;

import java.util.ArrayList;

public class CurriculumAllModel {

    private ArrayList<CurriculumLevelModel> levels;
    private ArrayList<CurriculumChapterModel> chapters;
    private ArrayList<CurriculumSubChapterModel> subChapters;
    private ArrayList<CurriculumSectionCardsModel> SectionCards;

    public ArrayList<CurriculumLevelModel> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<CurriculumLevelModel> levels) {
        this.levels = levels;
    }

    public ArrayList<CurriculumChapterModel> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<CurriculumChapterModel> chapters) {
        this.chapters = chapters;
    }

    public ArrayList<CurriculumSubChapterModel> getSubChapters() {
        return subChapters;
    }

    public void setSubChapters(ArrayList<CurriculumSubChapterModel> subChapters) {
        this.subChapters = subChapters;
    }

    public ArrayList<CurriculumSectionCardsModel> getSectionCards() {
        return SectionCards;
    }

    public void setSectionCards(ArrayList<CurriculumSectionCardsModel> sectionCards) {
        SectionCards = sectionCards;
    }
}
