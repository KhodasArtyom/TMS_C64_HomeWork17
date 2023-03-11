package by.teachMeSkills.KhodasArtyom.HomeWork17;

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Message {

    private final User author;
    private final String message;

    private final Instant time;

    public Message(User author, String message,Instant time) {
        this.author = author;
        this.message = message;
        this.time = time;
    }

    @Override
    public String toString() {
        return
                author + "\n" + " message:'" + message + "\n" + " time:" + time;
    }

    public User getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTime() {
        return time;
    }
}
