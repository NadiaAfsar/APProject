package model;

import java.util.UUID;

public class Request {
    private String requestName;
    private boolean accepted;
    private boolean declined;
    private String sender;
    private String receiver;
    private final String ID;

    public Request(String requestName, String sender, String receiver) {
        this.requestName = requestName;
        this.sender = sender;
        this.receiver = receiver;
        ID = UUID.randomUUID().toString();
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getRequestName() {
        return requestName;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getID() {
        return ID;
    }

    public boolean isDeclined() {
        return declined;
    }

    public void setDeclined(boolean declined) {
        this.declined = declined;
    }
}
