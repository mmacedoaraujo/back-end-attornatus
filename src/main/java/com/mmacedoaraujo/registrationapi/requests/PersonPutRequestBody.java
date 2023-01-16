package com.mmacedoaraujo.registrationapi.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonPutRequestBody {

    private Long id;
    private String name;
    private LocalDate birthdate;
}
