package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddTransEventAccStatus extends TransEventAccStatusResource {

    @JsonProperty("createdUser")
    private String createdUser;

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

}
