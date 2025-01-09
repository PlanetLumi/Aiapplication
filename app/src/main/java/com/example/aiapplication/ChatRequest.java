package com.example.aiapplication;
import java.util.List;

public class ChatRequest {
    private String model;
    private List<Message> messages;

    //defines chat request
    public ChatRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }



    public static class Message {
        private final String role;
        private final String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}