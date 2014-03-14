package com.debaj.overheadmanager.logic.model.statistics;

import com.debaj.overheadmanager.logic.model.bean.Reading;

public class ReadingWithDelta implements Comparable<ReadingWithDelta> {
    public Reading reading;
    public float difference;

    public ReadingWithDelta(Reading reading, float difference) {
        this.reading = reading;
        this.difference = difference;
    }

    @Override
    public int compareTo(ReadingWithDelta another) {
        int compared = 0;
        if (this.reading.getTime() != another.reading.getTime()) {
            compared = this.reading.getTime() > another.reading.getTime() ? 1 : -1;
        }
        
        return compared;
    }
}
