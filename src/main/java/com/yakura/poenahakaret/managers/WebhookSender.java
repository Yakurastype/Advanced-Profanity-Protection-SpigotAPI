package com.yakura.poenahakaret.managers;

import okhttp3.*;

import java.io.IOException;

public class WebhookSender {

    private static final OkHttpClient client = new OkHttpClient();

    public static void sendWebhook(String url, String playerName, String fullMessage, String foulWord, String punishmentCommand, String category) {
        String json = "{"
                + "\"embeds\": ["
                + "{"
                + "\"title\": \"Küfür Engellendi\","
                + "\"color\": 16711680,"
                + "\"fields\": ["
                + "{"
                + "\"name\": \"Küfür Eden Kişi\","
                + "\"value\": \"" + playerName + "\""
                + "},"
                + "{"
                + "\"name\": \"Küfürün Geçtiği Tam Mesaj\","
                + "\"value\": \"" + fullMessage + "\""
                + "},"
                + "{"
                + "\"name\": \"Küfür Kısmı\","
                + "\"value\": \"" + foulWord + "\""
                + "},"
                + "{"
                + "\"name\": \"Uygulanan Ceza Komutu\","
                + "\"value\": \"" + punishmentCommand + "\""
                + "},"
                + "{"
                + "\"name\": \"Küfür Katagorisi\","
                + "\"value\": \"" + category + "\""
                + "}"
                + "]"
                + "}"
                + "]"
                + "}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }
}
