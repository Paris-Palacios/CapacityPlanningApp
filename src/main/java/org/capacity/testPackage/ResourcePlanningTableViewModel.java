package org.capacity.testPackage;

import lombok.*;
import org.capacity.models.ResourcePlanning;
import org.capacity.utilities.Months;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourcePlanningTableViewModel {
    private Months month;
    private ResourcePlanning monthAndSelectedInitiativeResourcePlanning;
}
