package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FinancialStatementUpdateResource {

    @NotBlank(message = "{common.not-null}")
    @Size(max = 4, min = 4, message = "{common-code.size}")
    @Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
    private String code;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 70, message = "{common-name.size}")
    private String name;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 70, message = "{common.invalid-value}")
    private String statementType;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String statementTypeId;

    // @NotBlank(message = "{common.not-null}")
    @Size(max = 350, message = "{common-description.size}")
    private String description;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
    private String status;

    @Valid
    private List<FinancialStatementDetailsUpdateResource> levels;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String version;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    public String getStatementTypeId() {
        return statementTypeId;
    }

    public void setStatementTypeId(String statementTypeId) {
        this.statementTypeId = statementTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FinancialStatementDetailsUpdateResource> getLevels() {
        return levels;
    }

    public void setLevels(List<FinancialStatementDetailsUpdateResource> levels) {
        this.levels = levels;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
