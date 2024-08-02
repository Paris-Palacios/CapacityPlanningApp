package org.capacity.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitiativePlanning {
    private int id;
    private LocalDate date;
    private float commited;
    private float planned;
    private float available;
    private float initiativeId;
    private float USANeeded;
    private float otherNeeded;
}
