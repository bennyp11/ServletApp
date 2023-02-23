package com.servletapp.model;

public class BatchParticipant {
    private String batchId;
    private String participantEmail;

    public BatchParticipant(String batchId, String participantEmail) {
        this.batchId = batchId;
        this.participantEmail = participantEmail;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getParticipantEmail() {
        return participantEmail;
    }

    public void setParticipantEmail(String participantEmail) {
        this.participantEmail = participantEmail;
    }
}

