package com.sai.greenbus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by saipkri on 03/09/17.
 */
public final class TimeFrequencyUtil {

    private TimeFrequencyUtil() {
    }

    public static double frequencyInMinutes(final List<Integer> timesHHMMs) {
        if (timesHHMMs.size() > 1) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmm");
            long diffs = timesHHMMs.stream()
                    .sorted()
                    .mapToLong(hhmm -> {
                        try {
                            return simpleDateFormat.parse(hhmm + "").getTime();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }).reduce((a, b) -> a - b)
                    .getAsLong();

            long diffsInMinutes = Math.abs(diffs) / (1000 * 60);
            return diffsInMinutes / timesHHMMs.size() - 1;
        } else {
            return -1;
        }
    }
}
