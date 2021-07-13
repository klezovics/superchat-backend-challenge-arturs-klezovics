package com.klezovich.superchat.controller.dto;

import lombok.Data;

@Data
public class EmailDto {

    private String emailTo;
    private String emailFrom;
    private String text;
}
