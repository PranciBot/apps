package com.prancibot.chatserver.utils;

public class DataFormater {
    public static String formatSSEData(String data) {
        return "data: " + data.replace("\n", "\ndata: ") + "\n\n";
    }
}
