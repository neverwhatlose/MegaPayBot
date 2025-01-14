package ru.nwtls.megapaybot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nwtls.megapaybot.ticket.listener.ChatListener;
import ru.nwtls.megapaybot.ticket.TicketManager;
import ru.nwtls.megapaybot.ticket.listener.SlashCommandListener;

import java.util.HashMap;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class JdaBot {
    @SuppressWarnings("unchecked")
    private final HashMap<String, Object> mainDatabaseConfig = (HashMap<String, Object>) Main.getConfig().get("main-database");

    private final @NotNull TicketManager ticketManager = new TicketManager();
    private MainDatabase mainDatabase;
    private final @NotNull Logger logger = LoggerFactory.getLogger(JdaBot.class);

    private final @NotNull JDA jda;
    private final @NotNull String commandSymbol;

    public JdaBot(@NotNull JDA jda, @NotNull String commandSymbol) {
        this.jda = jda;
        this.commandSymbol = commandSymbol;

        try {
            this.mainDatabase = new MainDatabase(
                    this.mainDatabaseConfig.get("url").toString(),
                    this.mainDatabaseConfig.get("login").toString(),
                    this.mainDatabaseConfig.get("password").toString());
            this.mainDatabase.init();
        } catch (MainDatabase.MainDatabaseException e) {
            this.logger.atError().log("Failed to connect to database, startup aborted, exception: " + e.getMessage());
        }

        this.registerListeners();
        this.registerSlashCommands();
    }

    // TODO: add parser or idk
    private void registerListeners() {
        this.jda.addEventListener(new ChatListener());
        this.jda.addEventListener(new SlashCommandListener());
    }

    private void registerSlashCommands() {
        CommandListUpdateAction commands = this.jda.updateCommands();

        commands = commands.addCommands(
                Commands.slash("ping", "Ping?")
        );

        commands = commands.addCommands(
                Commands.slash("profile", "Show your bank profile")
        );

        commands = commands.addCommands(
                Commands.slash("balance", "Show your balance")
        );

        commands = commands.addCommands(
                Commands.slash("pay", "Pay <@user> <amount> of diamonds")
                    .addOption(STRING, "id", "Shop or user to pay for", true)
                    .addOption(STRING, "amount", "Amount of diamonds to pay", true)
        );

        commands = commands.addCommands(
                Commands.slash("deposit", "Deposit <amount> of diamonds")
                        .addOption(STRING, "amount", "Amount of diamonds to deposit", true)
        );

        commands = commands.addCommands(
                Commands.slash("withdraw", "Withdraw <amount> of diamonds")
                        .addOption(STRING, "amount", "Amount of diamonds to withdraw", true)
        );

        commands = commands.addCommands(
                Commands.slash("register", "Register your bank card")
        );

        commands.queue();
    }

    public @NotNull JDA getJda() {
        return this.jda;
    }

    public @NotNull String getCommandSymbol() {
        return this.commandSymbol;
    }

    public @NotNull MainDatabase getMainDatabase() {
        return this.mainDatabase;
    }
}
