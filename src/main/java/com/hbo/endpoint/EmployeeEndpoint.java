package com.hbo.endpoint;

import com.hbo.employee.EmployeeType;
import com.hbo.employee.GetEmployeeRequest;
import com.hbo.employee.GetEmployeeResponse;
import com.hbo.employee.ObjectFactory;
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

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetEmployeeRequest")
    public GetEmployeeResponse getEmployee(@RequestPayload GetEmployeeRequest request) {
        ObjectFactory objectFactory = new ObjectFactory();
        EmployeeType employee = objectFactory.createEmployeeType();
        employee.setEmployeeId(request.getEmployeeId());
        employee.setEmployeeName("Ketan Maydeo");

        GetEmployeeResponse response = objectFactory.createGetEmployeeResponse();
        response.setEmployee(employee);
        return response;
    }
}
