package com.doctor.appointment.service;

import com.doctor.appointment.model.Doctor;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Service
public class DoctorService {
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public DoctorService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() throws JsonProcessingException {
        String url = "http://localhost:8080/doctors";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        List<Doctor> doctors = objectMapper.readValue(response.getBody(), new TypeReference<List<Doctor>>(){});
        return doctors;
    }
}
