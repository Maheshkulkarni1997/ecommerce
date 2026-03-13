package org.ecommerce.domainmodel;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AIResponse {
    private String message;
    private boolean success;
}