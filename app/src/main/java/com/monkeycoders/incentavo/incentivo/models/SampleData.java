package com.monkeycoders.incentavo.incentivo.models;

import java.util.ArrayList;

public class SampleData {

    public static ArrayList<String> generateSampleData(int sample_data) {
        final ArrayList<String> data = new ArrayList<String>(sample_data);

        for (int i = 0; i < sample_data; i++) {
            data.add("SAMPLE #");
        }

        return data;
    }

}