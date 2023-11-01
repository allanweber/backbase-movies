package com.backbase.movies.domain;

public enum Category {
    BEST_PICTURE("Best Picture"),

    //TODO: MAYBE ONE DAY
    ACTOR_LEADING("Actor -- Leading Role"),
    ACTOR_SUPPORTING("Actor -- Supporting Role"),
    ACTRESS_LEADING("Actress -- Leading Role"),
    ACTRESS_SUPPORTING("Actress -- Supporting Role"),
    ANIMATED("Animated Feature Film"),
    ART_DIRECTION("Art Direction"),
    CINEMATOGRAPHY("Cinematography"),
    CUSTOMER("Costume Design"),
    DIRECTING("Directing"),
    DOCUMENTARY("Documentary (Feature)"),
    DOCUMENTARY_SHORT("Documentary (Short Subject)"),
    EDITING("Film Editing"),
    FOREIGN("Foreign Language Film"),
    MAKEUP("Makeup"),
    MUSIC("Music (Scoring)"),
    SONG("Music (Song)"),
    SHORT_ANIMATION("Short Film (Animated)"),
    SHORT_FILM("Short Film (Live Action)"),
    SOUND("Sound"),
    SOUND_EDITING("Sound Editing"),
    VISUAL_EFFECTS("Visual Effects"),
    WRITING("Writing"),
    SPECIAL_EFFECTS("Special Effects (archaic category)"),
    DOCUMENTARY_OTHER("Documentary (other)"),
    ASSISTANT_DIRECTOR("Assistant Director (archaic category)");

    private final String csvValue;

    Category(String csvValue) {
        this.csvValue = csvValue;
    }

    public String getCsvValue() {
        return csvValue;
    }
}


