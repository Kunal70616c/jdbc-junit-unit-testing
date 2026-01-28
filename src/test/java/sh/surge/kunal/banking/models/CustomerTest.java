package sh.surge.kunal.banking.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import sh.surge.kunal.banking.utils.CustomerApp;
import com.github.javafaker.Faker;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
public class CustomerTest {

    // Customer Object for Testing
	private Customer customer;
    // FullName Object for Testing
	private FullName fullName;
    // SavingsAccount Object for Testing
	private SavingsAccount savingsAccount;

    @BeforeEach // This annotation is used to run the setUp method before each test
    // setUp is a method to initialize the objects
	public void setUp() {
		customer = new Customer();
		fullName = new FullName();
		
	}

	@Nested // This annotation is used to group related tests
	class FullNameTest{
		@ParameterizedTest // Perameterized Test is a test that runs multiple times with different inputs
		@ValueSource(strings = {"John","A.","Doe"}) // @ValueSource is a provider that provides a list of values
		public void testFullName(String firstName) {
			fullName.setFirstName(firstName);			
			assertAll( // assertAll is used to run multiple assertions
					() -> assertTrue(firstName.equals(fullName.getFirstName()))
                        // assertTrue is used to check if the condition is true
					);
		}
		
	}
	@Nested // This annotation is used to group related tests
	class AccountNoTest{
		@Test // This annotation is used to run the test
		public void testAccountNo() {
			List<Long> accountNos = CustomerApp.getAllCustomers()
					  .stream().map(c->c.getAccountNo()).toList();
			assertAll( // assertAll is used to run multiple assertions
					() -> assertTrue(accountNos.stream().allMatch(no -> no >= 1000000000L 
					&& no <= 9999999999L)), // assertTrue
					() -> assertEquals(5, accountNos.size()),
					()-> assertFalse(accountNos.isEmpty())
					);
		 
		}
		
	}
	
	@Nested
	class EmailTest{
		@ParameterizedTest
		@MethodSource("sh.surge.kunal.banking.models.CustomerTest#provideCustomers")
		public void testEmail(Customer customer) {
			String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            // assertTrue is used to check if the condition is true
			assertTrue(customer.getEmail().matches(emailRegex));
		}
	}
		
	
	
	@RepeatedTest(3) // Annotation to run the test multiple times
	@DisplayName("Customer Object Not Null Test") // Display name of the test
	@Order(2) // Order is the order of the test execution
	@Tag("dev") // Tag is used to group tests
	public void testCustomerNotNull() {

        // assertNotNull is used to check if the object is not null
		assertNotNull(customer);
	}

	@ParameterizedTest	// Parameterized Test is a test that runs multiple times with different inputs
	@DisplayName("Customer Getters and Setters Test")
	@Order(1)
	@Tag("qa") // Tag is used to group tests
	@CsvFileSource(resources = "/customer.csv", numLinesToSkip = 1) // @CsvFileSource is a provider that provides a list of values from a csv file
	public void testGettersAndSetters(long accountNo,String firstName,String middleName,String lastName,
			String email, String password,long contactNo) {
		
		fullName.setFirstName(firstName);
		fullName.setMiddleName(middleName);
		fullName.setLastName(lastName);
		customer.setAccountNo(accountNo);
		customer.setFullName(fullName);		
		customer.setEmail(email);
		customer.setContactNo(contactNo);
		customer.setPassword(password);
		assertAll( // assertAll is used to run multiple assertions
                // assertEquals is used to check if the values are equal
				() -> assertEquals(accountNo, customer.getAccountNo()),
				() -> assertEquals(firstName, customer.getFullName().getFirstName()),
				() -> assertEquals(middleName, customer.getFullName().getMiddleName()),
				() -> assertEquals(lastName, customer.getFullName().getLastName()),
				() -> assertEquals(email, customer.getEmail()),
				() -> assertEquals(contactNo, customer.getContactNo()),
				() -> assertEquals(password, customer.getPassword())
				);	
		
	}
	
	@ParameterizedTest
	@ValueSource(longs = {1234567890L, 9876543210L, 5555555555L})
	@Tag("qa")
	public void TestGettersAndSettersNegative(long contactNo) {
		customer.setContactNo(contactNo);
		assertNotEquals(1111111111L, customer.getContactNo());
	}
	@Test
	@DisplayName("Savings Account Exception Test")
	@Order(3)
	@Tag("dev")
	public void testSavingsAccountExceptionNegative() {
		
		assertThrows(NullPointerException.class, ()->savingsAccount.getRoi());
	}
	@AfterEach // @AfterEach is used to run the tearDown method after each test
	public void tearDown() {

        customer = null;
	}

	
	public static Stream<Arguments> provideCustomers() {
		List<Arguments> customerStream = new ArrayList<>();
		for(int i=1;i<=5;i++) {
			Customer customer=new Customer();
			FullName fullName=new FullName();
			Faker faker = new Faker();
			customer.setAccountNo(faker.number().numberBetween(1000000000L, 9999999999L));
			customer.setFullName(fullName);
			customer.getFullName().setFirstName(faker.name().firstName());
			customer.getFullName().setMiddleName(faker.name().nameWithMiddle());
			customer.getFullName().setLastName(faker.name().lastName());
			customer.setEmail(faker.internet().emailAddress());
			customer.setContactNo(Long.parseLong(faker.phoneNumber().subscriberNumber(10)));
			customer.setPassword(faker.internet().password(8, 10, true, true, true));
			customerStream.add(Arguments.of(customer));
		}
		return customerStream.stream();
	}
}
