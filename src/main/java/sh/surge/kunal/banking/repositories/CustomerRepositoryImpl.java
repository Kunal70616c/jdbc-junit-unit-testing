package sh.surge.kunal.banking.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sh.surge.kunal.banking.models.Customer;
import sh.surge.kunal.banking.models.FullName;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    @Autowired
	private JdbcTemplate jdbcTemplate;

    // addCustomer is a query to add a new customer
    @Value("${addCustomer}")
    private String addCustomerQuery;
    // selectAllCustomer is a query to get all the customers
    @Value("${selectAllCustomers}")
    private String selectAllCustomersQuery;
    // selectCustomerById is a query to get a customer by id
    @Value("${selectCustomerById}")
    private String selectCustomerByIdQuery;
    // updateCustomerContactNo is a query to update a customer's contact number
    @Value("${updateCustomerContactNo}")
    private String updateCustomerQuery;
    // deleteCustomerById is a query to delete a customer by id
    @Value("${deleteCustomerById}")
    private String deleteCustomerQuery;
    @Autowired 
    ObjectProvider<Customer> customerProvider;
    @Autowired 
    ObjectProvider<FullName> fullNameProvider;
   
	
	@Override
	public boolean addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		int rows=jdbcTemplate.update(addCustomerQuery, 
				customer.getAccountNo(), customer.getFullName().getFirstName(),
				customer.getFullName().getMiddleName(),
				customer.getFullName().getLastName(), 
				customer.getEmail(), customer.getPassword(),customer.getContactNo());
		
		return rows>0;
	}

	@Override
	public Customer getCustomerById(long accountNo) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(selectCustomerByIdQuery, this::mapRowToCustomer, 
				accountNo);
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(selectAllCustomersQuery, this::mapRowToCustomer);
	}

	@Override
	public boolean updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update(updateCustomerQuery, customer.getContactNo(),
				customer.getAccountNo())>0;
	}

	@Override
	public boolean deleteCustomer(long accountNo) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update(deleteCustomerQuery, accountNo)>0;
	}
	

    // mapRowToCustomer is a method to map a row to a customer object
	private Customer mapRowToCustomer(ResultSet rs, int rowNum) throws SQLException {
		// Provider is used to get the object
        Customer customer = customerProvider.getObject();
        // FullName is used to get the object
	    FullName fullName = fullNameProvider.getObject();
		customer.setFullName(fullName);
        // Setting the values of the customer object
		customer.setAccountNo(rs.getLong("account_no"));
		customer.getFullName().setFirstName(rs.getString("first_name"));
		customer.getFullName().setMiddleName(rs.getString("middle_name"));
		customer.getFullName().setLastName(rs.getString("last_name"));
		customer.setEmail(rs.getString("email"));
		customer.setPassword(rs.getString("password"));
		customer.setContactNo(rs.getLong("contact_no"));
		return customer;
		
	}

}
