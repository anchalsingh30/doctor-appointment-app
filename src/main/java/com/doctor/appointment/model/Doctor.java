package com.doctor.appointment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Doctor {
    private String FirstName;
    private String LastName;
    private long id;
}
