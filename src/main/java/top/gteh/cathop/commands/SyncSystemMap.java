package top.gteh.cathop.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class SyncSystemMap {
    private static final String apiURL = "https://update.cathop.gteh.top/api/syncSystemMap";

    public static void register() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
            if (dedicated) {
                dispatcher.register(CommandManager.literal("cathopsync")
                        .requires(source -> source.hasPermissionLevel(3))
                        .then(CommandManager.argument("key", StringArgumentType.word())
                                .executes(SyncSystemMap::executeSync)));
            }
        }));
    }

    private static int executeSync(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        source.sendFeedback(new TranslatableText("commands.cathop.synchronizing"), false);
        source.sendFeedback(new TranslatableText("commands.cathop.ex10pw"), false);
        // String apiUsername = source.getEntity() instanceof ServerPlayerEntity ? source.getName() : "cathop";
        String apiPassword = StringArgumentType.getString(context, "key");
        String encodedCredentials = Base64.getEncoder().encodeToString(("cathop:" + apiPassword).getBytes());

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/data"))
                .GET()
                .build();

        client.sendAsync(getRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(responseData -> {
                    if (!responseData.isEmpty()) {
                        HttpRequest postRequest = HttpRequest.newBuilder()
                                .uri(URI.create(apiURL))
                                .header("Content-Type", "application/json; charset=UTF-8")
                                .header("Authorization", "Basic " + encodedCredentials)
                                .header("User-Agent", "cathop-v0503")
                                .POST(HttpRequest.BodyPublishers.ofString(responseData))
                                .build();

                        try {
                            HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

                            switch (postResponse.statusCode()) {
                                case 200:
                                    source.sendFeedback(new TranslatableText("commands.cathop.sync_successful"), false);
                                    break;
                                case 401:
                                    source.sendError(new TranslatableText("commands.cathop.sync_failed_incorrect_key"));
                                    break;
                                case 403:
                                    source.sendError(new TranslatableText("commands.cathop.sync_failed_server_closed"));
                                    break;
                                case 406:
                                    source.sendError(new TranslatableText("commands.cathop.sync_failed_ua"));
                                case 418:
                                    source.sendError(new TranslatableText("commands.cathop.sync_failed_41"));
                                    break;
                                case 500:
                                    source.sendError(new TranslatableText("commands.cathop.sync_failed"));
                                default:
                                    source.sendError(new TranslatableText("commands.cathop.sync_failed"));
                            }
                        } catch (IOException | InterruptedException e) {
                            source.sendError(new TranslatableText("commands.cathop.sync_failed"));
                        }
                    } else {
                        source.sendError(new TranslatableText("commands.cathop.req_data_failed_conn"));
                    }
                })
                .exceptionally(e -> {
                    source.sendError(new TranslatableText("commands.cathop.req_data_failed"));
                    return null;
                });

        return 1;
    }
}
