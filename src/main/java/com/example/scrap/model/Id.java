package com.example.scrap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Id implements Serializable {
    private static final long serialVersionUID = 4749959063383083689L;
    private String server;
    private LocalDateTime date;
}
