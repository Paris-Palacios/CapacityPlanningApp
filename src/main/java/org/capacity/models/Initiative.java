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
public class Initiative {
    private int id;
    private Office office;
    private CostCenter costCenter;
    private Role role;
    private Customer customer;
    private Project project;
    private LocalDate beginDate;
    private LocalDate endDate;

    public String getCustomerProjectName() {
        return customer.getCustomerName() +
                " - " + project.getProjectName();
    }
}
