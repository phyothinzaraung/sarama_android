package com.koekoetech.sayarma.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurriculumSectionCardsModel extends RealmObject {

    @PrimaryKey
    private String SectionID;
    private String SubChapterID;
    private String CardName;

    public String getSectionID() {
        return SectionID;
    }

    public void setSectionID(String sectionID) {
        SectionID = sectionID;
    }

    public String getSubChapterID() {
        return SubChapterID;
    }

    public void setSubChapterID(String subChapterID) {
        SubChapterID = subChapterID;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }
}