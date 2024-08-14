package org.capacity.testPackage;

import lombok.Getter;
import lombok.Setter;
import org.capacity.models.Initiative;
import org.capacity.models.Resource;
import org.capacity.models.ResourcePlanning;
import org.capacity.service.UIService;
import org.capacity.utilities.Months;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ResourcePlanningTableViewManager {
    private UIService uiService;
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
        this.uiService = UIService.getInstance();
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
                                .isEmpty(true)
                                .build())
                        .build());
            }
                });
        return resourcePlanningTableViewModels;
    }

    public void saveResourcePlannings(List<ResourcePlanningTableViewModel> resourcePlannings) {
        resourcePlannings
                .forEach(resourcePlanningTableViewModel -> {
                    ResourcePlanning resourcePlanning = resourcePlanningTableViewModel.getMonthAndSelectedInitiativeResourcePlanning();
                    if(!resourcePlanning.isEmpty()) {
                        uiService.updateResourcePlanningById(resourcePlanning);
                    }
                    else {
                        var commited = resourcePlanning.getCommited();
                        var planned = resourcePlanning.getPlanned();
                        if (commited != 0 || planned != 0) {
                            resourcePlanning.setEmpty(false);
                            uiService.createResourcePlanning(ResourcePlanning.builder()
                                    .resourceId(resource.getId())
                                    .commited(resourcePlanning.getCommited())
                                    .planned(resourcePlanning.getPlanned())
                                    .date(LocalDate.of(year.getValue(), resourcePlanningTableViewModel.getMonth().getMonth().getValue(), 1))
                                    .initiativeId(initiative.getId())
                                    .build());
                        }
                    }
                });
    }

    private List<ResourcePlanning> filterByYearAndInitiative(List<ResourcePlanning> resourcePlanning) {
        return resourcePlanning.stream()
                .filter(rp -> rp.getDate().getYear() == getYear().getValue() &&
                        rp.getInitiativeId() == getInitiative().getId()).toList();
    }
}
