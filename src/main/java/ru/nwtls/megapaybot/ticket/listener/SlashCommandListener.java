package ru.nwtls.megapaybot.ticket.listener;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.jetbrains.annotations.NotNull;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        switch (commandName) {
            case "ping" -> this.ping(event);
            case "profile" -> {

            }
            case "balance" -> {

            }
            case "pay" -> {

            }
            case "deposit" -> {

            }
            case "withdraw" -> {

            }
            case "register" -> {

            }
        }
    }

    public void ping(@NotNull SlashCommandInteractionEvent event) {
        event.getInteraction().reply("pong").queue();
    }

    public void profile() {

    }

    public void balance() {

    }

    public void pay() {

    }

    public void deposit() {

    }

    public void withdraw() {

    }

    public void register() {

    }
}
