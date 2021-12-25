package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateTransEventAccStatus extends TransEventAccStatusResource {

    @JsonProperty("modifiedUser")
    private String modifiedUser;

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

}
