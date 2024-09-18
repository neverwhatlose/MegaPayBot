package ru.nwtls.megapaybot.ticket.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.nwtls.megapaybot.Main;

import java.util.Arrays;
import java.util.List;

public class ChatListener extends ListenerAdapter {
    public enum MenuType {
        BALANCE,
        PAYMENT,
        DEPOSIT,
        WITHDRAWAL,
        REQUEST
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMessage().getContentDisplay().startsWith(Main.getBot().getCommandSymbol()) && !(event.getAuthor().isBot())) {
            this.handle(event);
        }
    }

    private void handle(@NotNull MessageReceivedEvent event) {
        List<String> input = Arrays.stream(event.getMessage().getContentDisplay().split(" ")).toList();
        System.out.println(input);
    }
}
