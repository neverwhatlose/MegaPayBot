package ru.nwtls.megapaybot;

import net.dv8tion.jda.api.JDA;
import org.jetbrains.annotations.NotNull;
import ru.nwtls.megapaybot.listener.ChatListener;

public class JdaBot {
    private JDA jda;
    public JdaBot(@NotNull JDA jda) {
        this.jda = jda;
        this.jda.addEventListener(new ChatListener());
    }
}
