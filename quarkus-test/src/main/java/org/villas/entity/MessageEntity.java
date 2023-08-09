package org.villas.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalTime;

@Entity
public class MessageEntity extends PanacheEntity {
    @Column(name = "message")
    public String message;
    @Column(name = "sent_time")
    public LocalTime sentTime;
}
