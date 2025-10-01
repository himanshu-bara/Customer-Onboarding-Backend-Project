package com.oracle.testService;

import com.oracle.customer.dao.CustomerDao;
import com.oracle.customer.exception.CustomerNotFoundException;
import com.oracle.customer.model.Customer;
import com.oracle.customer.service.CustomerServiceImpl;
import com.oracle.kafka.PublishEmailNotification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private PublishEmailNotification notification;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private Customer createMockCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId("CUST001");
        customer.setFullName("Ajay Sharma");
        customer.setEmail("ajay@example.com");
        customer.setPhone("9876543210");
        customer.setDob("2000-01-01");
        customer.setAddress("123 Street Name, City");
        customer.setPan("ABCDE1234F");
        customer.setAadhaar("123456789012");
        return customer;
    }

    @Test
    void testSaveCustomer_Success() {
        Customer customer = createMockCustomer();
        customer.setCustomerId(null); // Simulate customer creation

        when(customerDao.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer saved = invocation.getArgument(0);
            saved.setCustomerId("CUST123456");
            return saved;
        });

        Customer result = customerService.saveCustomer(customer);

        assertNotNull(result.getCustomerId());
        verify(notification).sendEmailNotification(any(Customer.class));
        verify(customerDao).save(any(Customer.class));
    }

    @Test
    void testFindById_Success() {
        Customer mockCustomer = createMockCustomer();
        when(customerDao.findById("CUST001")).thenReturn(mockCustomer);

        Customer result = customerService.findById("CUST001");

        assertEquals("Ajay Sharma", result.getFullName());
        assertEquals("CUST001", result.getCustomerId());
    }

    @Test
    void testFindById_NotFound() {
        when(customerDao.findById("CUST404")).thenReturn(null);

        assertThrows(CustomerNotFoundException.class, () -> customerService.findById("CUST404"));
    }

    @Test
    void testFindAll_ReturnsList() {
        List<Customer> mockList = new ArrayList<>();
        mockList.add(createMockCustomer());

        when(customerDao.findAll()).thenReturn(mockList);

        List<Customer> result = customerService.findAll();

        assertEquals(1, result.size());
        assertEquals("Ajay Sharma", result.get(0).getFullName());
    }

    @Test
    void testUpdateCustomer_Success() {
        Customer customer = createMockCustomer();

        when(customerDao.findById("CUST001")).thenReturn(customer);
        when(customerDao.update(customer)).thenReturn(customer);

        Customer result = customerService.updateCustomer(customer);

        assertEquals("Ajay Sharma", result.getFullName());
        verify(customerDao).update(customer);
    }

    @Test
    void testUpdateCustomer_NotFound() {
        Customer customer = createMockCustomer();

        when(customerDao.findById("CUST001")).thenReturn(null);

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(customer));
    }

    @Test
    void testDeleteCustomer_Success() {
        Customer customer = createMockCustomer();

        when(customerDao.findById("CUST001")).thenReturn(customer);

        customerService.deleteCustomer("CUST001");

        verify(customerDao).delete("CUST001");
    }

    @Test
    void testDeleteCustomer_NotFound() {
        when(customerDao.findById("CUST404")).thenReturn(null);

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer("CUST404"));
    }
}
