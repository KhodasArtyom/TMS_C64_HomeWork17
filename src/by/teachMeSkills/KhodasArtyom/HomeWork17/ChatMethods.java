package by.teachMeSkills.KhodasArtyom.HomeWork17;

import java.time.Instant;

public interface ChatMethods {

    Message[] getHistory();

    void saveMessage(Message messages);

    boolean writeMessage(User author,String content);

    boolean exceedRateLimiting(User author, Instant createdInstant);



}
