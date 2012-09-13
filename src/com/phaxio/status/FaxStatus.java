package com.phaxio.status;

public enum FaxStatus {

    QUEUED("queued"),
    BATCHING("pendingbatch"),
    IN_PROGRESS("inprogress"),
    SUCCESS("success"),
    FAILURE("failure"),
    PARTIAL_SUCCESS("partialsuccess");

    String name;

    FaxStatus(String name){
        this.name = name;
    }


}
