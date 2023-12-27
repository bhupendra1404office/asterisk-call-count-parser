package com.example.scrap.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "incoming_calls_details")
public class IncomingCallsDetails {
    @EmbeddedId
    Id id;

    @Column(name = "channel_active")
    private long channelActive;

    @Column(name = "channel_used")
    private long channelUsed;

    @Column(name = "incremental_calls")
    private long incrementalCalls;

    @Column(name = "last_call_processed")
    private long lastCallProcessed;

}
//CREATE TABLE `crm`.`incoming_calls_details` (
//        `server` VARCHAR(255) NOT NULL,
//  `date` DATETIME NOT NULL,
//        `channel_active` INT NULL,
//  `channel_used` INT NULL,
//  `incremental_calls` INT NULL,
//  `last_call_processed` INT NULL,
//PRIMARY KEY (`server`, `date`));