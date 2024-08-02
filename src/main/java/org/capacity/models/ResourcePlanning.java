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
public class ResourcePlanning {
    private int id;
    private int resourceId;
    private LocalDate date;
    private float initiativeId;
    private float commited;
    private float planned;
    private float available;
}
