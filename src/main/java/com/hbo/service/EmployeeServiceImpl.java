package com.hbo.service;

import com.hbo.employee.EmployeeType;
import com.hbo.employee.ObjectFactory;
import org.springframework.stereotype.Service;

/**
 * Created by kmaydeo on 9/9/2014.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final ObjectFactory objectFactory = new ObjectFactory();

    @Override
    public EmployeeType getEmployee(String id) {
        EmployeeType employee = objectFactory.createEmployeeType();
        employee.setEmployeeId(id);
        employee.setEmployeeName("Ketan Maydeo");
        return employee;
    }
}
