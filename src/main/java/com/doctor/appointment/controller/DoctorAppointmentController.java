package com.doctor.appointment.controller;

import com.doctor.appointment.model.Appointment;
import com.doctor.appointment.model.Doctor;
import com.doctor.appointment.repository.DoctorAppointmentRepo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DoctorAppointmentController {
    private final DoctorAppointmentRepo repository;

    public DoctorAppointmentController(DoctorAppointmentRepo repository) {
        this.repository = repository;
    }

    @GetMapping("/doctors")
    public Collection<Doctor> getAllDoctors() {
        return repository.getAllDoctors();
    }

    @GetMapping("/appointments")
    public List<Appointment> getAppointments(
            @RequestParam String doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return repository.getAppointments(doctorId, date);
    }

    @PostMapping("/appointments")
    public ResponseEntity<Void> addAppointment(@RequestBody Appointment appointment) {
        validateAppointment(appointment);
        repository.addAppointment(appointment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Void> removeAppointment(@PathVariable String id) {
        repository.removeAppointment(id);
        return ResponseEntity.ok().build();
    }

    private void validateAppointment(Appointment appointment) {
        Doctor doctor = repository.getDoctor(appointment.getDoctorId());
        if (doctor == null) {
            throw new IllegalArgumentException("Invalid doctor ID");
        }
        if (appointment.getDateAndTime().getMinute() % 15 != 0) {
            throw new IllegalArgumentException("Invalid appointment time");
        }
        List<Appointment> doctorAppointments = repository.getAppointments(
                appointment.getDoctorId(), appointment.getDateAndTime().toLocalDate());
        if (doctorAppointments.stream().filter(a -> a.getDateAndTime().equals(appointment.getDateAndTime())).count() >= 3) {
            throw new IllegalArgumentException("Too many appointments at the same time");
        }
    }
}
