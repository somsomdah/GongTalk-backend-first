package com.dasom.gongtalk.util;


import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.PushClient;
import io.github.jav.exposerversdk.PushClientException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageSender {

    private List<ExpoPushMessage> expoPushMessages;
    private PushClient pushClient;


    public MessageSender() throws PushClientException {
        this.expoPushMessages = new ArrayList<>();
        this.pushClient = new PushClient();
    }

    public static ExpoPushMessage createMessage(List<String> recipients, String title, String body, HashMap<String, Object> data) {
        ExpoPushMessage expoPushMessage = new ExpoPushMessage();

        for (String recipient : recipients) {

            if (!PushClient.isExponentPushToken(recipient)) {
                System.out.printf("[Exception] expo token [%s] - invalid expo push token", recipient);
                continue;
            }

            expoPushMessage.getTo().add(recipient);
        }

        expoPushMessage.setTitle(title);
        expoPushMessage.setBody(body);
        expoPushMessage.setData(data);

        return expoPushMessage;
    }

    public ExpoPushMessage createAndAddMessage(List<String> recipients, String title, String body, HashMap<String, Object> data) {
        ExpoPushMessage expoPushMessage = createMessage(recipients, title, body, data);
        this.addMessage(expoPushMessage);
        return expoPushMessage;
    }

    public void sendMessages() {
        List<List<ExpoPushMessage>> chunks = this.pushClient.chunkPushNotifications(this.expoPushMessages);
        for (List<ExpoPushMessage> chunk : chunks) {
            this.pushClient.sendPushNotificationsAsync(chunk);
        }
    }

    public void addMessage(ExpoPushMessage expoPushMessage) {
        this.expoPushMessages.add(expoPushMessage);
    }

}
