package com.example.aiapplication;
import com.google.gson.annotations.SerializedName;


import java.util.List;

public class DomainSearchResponse {
    private Data data;
    public Data getData(){
        return data;
    }
    public static class Data{
        private String domain;
        private List<Email> emails;
        public String getDomain(){
            return domain;
        }
        public List<Email> getEmails(){
            return emails;
        }
        public static class Email{
            private String value;
            private String type;
            private int confidence;
            public String getValue(){
                return value;
            }
            public String getType(){
                return type;
            }
            public int getConfidence(){
                return confidence;
            }
        }
    }
}
