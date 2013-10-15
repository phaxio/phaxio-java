package com.phaxio.status;

public enum FaxStatus {

    QUEUED("queued"),
    BATCHING("pendingbatch"),
    IN_PROGRESS("inprogress"),
    SUCCESS("success"),
    FAILURE("failure"),
    PARTIAL_SUCCESS("partialsuccess");

    private final String apiName;

    FaxStatus(String apiName){
        this.apiName = apiName;
    }
    
    public String getApiName(){
        return apiName;
    }

    public static FaxStatus fromApiName(String apiName) {
        if (apiName != null) {
            for (FaxStatus fs : FaxStatus.values()) {
                if (apiName.equalsIgnoreCase(fs.apiName)) {
                    return fs;
                }
            }
        }
        return null;
    }

}
