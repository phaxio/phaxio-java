package com.phaxio.status;

public enum FaxRecipientStatus {

    QUEUED("queued"),
    BATCHING("pendingbatch"),
    IN_PROGRESS("inprogress"),
    SUCCESS("success"),
    FAILURE("failure"),
    WILL_RETRY("willretry"),
    CALL_ACTIVE("callactive");


    private final String apiName;

    FaxRecipientStatus(String apiName){
        this.apiName = apiName;
    }


    public String getApiName(){
        return apiName;
    }

    public static FaxRecipientStatus fromApiName(String apiName) {
        if (apiName != null) {
            for (FaxRecipientStatus fs : FaxRecipientStatus.values()) {
                if (apiName.equalsIgnoreCase(fs.apiName)) {
                    return fs;
                }
            }
        }
        return null;
    }


}
