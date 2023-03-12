package by.teachMeSkills.KhodasArtyom.HomeWork17;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class HomeWork17 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChatService chatService = new ChatService(2, Duration.ofSeconds(15));

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
                            % s
                            ===========
                            """,message.getAuthor(),message.getMessage(),message.getTime()
                            .atZone(ZoneId.of("Europe/Minsk"))
                            .format(DateTimeFormatter.ofPattern("HH:mm")),message.getMessage());
                }
            } else {
                User user = new User(input);
                System.out.println("Enter the message:");
                String message = scanner.nextLine();
                System.out.printf("""
                        %s - %s
                        %s
                        ============
                        """,user.getLogin(),message,Instant.now().atZone(ZoneId.of("Europe/Minsk")).
                        format(DateTimeFormatter.ofPattern("HH:mm")));
                chatService.writeMessage(user, message);
                if (chatService.exceedRateLimiting(user, Instant.now())) {
                    System.out.println("Too many messages");
                }

            }


        }
    }
}
