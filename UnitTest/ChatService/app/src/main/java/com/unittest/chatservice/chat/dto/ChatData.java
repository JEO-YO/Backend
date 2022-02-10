package com.unittest.chatservice.chat.dto;

public class ChatData {

    private String message;
    private String sender;
    private String receiver;
    public static final String CHAT_DATA_TABLE = "ChatData";

    public ChatData(String message, String sender, String receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public ChatData() {
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
