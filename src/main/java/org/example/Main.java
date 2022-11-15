package org.example;

import org.python.antlr.ast.Str;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    private static final List<String> VALID_ORIGINATORS = new ArrayList<>(Arrays.asList("EAS", "CIV", "WXR", "PEP"));
    private static final List<String> VALID_EVENTS = new ArrayList<>(Arrays.asList("BZW", "CFA", "CFW", "DSW", "EWW", "FFA", "FFW", "FFS", "FLA", "FLW",
            "FLS", "HWA", "HWW", "HUA", "HUW", "HLS", "SVA", "SVR", "SVS", "SQW",
            "SMW", "SPS", "SSA", "SSW", "TOA", "TOR", "TRA", "TRW", "TSA", "TSW",
            "WSA", "WSW", "AVA", "AVW", "BLU", "CAE", "CDW", "CEM", "EQW", "EVI",
            "FRW", "HMW", "LEW", "LAE", "TOE", "NUW", "RHW", "SPW", "VOW", "ADR",
            "DMO", "RMT", "RWT"));
    public static String originator;
    public static String event;
    public static List<String> fips;
    public static String eventFips;
    public static String senderId;
    public static String purge;
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        String header;
        System.out.println("Welcome! Please select an originator: ");
        setOriginator();
        System.out.println("Please select an event code: ");
        setEvent();
        System.out.println("Type in all FIPS codes, separating them with a space.");
        setFips();
        System.out.println("Type in the purge time here: (HHMM, 15 min intervals)");
        purge = sc.nextLine();
        System.out.println("Type in the sender ID here.");
        setSenderId();
        var utc = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("HHmm"));
        header = String.format("%s-%s-%s+%s-%s%s-%s-", originator, event, eventFips, purge, LocalDateTime.now().getDayOfYear(), utc, senderId);
        System.out.println(header);

    }
    static void setOriginator() {
        for (int i = 0; i < VALID_ORIGINATORS.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, VALID_ORIGINATORS.get(i));
        }
        try {
            originator = VALID_ORIGINATORS.get(Integer.parseInt(sc.nextLine()) - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println("Invalid. Try again.");
            setOriginator();
        }
    }
    static void setEvent() {
        for (int i = 0; i < VALID_EVENTS.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, VALID_EVENTS.get(i));
        }
        try {
            event = VALID_EVENTS.get(Integer.parseInt(sc.nextLine()) - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println("Invalid. Try again.");
            setEvent();

        }
    }
    static void setFips() {
        String[] currentFips = sc.nextLine().split(" ");
        if (currentFips.length > 36) {
            System.out.println("Too many FIPS; reducing to 36...");
            currentFips = Arrays.copyOf(currentFips, 36);
        }
        eventFips = String.join("-", currentFips);
        System.out.println(eventFips);
    }
    static void setSenderId() {
        senderId = sc.nextLine();
        if (senderId.length() < 8) {
            for (int i = senderId.length(); i < 8; i++) {
                senderId += " ";
            }
        } else if (senderId.length() > 8) {
            char[] newSenderId = senderId.toCharArray();
            senderId = String.valueOf(Arrays.copyOf(newSenderId, 8));
        }
    }
}