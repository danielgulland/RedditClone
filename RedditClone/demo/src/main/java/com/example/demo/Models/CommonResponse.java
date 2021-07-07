package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;

public class CommonResponse {
    @Getter
    @Setter
    boolean response;
    @Getter
    @Setter
    String message;

    public CommonResponse(boolean response, String message) {
        this.response = response;
        this.message = message;
    }
}
