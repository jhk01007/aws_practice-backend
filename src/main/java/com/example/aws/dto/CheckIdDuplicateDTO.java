package com.example.aws.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CheckIdDuplicateDTO {

    private Boolean available;

    @JsonCreator
    public CheckIdDuplicateDTO(@JsonProperty("available") Boolean available) {
        this.available = available;
    }
}
