package by.teachMeSkills.KhodasArtyom.HomeWork17;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class ChatService implements ChatMethods {
    private int messageLimit;
    private Duration timeLimit;
    private Message[] historyOfMessage;

    public ChatService(int messageLimit, Duration timeLimit) {
        if (timeLimit.compareTo(Duration.ZERO) <= 0) {
            throw new IllegalArgumentException("time should be positive");
        }
        if (messageLimit <= 0 ) {
            throw new IllegalArgumentException("message limit should e positive");
        }
        this.messageLimit = messageLimit;
        this.timeLimit = timeLimit;
        this.historyOfMessage = new Message[0];

    }

    public int getMessageLimit() {
        return messageLimit;
    }

    public Duration getDuration() {
        return timeLimit;
    }


    @Override
    public Message[] getHistory() {

        return Arrays.copyOf(historyOfMessage, historyOfMessage.length);
    }


    @Override
    public void saveMessage(Message messages) {
        historyOfMessage = Arrays.copyOf(historyOfMessage, historyOfMessage.length + 1);
        historyOfMessage[historyOfMessage.length - 1] = messages;
    }


    @Override
    public boolean writeMessage(User author, String content) {
        Instant createInstant = Instant.now();
        if(exceedRateLimiting(author,createInstant)) {
            return false;
        }
        Message newMessage = new Message(author, content, createInstant);
        saveMessage(newMessage);
        return true;
    }

    @Override
    public boolean exceedRateLimiting(User author, Instant createdInstant) {
        Instant checkFrom = createdInstant.minus(getDuration());
        int count = 0;
        for (int i = historyOfMessage.length - 1; i >= 0; i--) {
            Message message = historyOfMessage[i];
            if (message.getTime().isBefore(checkFrom)) {
                return false;
            }
            if (message.getAuthor().getLogin().equals(author.getLogin())) {
                count++;
                if (count == getMessageLimit()) {
                    return true;
                }
            }
        }
        return false;
    }


}
