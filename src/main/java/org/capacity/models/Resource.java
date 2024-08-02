package org.capacity.models;

import lombok.Data;

@Data
public class Resource {
    private int id;
    private String resourceName;
    private Office office;
    private CostCenter costCenter;
    private Position position;
    private boolean active;
}
