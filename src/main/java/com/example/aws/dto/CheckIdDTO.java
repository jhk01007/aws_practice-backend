package com.example.aws.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CheckIdDTO {
    private String memberId;

    @JsonCreator
    public CheckIdDTO(@JsonProperty("memberId") String memberId) {
        this.memberId = memberId;
    }
}
