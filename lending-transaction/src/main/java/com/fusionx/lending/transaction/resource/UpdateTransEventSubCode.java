package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateTransEventSubCode extends TransEventSubCodeResourse {

    @JsonProperty("modifiedUser")
    private String modifiedUser;

    @JsonProperty("version")
    @NotBlank(message = "{version.not-null}")
    private String version;

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}
