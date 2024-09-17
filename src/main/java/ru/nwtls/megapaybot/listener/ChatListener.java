package ru.nwtls.megapaybot.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.nwtls.megapaybot.Main;

public class ChatListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Main.LOGGER.info(event.getMessage().toString());
    }
}
