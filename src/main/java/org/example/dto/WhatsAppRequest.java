package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class WhatsAppRequest {
    private String from = "447860099299";
    private String to;
    private MessageContent content;
}

