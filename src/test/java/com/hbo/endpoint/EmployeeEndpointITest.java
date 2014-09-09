package com.hbo.endpoint;

import com.hbo.employee.GetEmployeeRequest;
import com.hbo.employee.GetEmployeeResponse;
import com.hbo.employee.ObjectFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by kmaydeo on 8/23/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class EmployeeEndpointITest {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Test
    public void marshalSendAndReceive() {
        ObjectFactory objectFactory = new ObjectFactory();
        GetEmployeeRequest request = objectFactory.createGetEmployeeRequest();
        request.setEmployeeId("1");
        GetEmployeeResponse response = (GetEmployeeResponse) webServiceTemplate.marshalSendAndReceive(request, new SoapActionCallback("http://localhost:8080/spring-ws-sample/GetEmployee"));

        assertThat(response.getEmployee().getEmployeeName(), is(equalTo("Ketan Maydeo")));
    }

}
