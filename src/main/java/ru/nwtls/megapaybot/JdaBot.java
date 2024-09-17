package ru.nwtls.megapaybot;

import net.dv8tion.jda.api.JDA;
import org.jetbrains.annotations.NotNull;
import ru.nwtls.megapaybot.listener.ChatListener;
import ru.nwtls.megapaybot.ticket.TicketManager;

public class JdaBot {
    private JDA jda;
    private final @NotNull TicketManager ticketManager = new TicketManager();

    public JdaBot(@NotNull JDA jda) {
        this.jda = jda;
        this.registerListeners();
    }

    // TODO: add parser or idk
    private void registerListeners() {
        this.jda.addEventListener(new ChatListener());
    }
}
