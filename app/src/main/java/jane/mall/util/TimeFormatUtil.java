package jane.mall.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeFormatUtil {

    public static String getTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static Long getTimeTurnMs(String time) {
        if (TextUtils.isEmpty(time))
            return 0l;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            return format.parse(time.toString()).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static String getNormalTime(long value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date(value));
        return time;
    }

    public static String getDate(String dateMills) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long birthdayMills;
        if (dateMills != null && !TextUtils.isEmpty(dateMills)) {
            birthdayMills = Long.parseLong(dateMills);
            return format.format(new Date(birthdayMills));
        } else {
            return null;
        }
    }

}
