package by.teachMeSkills.khodasArtyom.homeWork17;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class ChatService {
    private final int messageLimit;
    private final Duration timeLimit;
    private Message[] historyOfMessage;

    public ChatService(int messageLimit, Duration timeLimit) {
        if (timeLimit.compareTo(Duration.ZERO) <= 0) {
            throw new IllegalArgumentException("time should be positive");
        }
        if (messageLimit <= 0) {
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


    public Message[] getHistory() {

        return Arrays.copyOf(historyOfMessage, historyOfMessage.length);
    }


    private void saveMessage(Message messages) {
        historyOfMessage = Arrays.copyOf(historyOfMessage, historyOfMessage.length + 1);
        historyOfMessage[historyOfMessage.length - 1] = messages;
    }


    public boolean writeMessage(User author, String content) throws UserRateLimitingException {
        Instant createInstant = Instant.now();
        exceedRateLimiting(author, createInstant);
        Message newMessage = new Message(author, content, createInstant);
        saveMessage(newMessage);
        return true;
    }


    public void exceedRateLimiting(User author, Instant createdInstant) throws UserRateLimitingException {
        Instant checkFrom = createdInstant.minus(getDuration());
        int count = 0;
        for (int i = historyOfMessage.length - 1; i >= 0; i--) {
            Message message = historyOfMessage[i];
            if (message.getTime().isBefore(checkFrom)) {
                return;
            }
            if (message.getAuthor().getLogin().equals(author.getLogin())) {
                count++;
                if (count == messageLimit) {
                    throw new UserRateLimitingException(message.getTime().plus(getDuration()));
                }
            }
        }

    }


}
