/********************************************************************************************************
 * @Module Account Service - maintain the required Account Control parameters
 * @author Udara Liyanage
 * @Date 22-08-2019
 *
 ********************************************************************************************************
 *  ###   Date         Story Point    	Task No     Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-08-2019   FX-1511         	FX-1640		UdaraLi      Created
 *
 ********************************************************************************************************/

package com.fusionx.lending.transaction.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
 * @author Udara Liyanage
 * @Developed with @IntelijIdea
 */

public class FusionDateConveterUtil {
    protected static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    protected static final String TIME_ZONE = "UTC";
    protected static final int FRIST_DAY = 1;

    public static Date getUTCDateTime() throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.parse(sdf.format(new Date()));
    }

    public static Date getStartAndEndDate(int year, int month, boolean isFirstDayOfMonth, boolean isLastDayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        if (isFirstDayOfMonth)
            calendar.set(year, month, FRIST_DAY, 0, 0);
        else if (isLastDayOfMonth)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    public static Date parseStringToDate(String stringDate) throws ParseException {
        return new SimpleDateFormat("yyyy/MM/dd").parse(stringDate);
    }

    public static Date parseStringToDateSlashFormat(String stringDate) throws ParseException {
        return new SimpleDateFormat("yyyy/MM/dd").parse(stringDate);
    }

    public static int ageCalculator(LocalDate fromDate, LocalDate toDate) {
        if ((fromDate != null) && (toDate != null)) {
            int totalDays = 0;
            int months = Period.between(fromDate, toDate).getMonths();
            Calendar calendar = new GregorianCalendar();
            calendar.set(Calendar.MONTH, toDate.getMonthValue());

            for (int i = 0; i < months; i++) {
                totalDays += calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.MONTH, -1);
            }
            return totalDays + Period.between(fromDate, toDate).getDays();
        } else {
            return 0;
        }
    }

    public static boolean validateWithSystemDate(String Date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date sysDate = calendar.getTime();

        Date mutualDate = new SimpleDateFormat("yyyy/MM/dd").parse(Date);

        if (sysDate.before(mutualDate) || sysDate.equals(mutualDate))
            return true;
        else
            return false;
    }
}
