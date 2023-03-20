package by.teachMeSkills.khodasArtyom.homeWork17;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class HomeWork17 {
    public static void main(String[] args) throws UserRateLimitingException {
        Scanner scanner = new Scanner(System.in);
        ChatService chatService = new ChatService(3, Duration.ofSeconds(15));

        while (true) {
            System.out.println("Enter history if you want to see a history of comments or enter user and message");
            System.out.println("""
                    1.History
                    2.Chat
                    """);

            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("history")) {
                Message[] history = chatService.getHistory();
                for (int i = 0; i < history.length; i++) {
                    Message message = history[i];
                    System.out.printf("""
                            %s - %s
                            %s
                            ===========
                            Received
                            """, message.getAuthor().getLogin(), message.getMessage(), message.getTime()
                            .atZone(ZoneId.of("Europe/Minsk"))
                            .format(DateTimeFormatter.ofPattern("HH:mm")), message.getMessage());
                }
            } else {
                User user = new User(input);
                System.out.println("Enter the message:");
                String message = scanner.nextLine();
                System.out.printf("""
                        %s - %s
                        %s
                        ============
                        """, user.getLogin(), message, Instant.now().atZone(ZoneId.of("Europe/Minsk"))
                        .format(DateTimeFormatter.ofPattern("HH:mm")));
                try {
                    chatService.writeMessage(user, message);
                    chatService.exceedRateLimiting(user, Instant.now());


                } catch (UserRateLimitingException e) {
                    System.out.println("Too many requests.Repeat after " + Duration.between(Instant.now(),e
                            .getLimitedUntil()).toSeconds());
                }

            }


        }
    }
}
