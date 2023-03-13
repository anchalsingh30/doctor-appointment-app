package com.doctor.appointment.repository;

import com.doctor.appointment.model.Appointment;
import com.doctor.appointment.model.Doctor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class DoctorAppointmentRepo {
    private Map<String, Doctor> doctors = new HashMap<>();
    private Map<String, List<Appointment>> appointments = new HashMap<>();

    public Doctor getDoctor(String id) {
        return doctors.get(id);
    }

    public Collection<Doctor> getAllDoctors() {
        return doctors.values();
    }

    public List<Appointment> getAppointments(String doctorId, LocalDate date) {
        return appointments.getOrDefault(key(doctorId, date), Collections.emptyList());
    }

    public void addAppointment(Appointment appointment) {
        List<Appointment> doctorAppointments = appointments.computeIfAbsent(
                key(appointment.getDoctorId(), appointment.getDateAndTime().toLocalDate()),
                k -> new ArrayList<>());
        doctorAppointments.add(appointment);
    }

    public void removeAppointment(String id) {
        appointments.values().forEach(appointments -> appointments.removeIf(a -> a.getId().equals(id)));
    }

    private String key(String doctorId, LocalDate date) {
        return doctorId + "_" + date.toString();
    }

}
