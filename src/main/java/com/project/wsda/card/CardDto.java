package com.project.wsda.card;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    @Min(value = 1, message = "Id must be greater than 0")
    private Integer id;

    @Pattern(regexp = "(valid|invalid)")
    private String state;

    @Min(value = 1, message = "Credit must be between 1 and 10000")
    @Max(value = 10000, message = "Credit must be between 1 and 10000")
    @NotNull(message = "Provide a credit amount")
    private Integer credit;
}
