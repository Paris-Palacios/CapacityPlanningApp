package org.capacity.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resource {
    private int id;
    private String resourceName;
    private Office office;
    private CostCenter costCenter;
    private Position position;
    private boolean active;
}
