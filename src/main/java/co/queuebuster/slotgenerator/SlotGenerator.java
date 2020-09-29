package co.queuebuster.slotgenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by Syed Hussain Mehdi on 29 September 2020
 */
public class SlotGenerator {

    private String endThresholdTime;
    private String startThresholdTime;
    private String startTime;
    private String endTime;
    private int slotDuration; //in minute
    private String format;
    private SlotGenerationCallback callback;

    public static final String FORMAT_24 = "kk:mm";
    public static final String FORMAT_12 = "hh:mm a";

    private SlotGenerator() {

    }

    public static SlotGenerator newInstance() {
        return new SlotGenerator();
    }

    /**
     *
     * @param slotDuration to tell the duration of slot. It should be in minute.
     * @return
     */
    public SlotGenerator setSlotDuration(int slotDuration) {
        this.slotDuration = slotDuration;
        return this;
    }

    /**
     *
     * @param callback set callback to get list of slots
     * @return
     */
    public SlotGenerator setCallback(SlotGenerationCallback callback) {
        this.callback = callback;
        return this;
    }

    /**
     *
     * @param endThresholdTime to tell end the range to this threshold. It should be always in 24hrs format
     * @return
     */
    public SlotGenerator setEndThresholdTime(String endThresholdTime) {
        this.endThresholdTime = endThresholdTime;
        return this;
    }

    /**
     *
     * @param startThresholdTime to tell start the range to this threshold. It should be always in 24hrs format
     * @return
     */
    public SlotGenerator setStartThresholdTime(String startThresholdTime) {
        this.startThresholdTime = startThresholdTime;
        return this;
    }

    /**
     * @param endTime to tell the last range of slot. It can be in any format but must call {{@link #setFormat(String)}} to
     *                to tell the format {@link #FORMAT_12} {{@link #FORMAT_24}}
     * @return
     */
    public SlotGenerator setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * @param startTime to tell the start range of slot. It can be in any format but must call {{@link #setFormat(String)}} to
     *                to tell the format {@link #FORMAT_12} {{@link #FORMAT_24}}
     * @return
     */
    public SlotGenerator setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * @param format The format is mandatory to tell what is the format of startTime and endTime
     * @return
     */
    public SlotGenerator setFormat(String format) {
        this.format = format;
        return this;
    }

    /**
     * Call this method to generate slots and you will get the all slots in {@link SlotGenerationCallback}
     * Before calling this method you must set all the property;
     */
    public void generate() {

        //CONVERT INTO 24 HRS IF GIVEN TIME IS IN 12 HRS
        if (format.equals(FORMAT_12)) {
            startTime = Utils.changeDateFormat(startTime, FORMAT_12, FORMAT_24);
            endTime = Utils.changeDateFormat(endTime, FORMAT_12, FORMAT_24);
        }

        //CREATE CALENDER INSTANCE FOR START AND END TIME;
        Calendar startCalender = Utils.getCalender(startTime);
        Calendar endCalender = Utils.getCalender(endTime);

        //FIXES : Description yet to be write
        if (startCalender.get(Calendar.HOUR_OF_DAY) == 0) {
            startCalender.add(Calendar.DATE, -1);
        }

        //SLOT FORMAT WILL ALWAYS BE IN 24
        SimpleDateFormat slotTimeFormat = new SimpleDateFormat(FORMAT_24, Locale.getDefault());

        //CHECK FOR START THRESHOLD
        if (startThresholdTime != null && startCalender.before(Utils.getCalender(startThresholdTime))) {
            startCalender = Utils.getCalender(startThresholdTime);
        }

        if (endThresholdTime == null) {
            endThresholdTime = endTime;
        }

        _generate(startCalender, endCalender, slotDuration, slotTimeFormat, endThresholdTime, callback);
    }

    private static List<Slot> _generate(Calendar startCal, Calendar endCal, int slotDuration, SimpleDateFormat slotTimeFormat, String endThresholdTime, SlotGenerationCallback callback) {

        String nextStartTime = "";

        List<Slot> slots = new ArrayList<Slot>();

        if(slotDuration == 0) {
            if(callback != null) callback.onSlotGenerated(slots, nextStartTime);
            return new ArrayList<Slot>();
        }

        while (endCal.after(startCal)) {
            String slotStartTime = slotTimeFormat.format(startCal.getTime());
            startCal.add(Calendar.MINUTE, slotDuration);
            String slotEndTime = slotTimeFormat.format(startCal.getTime());

            if (!nextStartTime.equals(endThresholdTime)) {
                slots.add(new Slot(slotStartTime, slotEndTime));

                System.out.println("SYED HUSSAIN MEHDI Generated Time: " + slotStartTime + " - " + slotEndTime);
            }

            if (endThresholdTime != null && Utils.getCalender(slotEndTime).after(Utils.getCalender(endThresholdTime))) {
                if (!nextStartTime.equals(endThresholdTime) && Utils.getCalender(nextStartTime).before(Utils.getCalender(endThresholdTime)))
                    nextStartTime = slotEndTime;

                break;
            }

            nextStartTime = slotEndTime;

        }

        if(callback != null) callback.onSlotGenerated(slots, nextStartTime);

        return slots;
    }
}
