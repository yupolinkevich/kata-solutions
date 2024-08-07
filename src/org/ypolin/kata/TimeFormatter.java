package org.ypolin.kata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://www.codewars.com/kata/52742f58faf5485cae000b9a
 *
 * Human readable duration format
 *
 * Description:
 * Your task in order to complete this Kata is to write a function which formats a duration, given as a number of seconds, in a human-friendly way.
 *
 * The function must accept a non-negative integer. If it is zero, it just returns "now". Otherwise, the duration is expressed as a combination of years, days, hours, minutes and seconds.
 *
 * It is much easier to understand with an example:
 *
 * * For seconds = 62, your function should return
 *     "1 minute and 2 seconds"
 * * For seconds = 3662, your function should return
 *     "1 hour, 1 minute and 2 seconds"
 * For the purpose of this Kata, a year is 365 days and a day is 24 hours.
 *
 * Note that spaces are important.
 *
 * Detailed rules
 * The resulting expression is made of components like 4 seconds, 1 year, etc. In general, a positive integer and one of the valid units of time, separated by a space. The unit of time is used in plural if the integer is greater than 1.
 *
 * The components are separated by a comma and a space (", "). Except the last component, which is separated by " and ", just like it would be written in English.
 *
 * A more significant units of time will occur before than a least significant one. Therefore, 1 second and 1 year is not correct, but 1 year and 1 second is.
 *
 * Different components have different unit of times. So there is not repeated units like in 5 seconds and 1 second.
 *
 * A component will not appear at all if its value happens to be zero. Hence, 1 minute and 0 seconds is not valid, but it should be just 1 minute.
 *
 * A unit of time must be used "as much as possible". It means that the function should not return 61 seconds, but 1 minute and 1 second instead. Formally, the duration specified by of a component must not be greater than any valid more significant unit of time.
 */
public class TimeFormatter {

    private enum TimeUnit {
        YEAR(3600*24*365, "year"),
        DAY (3600*24, "day"),
        HOUR(3600, "hour"),
        MIN(60, "minute"),
        SEC(1, "second");

        private int seconds;
        private String descr;
        TimeUnit(int seconds, String descr){
            this.seconds = seconds;
            this.descr = descr;
        }
    }

    public static String formatDuration(int seconds) {
        if(seconds == 0){
            return "now";
        }
        List<String> timeUnits = new ArrayList<>();
        int left = subtractTimeUnit(seconds, TimeUnit.YEAR, timeUnits);
        left = subtractTimeUnit(left, TimeUnit.DAY, timeUnits);
        left = subtractTimeUnit(left, TimeUnit.HOUR, timeUnits);
        left = subtractTimeUnit(left, TimeUnit.MIN, timeUnits);
        subtractTimeUnit(left, TimeUnit.SEC, timeUnits);
        if (timeUnits.size() > 1) {
            String lastItem = timeUnits.remove(timeUnits.size() - 1);
            return timeUnits.stream().collect(Collectors.joining(", ")) + " and " + lastItem;
        } else {
            return timeUnits.get(0);
        }
    }

    private static int subtractTimeUnit(int seconds, TimeUnit timeUnit, List<String> timeUnits ) {
        int left = seconds;
        if (seconds >= timeUnit.seconds) {
            int timeUnitCount = seconds / timeUnit.seconds;
            left = seconds % timeUnit.seconds;
            String format = (timeUnitCount > 1) ? "%d %ss": "%d %s";
            timeUnits.add(String.format(format, timeUnitCount, timeUnit.descr));
        }
        return left;
    }

    public static IntStream stream() {
        IntStream generated = IntStream.iterate(19, operand -> {
            var next = operand + 2;
            while (next % 2 == 0 || next % 3 == 0 || next % 5 == 0 || next % 7 == 0 || next % 9 == 0) {
                next++;
            }
            return next;
        });
        IntStream base = IntStream.of(2, 3, 5, 7, 11, 13, 17);
        return IntStream.concat(base, generated);
    }

    public static void main(String[] args) {
        System.out.println(stream().limit(50).mapToObj(value -> String.valueOf(value)).collect(Collectors.joining(" ,")));
    }
}
