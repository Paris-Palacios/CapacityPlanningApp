package org.capacity.service;

import org.capacity.models.*;
import org.capacity.repository.RepositoryDAO;

import java.util.List;

import static org.capacity.utilities.Constants.ALL_OPTION;

public class UIService {
    private static UIService instance;
    private final DatabaseManager databaseManager;

    private UIService() {
        databaseManager = DatabaseManager.getInstance();
    }
    public static UIService getInstance() {
        if (instance == null) {
            instance = new UIService();
        }
        return instance;
    }

    public List<Office> getAllOffices() {
        var officeList = databaseManager.executeQuery(RepositoryDAO::getAllOffices);
        officeList.add(Office.builder()
                        .officeName(ALL_OPTION)
                .build());
        return officeList;
    }

    public List<CostCenter> getAllCostCenters() {
        var costCenterList = databaseManager.executeQuery(RepositoryDAO::getAllCostCenters);
        costCenterList.add(CostCenter.builder()
                .costCenterName(ALL_OPTION)
                .build());
        return costCenterList;
    }

    public List<Resource> getAllResources() {
        return databaseManager.executeQuery(RepositoryDAO::getAllResources);
    }

    public List<Initiative> getAllInitiatives() {
        return databaseManager.executeQuery(RepositoryDAO::getAllInitiatives);
    }

    public List<ResourcePlanning> getResourcePlanningById(int resourceId) {
        return databaseManager.executeQuery(repositoryDAO -> repositoryDAO.getResourcePlanningById(resourceId));
    }
}
