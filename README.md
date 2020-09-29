# SlotGenerator

###Gradle
To use in android

        implementation 'SlotGenerator:co.queubuster.slotgenerator:1.0-SNAPSHOT'

###Maven
        
        <dependency>
          <groupId>SlotGenerator</groupId>
          <artifactId>co.queubuster.slotgenerator</artifactId>
          <version>1.0-SNAPSHOT</version>
        </dependency>

#### How to use 
        SlotGenerator.newInstance()
                        .setSlotDuration(45) // Slot duration
                        .setStartTime("4:00 AM") //Slot start time
                        .setEndTime("11:00 PM") //Slot end time
                        .setFormat(SlotGenerator.FORMAT_12) //Telling my start and end time is in 12 hr format
                        .setStartThresholdTime("8:00") // It will start generating slot from 8:00 AM (Optional)
                        .setEndThresholdTime("12:00") // It will generate last slot to 12:00 PM (Optional)
                        .setCallback(new SlotGenerationCallback() {
                            @Override
                            public void onSlotGenerated(List<Slot> slots, String nextStartTime) {
                                //Here you will get list of slots
                                
                            }
                        })
                        .generate();

