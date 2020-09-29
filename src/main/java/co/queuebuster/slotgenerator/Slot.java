package co.queuebuster.slotgenerator;

import java.io.Serializable;

public class Slot implements Serializable {
    String startTime;
    String endTime;


    public Slot(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDisplayTime() {
        String  st = Utils.changeDateFormat(startTime,"kk:mm", "hh:mm a");
        String  end = Utils.changeDateFormat(endTime,"kk:mm", "hh:mm a");
        return  st + " - " + end;
    }

    public String getDisplayTime24HrFormat() {
        return  startTime + " - " + endTime;
    }


    @Override
    public boolean equals(Object o) {
        if(o instanceof Slot) {
            return ((Slot) o).getEndTime().equals(endTime) && ((Slot) o).getStartTime().equals(startTime);
        }
      return super.equals(o);
    }

}

