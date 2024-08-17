package org.capacity.service;

import lombok.Getter;
import lombok.Setter;
import org.capacity.models.*;
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
    private List<ResourcePlanning> resourcePlannings;
    private List<Months> monthsList = Arrays.stream(Months.values()).toList();
    private List<ResourcePlanningTableViewModel> resourcePlanningTableViewModels;

    public ResourcePlanningTableViewManager(Resource resource, Initiative initiative, Year year, List<ResourcePlanning> resourcePlanning) {
        this.initiative = initiative;
        this.year = year;
        this.resource = resource;
        this.resourcePlannings = resourcePlanning;
        this.uiService = UIService.getInstance();
    }
    //List<ResourcePlanningTableViewModel> will be used as the model that UI would display
    public List<ResourcePlanningTableViewModel> getResourcePlanningTableViewModels() {
        resourcePlanningTableViewModels = new ArrayList<>();
        monthsList.forEach(month -> {
            var resourcePlanningFilteredByInitiative = filterByYearAndMonth(resourcePlannings, month)
                    .stream().filter(rp -> rp.getInitiativeId() == getInitiative().getId()).toList();

            var resourcePlanningFilteredByOtherProjects = filterByYearAndMonth(resourcePlannings, month)
                    .stream().filter(rp -> rp.getInitiativeId() != getInitiative().getId()).toList();

            float sumOfCommittedOtherProjects = resourcePlanningFilteredByOtherProjects
                    .stream().map(ResourcePlanning::getCommited).reduce(Float::sum).orElse(0f);
            float sumOfPlannedOtherProjects = resourcePlanningFilteredByOtherProjects
                    .stream().map(ResourcePlanning::getPlanned).reduce(Float::sum).orElse(0f);

            if (!resourcePlanningFilteredByInitiative.isEmpty()) {
                var resourcePlanning = resourcePlanningFilteredByInitiative.get(0);
                resourcePlanningTableViewModels.add(ResourcePlanningTableViewModel.builder()
                        .month(month)
                        .monthAndSelectedInitiativeResourcePlanning(resourcePlanning)
                        .otherProjects(OtherProjects.builder()
                                .sumOfCommited(sumOfCommittedOtherProjects)
                                .sumOfPlanned(sumOfPlannedOtherProjects)
                                .build())
                        .build());
            }
            else {
                resourcePlanningTableViewModels.add(ResourcePlanningTableViewModel.builder()
                        .month(month)
                        .monthAndSelectedInitiativeResourcePlanning(ResourcePlanning.builder()
                                .isEmpty(true)
                                .build())
                        .otherProjects(OtherProjects.builder()
                                .sumOfCommited(sumOfCommittedOtherProjects)
                                .sumOfPlanned(sumOfPlannedOtherProjects)
                                .build())
                        .build());
            }
                });

        return resourcePlanningTableViewModels;
    }

    public void saveResourcePlannings(List<ResourcePlanningTableViewModel> resourcePlannings) throws Exception {
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

    private List<ResourcePlanning> filterByYearAndMonth(List<ResourcePlanning> resourcePlanning, Months month) {
        return resourcePlanning.stream()
                .filter(rp -> rp.getDate().getYear() == getYear().getValue() &&
                        rp.getDate().getMonth().getValue() == month.getMonth().getValue()
                )
                .toList();
    }
}
