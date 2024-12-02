package com.example.aiapplication;
import java.util.List;

public class DomainSearchResponse {
    private Data data;
    public Data getData(){
        return data;
    }
    public static class Data{
        private List<Email> emails;
        public List<Email> getEmails(){
            return emails;
        }
        public static class Email {
            private String value;
            private String type;

            public String getValue() {
                return value;
            }

            public String getType() {
                return type;
            }

        }
    }
}
