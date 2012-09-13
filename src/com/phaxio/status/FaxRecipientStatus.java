package com.phaxio.status;

public enum FaxRecipientStatus {

    QUEUED("queued"),
    BATCHING("pendingbatch"),
    IN_PROGRESS("inprogress"),
    SUCCESS("success"),
    FAILURE("failure"),
    WILL_RETRY("willretry"),
    CALL_ACTIVE("callactive");


    private final String name;

    FaxRecipientStatus(String name){
        this.name = name;
    }


}
