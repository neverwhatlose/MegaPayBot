package ru.nwtls.megapaybot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static final @NotNull Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final HashMap<Class<JdaBot>, Object> jdaBots = new HashMap<>();
    private static final EnumSet<GatewayIntent> intents = EnumSet.of(
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.DIRECT_MESSAGES,
            GatewayIntent.MESSAGE_CONTENT,
            GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
            GatewayIntent.GUILD_PRESENCES,
            GatewayIntent.SCHEDULED_EVENTS,
            GatewayIntent.GUILD_VOICE_STATES,
            GatewayIntent.GUILD_MODERATION
    );

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Map<String, Object> config = loadConfig();
        if (config == null) {
            LOGGER.atError().log("config.yml not found. Contact the developer. Startup aborted");
            return;
        }

        LinkedHashMap<String, Object> botConfig = (LinkedHashMap<String, Object>) config.get("bot");

        JDA jda = JDABuilder.create(botConfig.get("token").toString(), intents)
                .setActivity(Activity.listening("Yandex.Music"))
                .build();

        jdaBots.put(JdaBot.class, new JdaBot(jda));

        LOGGER.atInfo().log("done!");
    }

    public static @NotNull Object getBot(@NotNull Class<JdaBot> clazz) {
        return jdaBots.get(clazz);
    }

    public static @Nullable Map<String, Object> loadConfig() {
        File file = new File("./config.yml");
        if (!file.exists()) preloadConfig();

        Yaml yaml = new Yaml();
        try (InputStream input = new FileInputStream("./config.yml")) {
            return yaml.load(input);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private static void preloadConfig() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.yml")) {
            if (inputStream == null) {
                System.out.println("config.yml not found. Contact the developer");
                return;
            }
            Files.copy(inputStream, Path.of("./config.yml"), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
