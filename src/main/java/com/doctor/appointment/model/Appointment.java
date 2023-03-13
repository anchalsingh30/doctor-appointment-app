package com.doctor.appointment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Appointment {
    private String id;
    private String doctorId;
    private String patientFirstName;
    private String patientLastName;
    private LocalDateTime dateAndTime;
    private String kind;
}