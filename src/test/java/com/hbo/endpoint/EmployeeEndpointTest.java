package com.hbo.endpoint;

import com.hbo.exception.MyBusinessException;
import com.hbo.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import javax.inject.Inject;
import javax.xml.transform.Source;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

/**
 * Created by kmaydeo on 9/9/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-ws-servlet.xml")
public class EmployeeEndpointTest {

    @Inject
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @Spy
    @Inject
    private EmployeeService employeeService;

    @InjectMocks
    @Inject
    private EmployeeEndpoint employeeEndpoint;

    @Before
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void success() throws Exception {
        Source requestPayload = new StringSource(
                "<emp:GetEmployeeRequest xmlns:emp=\"http://hbo.com/employee\">\n" +
                "   <emp:EmployeeId>1</emp:EmployeeId>\n" +
                "</emp:GetEmployeeRequest>");
        Source responsePayload = new StringSource(
                "<ns2:GetEmployeeResponse xmlns:ns2=\"http://hbo.com/employee\">\n" +
                "   <ns2:Employee><ns2:EmployeeId>1</ns2:EmployeeId>\n" +
                "   <ns2:EmployeeName>Ketan Maydeo</ns2:EmployeeName>\n" +
                "</ns2:Employee></ns2:GetEmployeeResponse >");

        mockClient.sendRequest(withPayload(requestPayload)).andExpect(payload(responsePayload));
    }

    @Test
    public void invalidRequest() throws Exception {
        Source requestPayload = new StringSource(
                "<emp:GetEmployeeRequest xmlns:emp=\"http://hbo.com/employee\">\n" +
                "</emp:GetEmployeeRequest>");
        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <faultcode>SOAP-ENV:Client</faultcode>\n" +
                "   <faultstring xml:lang=\"en\">Validation error</faultstring>\n" +
                "   <detail>\n" +
                "       <spring-ws:ValidationError xmlns:spring-ws=\"http://springframework.org/spring-ws\">cvc-complex-type.2.4.b: The content of element 'emp:GetEmployeeRequest' is not complete. One of '{\"http://hbo.com/employee\":EmployeeId}' is expected.</spring-ws:ValidationError>\n" +
                "   </detail>\n" +
                "</SOAP-ENV:Fault>");

        mockClient.sendRequest(withPayload(requestPayload)).andExpect(payload(responsePayload));
    }

    @Test
    public void exceptionDuringProcessing() throws Exception {
        //given
        //when(employeeService.getEmployee(anyString())).thenThrow(new MyBusinessException("oops"));
        //bdd style
        given(employeeService.getEmployee(anyString())).willThrow(new MyBusinessException("oops"));

        Source requestPayload = new StringSource(
                "<emp:GetEmployeeRequest xmlns:emp=\"http://hbo.com/employee\">\n" +
                "   <emp:EmployeeId>1</emp:EmployeeId>\n" +
                "</emp:GetEmployeeRequest>");
        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <faultcode>SOAP-ENV:Server</faultcode>\n" +
                "   <faultstring xml:lang=\"en\">oops</faultstring>\n" +
                "</SOAP-ENV:Fault>");

        //when
        mockClient
                .sendRequest(withPayload(requestPayload))
        //then
                .andExpect(payload(responsePayload));
        verify(employeeService, times(1)).getEmployee(anyString());
    }
}
