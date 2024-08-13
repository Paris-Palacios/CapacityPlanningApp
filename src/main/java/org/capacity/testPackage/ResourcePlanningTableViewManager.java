package org.capacity.testPackage;

import lombok.Getter;
import lombok.Setter;
import org.capacity.models.Initiative;
import org.capacity.models.Resource;
import org.capacity.models.ResourcePlanning;
import org.capacity.utilities.Months;

import java.nio.file.FileSystemNotFoundException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class ResourcePlanningTableViewManager {
    private Initiative initiative;
    private Year year;
    private Resource resource;
    private List<ResourcePlanning> resourcePlanningsByYearAndInitiative;
    private List<Months> monthsList = Arrays.stream(Months.values()).toList();
    private List<ResourcePlanningTableViewModel> resourcePlanningTableViewModels;

    public ResourcePlanningTableViewManager(Resource resource, Initiative initiative, Year year, List<ResourcePlanning> resourcePlanning) {
        this.initiative = initiative;
        this.year = year;
        this.resource = resource;
        this.resourcePlanningsByYearAndInitiative = filterByYearAndInitiative(resourcePlanning);
    }
    //This function maps coming List<ResourcePlanning> to a List<ResourcePlanningTableViewModel> so UI can display it across all months of the year
    public List<ResourcePlanningTableViewModel> getResourcePlanningTableViewModels() {
        resourcePlanningTableViewModels = new ArrayList<>();
        monthsList.forEach(month -> {
            var resourcePlanningFilteredByMonth = resourcePlanningsByYearAndInitiative.stream()
                    .filter(resourcePlanning -> resourcePlanning.getDate().getMonth().getValue() == month.getMonth().getValue()).toList();

            if (!resourcePlanningFilteredByMonth.isEmpty()) {
                var resourcePlanning = resourcePlanningFilteredByMonth.get(0);
                resourcePlanningTableViewModels.add(ResourcePlanningTableViewModel.builder()
                        .month(month)
                        .monthAndSelectedInitiativeResourcePlanning(resourcePlanning)
                        .build());
            }
            else {
                resourcePlanningTableViewModels.add(ResourcePlanningTableViewModel.builder()
                        .month(month)
                        .monthAndSelectedInitiativeResourcePlanning(ResourcePlanning.builder()
                                .build())
                        .build());
            }
                });
        return resourcePlanningTableViewModels;
    }

    private List<ResourcePlanning> filterByYearAndInitiative(List<ResourcePlanning> resourcePlanning) {
        return resourcePlanning.stream()
                .filter(rp -> rp.getDate().getYear() == getYear().getValue() &&
                        rp.getInitiativeId() == getInitiative().getId()).toList();
    }
}
