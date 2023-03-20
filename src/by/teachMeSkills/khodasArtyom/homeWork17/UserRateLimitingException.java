package by.teachMeSkills.khodasArtyom.homeWork17;

import java.time.Instant;

public class UserRateLimitingException extends Exception{

    private final Instant timeUntil;

    public UserRateLimitingException(Instant timeUntil){
        this.timeUntil=timeUntil;
    }

    public Instant getLimitedUntil() {
        return timeUntil;
    }
}
