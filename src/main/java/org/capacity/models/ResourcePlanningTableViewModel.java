package org.capacity.models;

import lombok.*;
import org.capacity.utilities.Months;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourcePlanningTableViewModel {
    private Months month;
    private ResourcePlanning monthAndSelectedInitiativeResourcePlanning;
}
