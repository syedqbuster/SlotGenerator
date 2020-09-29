package co.queuebuster.slotgenerator;

import java.util.List;

public interface SlotGenerationCallback {
    /**
     *
     * @param slots It will return list of {@link Slot} object
     * @param nextStartTime If you pass any end threshold to stop it will give you next start time for slot generation
     */
    void onSlotGenerated(List<Slot> slots, String nextStartTime);
}
