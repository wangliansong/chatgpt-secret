package com.secret.chatgpt.front.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Membership {
    private Long id;
    private Long userId;
    private String packageName;
    private BigDecimal price;
    private int maxQuestions;
    private LocalDateTime expireDate;
}
