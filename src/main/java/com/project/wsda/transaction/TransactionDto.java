package com.project.wsda.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private String shopUsername;
    private Integer cardId;
    private Integer amount;
    private LocalDateTime timestamp;

}
