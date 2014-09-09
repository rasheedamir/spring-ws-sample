package com.hbo.endpoint;

import com.hbo.employee.EmployeeType;
import com.hbo.employee.GetEmployeeRequest;
import com.hbo.employee.GetEmployeeResponse;
import com.hbo.employee.ObjectFactory;
import com.hbo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by kmaydeo on 8/23/2014.
 */
@Endpoint
public class EmployeeEndpoint {

    private static final String NAMESPACE_URI = "http://hbo.com/employee";

    @Autowired
    private EmployeeService employeeService;

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetEmployeeRequest")
    public GetEmployeeResponse getEmployee(@RequestPayload GetEmployeeRequest request) {
        ObjectFactory objectFactory = new ObjectFactory();
        EmployeeType employee = employeeService.getEmployee(request.getEmployeeId());
        GetEmployeeResponse response = objectFactory.createGetEmployeeResponse();
        response.setEmployee(employee);
        return response;
    }
}
