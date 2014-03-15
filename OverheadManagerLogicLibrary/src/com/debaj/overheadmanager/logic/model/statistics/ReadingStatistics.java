package com.debaj.overheadmanager.logic.model.statistics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.debaj.overheadmanager.logic.model.bean.Reading;
import com.debaj.overheadmanager.logic.util.StringUtil;

public class ReadingStatistics {

    public List<ReadingWithDelta> readingsList;
    public List<ReadingWithDelta> dailyList;

    public ReadingWithDelta minConsumption;
    public ReadingWithDelta maxConsumption;
    public float average;
    public float consumption;

    public ReadingStatistics() {
        readingsList = new ArrayList<ReadingWithDelta>();
    }

    public void addReading(Reading reading, float difference) {
        if (readingsList == null) {
            readingsList = new ArrayList<ReadingWithDelta>();
        }
        readingsList.add(new ReadingWithDelta(reading, difference));
    }

    public List<ReadingWithDelta> getReadingsList() {
        return readingsList;
    }

    public ReadingWithDelta getFirst() {
        return readingsList.get(0);
    }

    public ReadingWithDelta getFirstDaily() {
        return dailyList.get(0);
    }

    public ReadingWithDelta getLast() {
        return readingsList.get(readingsList.size() - 1);
    }

    public ReadingWithDelta getLastDaily() {
        return dailyList.get(dailyList.size() - 1);
    }

    public void calculateDailyAverages() {
        Map<String, ReadingWithDelta> readingsMap = new HashMap<String, ReadingWithDelta>();

        String key = null;
        Collections.sort(readingsList);
        Date first = null;
        Date last = null;

        for (ReadingWithDelta reading : readingsList) {
            if (first == null)
                first = new Date(reading.reading.getTime());
            last = new Date(reading.reading.getTime());
            key = StringUtil.getDateForKey(new Date(reading.reading.getTime()));
            if (readingsMap.get(key) == null) {
                readingsMap.put(key, reading);
            } else {
                readingsMap
                        .put(key, calcAverage(readingsMap.get(key), reading));
            }
        }

        if (first == null) {
            return;
        }

        List<Date> allDays = getAllDays(first, last);
        List<Date> gaps = new ArrayList<Date>();
        ReadingWithDelta previous = null;
        ReadingWithDelta actual = null;
        String dayString = null;

        for (Date day : allDays) {

            dayString = StringUtil.getDateForKey(day);

            if (readingsMap.containsKey(dayString)) {
                actual = readingsMap.get(dayString);
                if (previous == null) {
                    previous = actual;
                }
                if (!gaps.isEmpty()) {
                    float dailyAverage = (actual.reading.getValue() - previous.reading.getValue())
                            / (gaps.size() + 1);
                    float consumption = previous.reading.getValue();
                    for (Date gapDate : gaps) {
                        consumption += dailyAverage;
                        Reading reading = new Reading();
                        reading.setTime(gapDate.getTime());
                        reading.setValue(consumption);
                        readingsMap.put(StringUtil.getDateForKey(gapDate),
                                new ReadingWithDelta(reading, 0));
                    }
                    gaps.clear();
                }

                previous = actual;
            } else {
                gaps.add(day);
            }
        }

        dailyList = new ArrayList<ReadingWithDelta>(readingsMap.values());
        Collections.sort(dailyList);

        previous = null;
        readingsMap = null;

        for (ReadingWithDelta reading : dailyList) {
            if (previous == null) {
                reading.difference = 0.0f;
            } else {
                reading.difference = reading.reading.getValue() - previous.reading.getValue();
            }
            previous = reading;
        }

        minConsumption = null;
        maxConsumption = null;
        consumption = 0.0f;

        for (ReadingWithDelta reading : dailyList) {
            consumption += reading.difference;

            if (minConsumption == null
                    || ((minConsumption.difference > reading.difference) || (minConsumption.difference == 0.0f))) {
                minConsumption = reading;
            }

            if (maxConsumption == null
                    || maxConsumption.difference < reading.difference) {
                maxConsumption = reading;
            }
        }

        average = consumption / dailyList.size();
    }

    private List<Date> getAllDays(Date first, Date last) {
        List<Date> dates = new ArrayList<Date>();
        Calendar cal = new GregorianCalendar();
        cal.setTime(first);
        while (cal.getTime().before(last)) {
            dates.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }
        dates.add(last);
        return dates;
    }

    private ReadingWithDelta calcAverage(ReadingWithDelta first, ReadingWithDelta second) {
        first.reading.setValue((first.reading.getValue() + second.reading.getValue()) / 2);
        return first;
    }
}
