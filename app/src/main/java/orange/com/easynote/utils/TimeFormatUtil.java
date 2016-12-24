package orange.com.easynote.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.text.format.Time;

/**
 * Created by Orange on 2016/12/24.
 */

public class TimeFormatUtil {

    private TimeFormatUtil() {
    }

    /**
     * 格式化时间. 转化时间格式 一年前的时间之显示年，当天之前的只显示到天，当天的消息显示到分钟.
     *
     * @param context 上下文.
     * @param when    时间.
     * @return 格式化后的时间.
     */
    public static String formatTimeStampString(Context context, long when) {
        return formatTimeStampString(context, when, false);
    }

    /**
     * 格式化时间. 转化时间格式 一年前的时间之显示年，当天之前的只显示到天，当天的消息显示到分钟.
     *
     * @param context    上下文.
     * @param when       时间.
     * @param fullFormat 为true时 会显示详细时间.
     * @return 格式化后的时间.
     */
    public static String formatTimeStampString(Context context, long when, boolean fullFormat) {
        Time then = new Time();
        then.set(when);
        Time now = new Time();
        now.setToNow();

        // Basic settings for formatDateTime() we want for all cases.
        @SuppressWarnings("deprecation")
        int formatFlags = DateUtils.FORMAT_NO_NOON_MIDNIGHT |
                DateUtils.FORMAT_ABBREV_ALL |
                DateUtils.FORMAT_CAP_AMPM;

        // If the message is from a different year, show the date and year.
        if (then.year != now.year) {
            formatFlags |= DateUtils.FORMAT_SHOW_YEAR
                    | DateUtils.FORMAT_SHOW_DATE;
        } else if (then.yearDay != now.yearDay) {
            // If it is from a different day than today, show only the date.
            formatFlags |= DateUtils.FORMAT_SHOW_DATE;
        } else {
            // Otherwise, if the message is from today, show the time.
            formatFlags |= DateUtils.FORMAT_SHOW_TIME;
        }
        // If the caller has asked for full details, make sure to show the date
        // and time no matter what we've determined above (but still make
        // showing
        // the year only happen if it is a different year from today).
        if (fullFormat) {
            formatFlags |= (DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);
        }
        return DateUtils.formatDateTime(context, when, formatFlags);
    }
}
