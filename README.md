# JUnit Unit Testing for Spring JDBC Applications

A comprehensive guide to implementing unit tests using JUnit 5 with parameterized tests, nested tests, test suites, and code coverage analysis for a Banking Application.

## Repository
**This Repository:** [GitHub - JDBC JUnit Unit Testing](https://github.com/Kunal70616c/jdbc-junit-unit-testing.git)

**Base Application:** [GitHub - Spring JDBC Data Access](https://github.com/Kunal70616c/spring-jdbc-data-access.git)

> **Note:** This project builds upon the Spring JDBC Data Access project by adding comprehensive JUnit 5 unit tests. All the application code (models, repositories, services) remains the same - we're focusing purely on testing strategies.

## Table of Contents
- [What is Unit Testing?](#what-is-unit-testing)
- [Why Unit Testing Matters](#why-unit-testing-matters)
- [JUnit 5 Overview](#junit-5-overview)
- [Project Structure](#project-structure)
- [JUnit 5 Annotations](#junit-5-annotations)
- [Test Implementation](#test-implementation)
- [Parameterized Tests](#parameterized-tests)
- [Test Suites](#test-suites)
- [Code Coverage with JaCoCo](#code-coverage-with-jacoco)
- [Running Tests](#running-tests)
- [Best Practices](#best-practices)

---

## What is Unit Testing?

**Unit Testing** is a software testing method where individual units or components of code are tested in isolation to verify that they work as expected. A "unit" is typically the smallest testable part of an application, such as a method, class, or module.

### Key Characteristics

1. **Isolated**: Tests run independently without dependencies on external systems (databases, APIs, file systems)
2. **Automated**: Tests run automatically without manual intervention
3. **Fast**: Unit tests should execute quickly (milliseconds)
4. **Repeatable**: Same input always produces same output
5. **Self-Validating**: Tests clearly pass or fail without manual inspection

### Unit Test Structure (AAA Pattern)

```java
@Test
public void testMethodName() {
    // Arrange - Set up test data and preconditions
    Customer customer = new Customer();
    customer.setAccountNo(1234567890L);
    
    // Act - Execute the method being tested
    long accountNo = customer.getAccountNo();
    
    // Assert - Verify the expected outcome
    assertEquals(1234567890L, accountNo);
}
```

---

## Why Unit Testing Matters

### 1. **Catches Bugs Early**
Finding bugs during development is exponentially cheaper than finding them in production.

**Cost Multiplier:**
- Development: 1x
- Integration Testing: 10x
- Production: 100x+

### 2. **Documentation**
Tests serve as executable documentation showing how code should be used.

```java
@Test
public void testEmailValidation() {
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    assertTrue(customer.getEmail().matches(emailRegex));
    // This test documents that emails must match this format
}
```

### 3. **Refactoring Confidence**
Tests provide a safety net when refactoring code. If tests pass after changes, you haven't broken existing functionality.

### 4. **Design Feedback**
Difficult-to-test code often indicates poor design. Writing tests forces you to write more modular, maintainable code.

### 5. **Regression Prevention**
Tests ensure that new code doesn't break existing functionality.

### 6. **Faster Development**
While writing tests takes time initially, it saves time by catching bugs early and reducing debugging time.

---

## JUnit 5 Overview

**JUnit 5** (also known as JUnit Jupiter) is the latest version of the most widely-used testing framework for Java. It was completely rewritten to be more modular, extensible, and modern.

### JUnit 5 Architecture

```
JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage
```

1. **JUnit Platform**: Foundation for launching testing frameworks on the JVM
2. **JUnit Jupiter**: New programming model and extension model for writing tests
3. **JUnit Vintage**: Support for running JUnit 3 and JUnit 4 tests

### JUnit 4 vs JUnit 5

| Feature | JUnit 4 | JUnit 5 |
|---------|---------|---------|
| Package | `org.junit` | `org.junit.jupiter.api` |
| @Before | `@Before` | `@BeforeEach` |
| @After | `@After` | `@AfterEach` |
| @BeforeClass | `@BeforeClass` | `@BeforeAll` |
| @AfterClass | `@AfterClass` | `@AfterAll` |
| @Ignore | `@Ignore` | `@Disabled` |
| Assertions | Static imports required | Built-in |
| Nested Tests | Not supported | `@Nested` |
| Parameterized | Separate runner | `@ParameterizedTest` |
| Display Names | Method name only | `@DisplayName` |
| Tags | `@Category` | `@Tag` |

---

## Project Structure

```
jdbc-junit-unit-testing/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── sh.surge.kunal.banking/
│   │   │       ├── configurations/
│   │   │       │   └── AppConfig.java
│   │   │       ├── models/
│   │   │       │   ├── Customer.java
│   │   │       │   ├── FullName.java
│   │   │       │   ├── Account.java
│   │   │       │   ├── CurrentAccount.java
│   │   │       │   └── SavingsAccount.java
│   │   │       ├── repositories/
│   │   │       │   ├── CustomerRepository.java
│   │   │       │   └── CustomerRepositoryImpl.java
│   │   │       ├── services/
│   │   │       │   └── CustomerService.java
│   │   │       └── utils/
│   │   │           └── CustomerApp.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/
│       │   └── sh.surge.kunal.banking/
│       │       ├── models/
│       │       │   └── CustomerTest.java          # Main test class
│       │       └── suites/
│       │           └── CustomerTestSuite.java     # Test suite
│       └── resources/
│           └── customer.csv                        # Test data
├── pom.xml                                         # Maven dependencies
└── README.md
```

---

## JUnit 5 Annotations

### Test Lifecycle Annotations

#### @BeforeEach
Runs before each test method. Used to initialize test data.

```java
@BeforeEach
public void setUp() {
    customer = new Customer();
    fullName = new FullName();
}
```

**Use Cases:**
- Initialize test objects
- Set up mock objects
- Prepare test database state
- Reset counters or flags

#### @AfterEach
Runs after each test method. Used for cleanup.

```java
@AfterEach
public void tearDown() {
    customer = null;
}
```

**Use Cases:**
- Clean up resources
- Close connections
- Reset state
- Delete temporary files

#### @BeforeAll
Runs once before all tests in the class. Must be static.

```java
@BeforeAll
public static void setUpAll() {
    // One-time setup for all tests
}
```

#### @AfterAll
Runs once after all tests in the class. Must be static.

```java
@AfterAll
public static void tearDownAll() {
    // One-time cleanup for all tests
}
```

---

### Test Definition Annotations

#### @Test
Marks a method as a test method.

```java
@Test
public void testCustomerNotNull() {
    assertNotNull(customer);
}
```

#### @DisplayName
Provides a custom display name for test methods.

```java
@DisplayName("Customer Object Not Null Test")
@Test
public void testCustomerNotNull() {
    assertNotNull(customer);
}
```

**Output:**
```
✓ Customer Object Not Null Test
```

Instead of:
```
✓ testCustomerNotNull
```

#### @Disabled
Disables a test method or class.

```java
@Disabled("Not implemented yet")
@Test
public void testFeature() {
    // Test temporarily disabled
}
```

---

### Parameterized Test Annotations

#### @ParameterizedTest
Indicates that a method is a parameterized test.

```java
@ParameterizedTest
@ValueSource(strings = {"John", "Jane", "Bob"})
public void testNames(String name) {
    assertNotNull(name);
}
```

---

### Organizational Annotations

#### @Nested
Groups related tests together in a nested class.

```java
@Nested
class EmailTest {
    @Test
    public void testEmailFormat() {
        // Email-specific test
    }
}
```

#### @Tag
Tags tests for selective execution.

```java
@Tag("dev")
@Test
public void testDevelopmentFeature() {
    // Only runs in dev environment
}
```

#### @Order
Controls test execution order.

```java
@Order(1)
@Test
public void firstTest() {
    // Runs first
}

@Order(2)
@Test
public void secondTest() {
    // Runs second
}
```

---

### Repeated Test Annotations

#### @RepeatedTest
Repeats a test multiple times.

```java
@RepeatedTest(3)
public void testRepeated() {
    // Runs 3 times
}
```

---

## Test Implementation

### Complete CustomerTest Class

Let's break down the comprehensive test implementation:

```java
public class CustomerTest {
    private Customer customer;
    private FullName fullName;
    private SavingsAccount savingsAccount;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        fullName = new FullName();
    }
```

**Lifecycle Setup:**
- Creates fresh instances before each test
- Ensures test isolation
- Prevents test interference

---

### 1. Nested Tests - FullNameTest

```java
@Nested
class FullNameTest {
    @ParameterizedTest
    @ValueSource(strings = {"John","A.","Doe"})
    public void testFullName(String firstName) {
        fullName.setFirstName(firstName);
        assertAll(
            () -> assertTrue(firstName.equals(fullName.getFirstName()))
        );
    }
}
```

**What This Tests:**
- ✅ Setter and getter work correctly
- ✅ String equality for first names
- ✅ Multiple values tested with minimal code

**@ValueSource Benefits:**
- Simple parameterization for primitive types
- No external files needed
- Clear and concise

**assertAll() Benefits:**
- Executes all assertions even if one fails
- Provides comprehensive failure information
- Better debugging experience

---

### 2. Nested Tests - AccountNoTest

```java
@Nested
class AccountNoTest {
    @Test
    public void testAccountNo() {
        List<Long> accountNos = CustomerApp.getAllCustomers()
            .stream()
            .map(c -> c.getAccountNo())
            .toList();
        
        assertAll(
            () -> assertTrue(accountNos.stream()
                .allMatch(no -> no >= 1000000000L && no <= 9999999999L)),
            () -> assertEquals(5, accountNos.size()),
            () -> assertFalse(accountNos.isEmpty())
        );
    }
}
```

**What This Tests:**
- ✅ All account numbers are 10 digits
- ✅ Expected number of customers (5)
- ✅ List is not empty

**Stream API Usage:**
- Modern, functional approach
- Easy data transformation
- Readable test logic

**Multiple Assertions:**
- Tests multiple conditions
- All assertions execute
- Comprehensive validation

---

### 3. Nested Tests - EmailTest with Method Source

```java
@Nested
class EmailTest {
    @ParameterizedTest
    @MethodSource("sh.surge.kunal.banking.models.CustomerTest#provideCustomers")
    public void testEmail(Customer customer) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        assertTrue(customer.getEmail().matches(emailRegex));
    }
}

public static Stream<Arguments> provideCustomers() {
    List<Arguments> customerStream = new ArrayList<>();
    for(int i = 1; i <= 5; i++) {
        Customer customer = new Customer();
        FullName fullName = new FullName();
        Faker faker = new Faker();
        
        customer.setAccountNo(faker.number().numberBetween(1000000000L, 9999999999L));
        customer.setFullName(fullName);
        customer.getFullName().setFirstName(faker.name().firstName());
        customer.getFullName().setMiddleName(faker.name().nameWithMiddle());
        customer.getFullName().setLastName(faker.name().lastName());
        customer.getEmail(faker.internet().emailAddress());
        customer.setContactNo(Long.parseLong(faker.phoneNumber().subscriberNumber(10)));
        customer.setPassword(faker.internet().password(8, 10, true, true, true));
        
        customerStream.add(Arguments.of(customer));
    }
    return customerStream.stream();
}
```

**Method Source Benefits:**
- ✅ Complex object creation
- ✅ Dynamic test data generation
- ✅ Reusable test data provider
- ✅ Realistic test scenarios with Faker

**Email Validation:**
- Standard email regex pattern
- Validates format, not existence
- Prevents invalid email storage

**Faker Integration:**
- Generates realistic test data
- No hardcoded values
- Different data each run

---

### 4. Repeated Test

```java
@RepeatedTest(3)
@DisplayName("Customer Object Not Null Test")
@Order(2)
@Tag("dev")
public void testCustomerNotNull() {
    assertNotNull(customer);
}
```

**Why Repeat Tests?**
- Catch intermittent failures
- Test thread safety
- Validate random data handling
- Performance testing

**Tags Usage:**
- `@Tag("dev")`: Runs in development
- `@Tag("qa")`: Runs in QA environment
- `@Tag("integration")`: Integration tests
- Selective test execution

---

### 5. CSV File Source Test

```java
@ParameterizedTest
@DisplayName("Customer Getters and Setters Test")
@Order(1)
@Tag("qa")
@CsvFileSource(resources = "/customer.csv", numLinesToSkip = 1)
public void testGettersAndSetters(
    long accountNo,
    String firstName,
    String middleName,
    String lastName,
    String email,
    String password,
    long contactNo
) {
    fullName.setFirstName(firstName);
    fullName.setMiddleName(middleName);
    fullName.setLastName(lastName);
    customer.setAccountNo(accountNo);
    customer.setFullName(fullName);
    customer.setEmail(email);
    customer.setContactNo(contactNo);
    customer.setPassword(password);
    
    assertAll(
        () -> assertEquals(accountNo, customer.getAccountNo()),
        () -> assertEquals(firstName, customer.getFullName().getFirstName()),
        () -> assertEquals(middleName, customer.getFullName().getMiddleName()),
        () -> assertEquals(lastName, customer.getFullName().getLastName()),
        () -> assertEquals(email, customer.getEmail()),
        () -> assertEquals(contactNo, customer.getContactNo()),
        () -> assertEquals(password, customer.getPassword())
    );
}
```

**CSV File Structure:**
```csv
AccountNo,FirstName,MiddleName,LastName,Email,Password,ContactNo
1001,Aria,Marie,Johnson,aria.johnson@example.com,Aria@123,9876543210
1002,Liam,James,Smith,liam.smith@example.com,Liam@123,9876543211
```

**CSV Source Benefits:**
- ✅ External test data management
- ✅ Easy to add/modify test cases
- ✅ Non-technical users can edit
- ✅ Version control friendly
- ✅ Multiple test scenarios

**numLinesToSkip = 1:**
- Skips header row
- Processes data rows only

---

### 6. Negative Test with ValueSource

```java
@ParameterizedTest
@ValueSource(longs = {1234567890L, 9876543210L, 5555555555L})
@Tag("qa")
public void TestGettersAndSettersNegative(long contactNo) {
    customer.setContactNo(contactNo);
    assertNotEquals(1111111111L, customer.getContactNo());
}
```

**Negative Testing:**
- Tests what should NOT happen
- Validates boundaries
- Ensures invalid inputs are rejected

---

### 7. Exception Testing

```java
@Test
@DisplayName("Savings Account Exception Test")
@Order(3)
@Tag("dev")
public void testSavingsAccountExceptionNegative() {
    assertThrows(NullPointerException.class, () -> savingsAccount.getRoi());
}
```

**Exception Testing Benefits:**
- ✅ Verifies expected failures
- ✅ Tests error handling
- ✅ Validates exceptional paths

**assertThrows:**
- Expects specific exception type
- Test fails if exception not thrown
- Test fails if wrong exception thrown

---

### 8. Cleanup

```java
@AfterEach
public void tearDown() {
    customer = null;
}
```

**Why Cleanup?**
- Prevents memory leaks
- Ensures test isolation
- Releases resources
- Good practice even if JVM handles it

---

## Parameterized Tests

Parameterized tests allow you to run the same test with different inputs, reducing code duplication and improving test coverage.

### 1. @ValueSource

**Best For:** Single primitive or String values

```java
@ParameterizedTest
@ValueSource(strings = {"John", "Jane", "Bob"})
public void testNames(String name) {
    assertNotNull(name);
    assertTrue(name.length() > 0);
}
```

**Supported Types:**
- `strings`: String values
- `ints`: Integer values
- `longs`: Long values
- `doubles`: Double values
- `booleans`: Boolean values

**Example:**
```java
@ValueSource(ints = {1, 2, 3, 5, 8, 13})
public void testFibonacci(int number) {
    assertTrue(isFibonacci(number));
}
```

---

### 2. @CsvSource

**Best For:** Multiple inline parameters

```java
@ParameterizedTest
@CsvSource({
    "1001, John, Doe",
    "1002, Jane, Smith",
    "1003, Bob, Johnson"
})
public void testCustomerData(long id, String firstName, String lastName) {
    assertTrue(id > 1000);
    assertNotNull(firstName);
    assertNotNull(lastName);
}
```

**Benefits:**
- Multiple parameters per test
- Inline test data
- Good for small datasets

---

### 3. @CsvFileSource

**Best For:** Large datasets or external test data

```java
@ParameterizedTest
@CsvFileSource(resources = "/customer.csv", numLinesToSkip = 1)
public void testFromCsv(long accountNo, String firstName, String lastName) {
    // Test logic
}
```

**Benefits:**
- Separates test data from test logic
- Easy to maintain large datasets
- Non-developers can update test data
- Version control friendly

**CSV File Location:**
- Place in `src/test/resources/`
- Reference with `/filename.csv`
- Must have matching parameter count

---

### 4. @MethodSource

**Best For:** Complex objects or dynamic test data

```java
@ParameterizedTest
@MethodSource("provideCustomers")
public void testCustomer(Customer customer) {
    assertNotNull(customer.getEmail());
    assertTrue(customer.getAccountNo() > 0);
}

static Stream<Arguments> provideCustomers() {
    return Stream.of(
        Arguments.of(new Customer(1001, "John", "john@email.com")),
        Arguments.of(new Customer(1002, "Jane", "jane@email.com"))
    );
}
```

**Benefits:**
- Full control over test data
- Complex object creation
- Dynamic data generation
- Can use Faker or other libraries

**Method Requirements:**
- Must be `static`
- Must return `Stream<Arguments>`
- Can be in same class or external class

---

### 5. @EnumSource

**Best For:** Testing all enum values

```java
enum AccountType {
    SAVINGS, CURRENT, FIXED_DEPOSIT
}

@ParameterizedTest
@EnumSource(AccountType.class)
public void testAccountTypes(AccountType type) {
    assertNotNull(type);
}
```

---

## Test Suites

Test Suites allow you to group and run related tests together.

### CustomerTestSuite

```java
@SelectPackages("sh.surge.kunal.banking.models")
@IncludeTags("qa")
@Suite
public class CustomerTestSuite {
}
```

**What This Does:**
- Runs all tests in `sh.surge.kunal.banking.models` package
- Only includes tests tagged with `@Tag("qa")`
- Groups related tests for organized execution

---

### Suite Annotations

#### @SelectPackages
```java
@SelectPackages({"com.example.tests", "com.example.integration"})
```
Selects tests from specified packages.

#### @SelectClasses
```java
@SelectClasses({CustomerTest.class, AccountTest.class})
```
Selects specific test classes.

#### @IncludeTags
```java
@IncludeTags({"fast", "unit"})
```
Includes tests with specified tags.

#### @ExcludeTags
```java
@ExcludeTags({"slow", "integration"})
```
Excludes tests with specified tags.

---

### Common Test Suite Patterns

#### Fast Tests Suite
```java
@SelectPackages("com.example")
@IncludeTags("fast")
@ExcludeTags("slow")
@Suite
public class FastTestSuite {
}
```

#### Integration Tests Suite
```java
@SelectPackages("com.example")
@IncludeTags("integration")
@Suite
public class IntegrationTestSuite {
}
```

#### Smoke Tests Suite
```java
@SelectPackages("com.example")
@IncludeTags("smoke")
@Suite
public class SmokeTestSuite {
}
```

---

## Code Coverage with JaCoCo

**JaCoCo** (Java Code Coverage) is a free code coverage library for Java that measures how much of your code is executed during tests.

### What is Code Coverage?

Code coverage measures the percentage of code executed by tests:

- **Line Coverage**: Percentage of lines executed
- **Branch Coverage**: Percentage of decision branches executed
- **Method Coverage**: Percentage of methods executed
- **Class Coverage**: Percentage of classes executed

### JaCoCo Maven Configuration

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.12</version>
    <executions>
        <execution>
            <id>jacoco-initialize</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>jacoco-site</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
            <configuration>
                <excludes>
                    <!-- Exclude specific packages if needed -->
                </excludes>
                <includes>
                    <include>sh/surge/kunal/banking/models/**</include>
                    <include>**/*</include>
                </includes>
            </configuration>
        </execution>
    </executions>
</plugin>
```

---

### Generating Coverage Report

```bash
# Run tests and generate coverage report
mvn clean test jacoco:report

# View report
open target/site/jacoco/index.html
```

---

### Understanding Coverage Reports

JaCoCo generates an HTML report showing:

1. **Overall Coverage Summary**
   - Total instructions covered
   - Branch coverage percentage
   - Line coverage percentage
   - Method coverage percentage
   - Class coverage percentage

2. **Per-Package Breakdown**
   - Coverage metrics for each package
   - Drill down to class level
   - Drill down to method level

3. **Color Coding**
   - 🟢 **Green**: Fully covered
   - 🟡 **Yellow**: Partially covered
   - 🔴 **Red**: Not covered

---

### Coverage Metrics Example

```
Class: Customer
├── Line Coverage: 85%
├── Branch Coverage: 75%
├── Method Coverage: 90%
└── Complexity: 15

Methods:
├── setAccountNo()      100% ✓
├── getAccountNo()      100% ✓
├── setEmail()          100% ✓
├── getEmail()          100% ✓
└── validateEmail()      60% ⚠️
    ├── Line 45: Covered
    ├── Line 46: Not covered
    └── Line 47: Partially covered
```

---

### Coverage Best Practices

1. **Aim for 80%+ Coverage**
   - Not 100% - diminishing returns
   - Focus on critical business logic
   - Don't test getters/setters obsessively

2. **Focus on Branch Coverage**
   - More important than line coverage
   - Ensures all decision paths tested
   - Catches edge cases

3. **Exclude Non-Testable Code**
   - Main methods
   - Configuration classes
   - Generated code

4. **Coverage ≠ Quality**
   - High coverage doesn't guarantee good tests
   - Write meaningful assertions
   - Test behavior, not implementation

---

## Running Tests

### Maven Commands

#### Run All Tests
```bash
mvn test
```

#### Run Specific Test Class
```bash
mvn -Dtest=CustomerTest test
```

#### Run Specific Test Method
```bash
mvn -Dtest=CustomerTest#testCustomerNotNull test
```

#### Run Tests with Tag
```bash
mvn test -Dgroups="qa"
```

#### Run Tests Excluding Tag
```bash
mvn test -DexcludedGroups="slow"
```

#### Run Test Suite
```bash
mvn -Dtest=CustomerTestSuite test
```

#### Run Tests with Coverage
```bash
mvn clean test jacoco:report
```

#### Skip Tests
```bash
mvn install -DskipTests
```

---

### IDE Test Execution

#### IntelliJ IDEA
1. Right-click on test class → Run 'CustomerTest'
2. Right-click on test method → Run 'testCustomerNotNull()'
3. Right-click on package → Run Tests in 'models'
4. View coverage: Run → Run with Coverage

#### Eclipse
1. Right-click on test class → Run As → JUnit Test
2. Right-click on project → Coverage As → JUnit Test

#### VS Code
1. Install Java Test Runner extension
2. Click play button next to test method
3. View test results in Test Explorer

---

### Maven Surefire Plugin

The Surefire plugin executes tests during the `test` phase:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M4</version>
    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>6.0.2</version>
        </dependency>
    </dependencies>
    <configuration>
        <testSourceDirectory>src/test/java</testSourceDirectory>
        <trimStackTrace>false</trimStackTrace>
    </configuration>
</plugin>
```

**Configuration Options:**
- `trimStackTrace`: Show full stack traces
- `testSourceDirectory`: Location of test files
- `includes/excludes`: Filter test classes
- `parallel`: Run tests in parallel

---

### Surefire Reports

After running tests, view reports:

```bash
# Generate HTML report
mvn surefire-report:report

# View report
open target/site/surefire-report.html
```

**Report Contents:**
- Total tests run
- Failures and errors
- Skipped tests
- Execution time
- Detailed failure messages

---

## JUnit 5 Assertions

### Basic Assertions

```java
// Equality
assertEquals(expected, actual);
assertEquals(expected, actual, "Error message");

// Boolean
assertTrue(condition);
assertFalse(condition);

// Null checks
assertNotNull(object);
assertNull(object);

// Same reference
assertSame(expected, actual);
assertNotSame(expected, actual);

// Array equality
assertArrayEquals(expectedArray, actualArray);
```

---

### Advanced Assertions

#### assertAll
Executes all assertions even if some fail:

```java
assertAll(
    () -> assertEquals(1001, customer.getAccountNo()),
    () -> assertEquals("John", customer.getFullName().getFirstName()),
    () -> assertNotNull(customer.getEmail())
);
```

**Output if multiple fail:**
```
Multiple Failures (2 failures)
    expected: <1001> but was: <1002>
    expected: <John> but was: <Jane>
```

#### assertThrows
Verifies exception is thrown:

```java
Exception exception = assertThrows(
    IllegalArgumentException.class,
    () -> customer.setAccountNo(-1)
);

assertEquals("Account number must be positive", exception.getMessage());
```

#### assertTimeout
Ensures execution completes within time:

```java
assertTimeout(Duration.ofSeconds(2), () -> {
    // Code that should complete within 2 seconds
    customerRepository.getAllCustomers();
});
```

#### assertTimeoutPreemptively
Aborts if timeout exceeded:

```java
assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
    // Aborted if exceeds 2 seconds
    longRunningOperation();
});
```

---

## Best Practices

### 1. Follow Naming Conventions

```java
// Good
@Test
public void testGetCustomerById_ValidId_ReturnsCustomer()

@Test
public void testAddCustomer_NullEmail_ThrowsException()

// Bad
@Test
public void test1()

@Test
public void testStuff()
```

**Pattern:** `test[MethodName]_[Condition]_[ExpectedResult]`

---

### 2. One Assertion Per Test (When Possible)

```java
// Good - Tests one thing
@Test
public void testEmailFormat() {
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    assertTrue(customer.getEmail().matches(emailRegex));
}

// Acceptable - Related assertions with assertAll
@Test
public void testCustomerCreation() {
    assertAll(
        () -> assertNotNull(customer.getAccountNo()),
        () -> assertNotNull(customer.getEmail())
    );
}
```

---

### 3. Test Behavior, Not Implementation

```java
// Good - Tests behavior
@Test
public void testCustomerCanBeCreated() {
    Customer customer = new Customer();
    customer.setEmail("test@example.com");
    assertEquals("test@example.com", customer.getEmail());
}

// Bad - Tests implementation details
@Test
public void testCustomerUsesHashMapInternally() {
    // Don't test internal data structures
}
```

---

### 4. Use Descriptive Test Names

```java
// Good
@DisplayName("Customer with valid email should pass validation")
@Test
public void testValidEmailValidation()

// Even Better
@DisplayName("Given valid email format, when validating customer, then validation passes")
@Test
public void givenValidEmail_whenValidating_thenValidationPasses()
```

---

### 5. Keep Tests Independent

```java
// Bad - Tests depend on each other
static Customer sharedCustomer;

@Test
public void test1() {
    sharedCustomer = new Customer();
}

@Test
public void test2() {
    // Depends on test1 running first
    assertNotNull(sharedCustomer);
}

// Good - Each test is independent
@BeforeEach
public void setUp() {
    customer = new Customer();
}
```

---

### 6. Use Test Data Builders

```java
// Good - Reusable test data builder
public class CustomerTestBuilder {
    private Customer customer = new Customer();
    
    public CustomerTestBuilder withAccountNo(long accountNo) {
        customer.setAccountNo(accountNo);
        return this;
    }
    
    public CustomerTestBuilder withEmail(String email) {
        customer.setEmail(email);
        return this;
    }
    
    public Customer build() {
        return customer;
    }
}

// Usage
@Test
public void testCustomer() {
    Customer customer = new CustomerTestBuilder()
        .withAccountNo(1001)
        .withEmail("test@example.com")
        .build();
}
```

---

### 7. Test Edge Cases

```java
@ParameterizedTest
@ValueSource(longs = {
    0L,                    // Minimum
    9999999999L,          // Maximum
    Long.MIN_VALUE,       // Edge case
    Long.MAX_VALUE        // Edge case
})
public void testAccountNumberBoundaries(long accountNo) {
    customer.setAccountNo(accountNo);
    assertEquals(accountNo, customer.getAccountNo());
}
```

---

### 8. Use Faker for Realistic Data

```java
@Test
public void testCustomerWithRealisticData() {
    Faker faker = new Faker();
    
    customer.setAccountNo(faker.number().numberBetween(1000000000L, 9999999999L));
    customer.setEmail(faker.internet().emailAddress());
    customer.setContactNo(Long.parseLong(faker.phoneNumber().subscriberNumber(10)));
    
    // More realistic than hardcoded test data
}
```

---

### 9. Organize Tests with @Nested

```java
class CustomerTest {
    
    @Nested
    @DisplayName("Account Number Tests")
    class AccountNumberTests {
        @Test
        public void testValidAccountNumber() { }
        
        @Test
        public void testInvalidAccountNumber() { }
    }
    
    @Nested
    @DisplayName("Email Tests")
    class EmailTests {
        @Test
        public void testValidEmail() { }
        
        @Test
        public void testInvalidEmail() { }
    }
}
```

---

### 10. Use Tags for Categorization

```java
@Tag("fast")
@Tag("unit")
@Test
public void testQuickOperation() { }

@Tag("slow")
@Tag("integration")
@Test
public void testDatabaseOperation() { }

// Run only fast tests
mvn test -Dgroups="fast"

// Run everything except slow tests
mvn test -DexcludedGroups="slow"
```

---

## Common Testing Anti-Patterns

### ❌ Don't: Test Private Methods

```java
// Bad
@Test
public void testPrivateMethod() {
    // Using reflection to test private method
}

// Good - Test through public API
@Test
public void testPublicMethodThatUsesPrivateMethod() {
    // Tests private method indirectly
}
```

---

### ❌ Don't: Ignore Failing Tests

```java
// Bad
@Disabled("Fails sometimes, will fix later")
@Test
public void testFlaky() { }

// Good - Fix or delete the test
@Test
public void testStable() {
    // Reliable test
}
```

---

### ❌ Don't: Have Tests with No Assertions

```java
// Bad
@Test
public void testSomething() {
    customer.setEmail("test@example.com");
    // No assertion - test always passes!
}

// Good
@Test
public void testEmailSetter() {
    customer.setEmail("test@example.com");
    assertEquals("test@example.com", customer.getEmail());
}
```

---

### ❌ Don't: Overuse Mocking

```java
// Bad - Mocking everything
@Test
public void testWithTooManyMocks() {
    Customer mockCustomer = mock(Customer.class);
    FullName mockName = mock(FullName.class);
    // Testing nothing real
}

// Good - Use real objects when possible
@Test
public void testWithRealObjects() {
    Customer customer = new Customer();
    FullName fullName = new FullName();
    // Testing actual behavior
}
```

---

## Test Coverage Goals

### Recommended Coverage Targets

| Component | Target Coverage | Priority |
|-----------|----------------|----------|
| Business Logic | 80-90% | High |
| Models/Entities | 70-80% | Medium |
| Repositories | 75-85% | High |
| Services | 80-90% | High |
| Controllers | 60-70% | Medium |
| Configuration | 20-30% | Low |
| Utils | 70-80% | Medium |

---

### What to Test

✅ **High Priority:**
- Business logic
- Validation rules
- Data transformations
- Edge cases
- Error handling
- Security-critical code

⚠️ **Medium Priority:**
- Getters and setters (with complex logic)
- Constructors
- Utility methods
- Helper functions

❌ **Low Priority (Skip):**
- Simple getters/setters
- Configuration classes
- Generated code
- Framework code
- Main methods

---

## Installation & Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)
- MySQL 8.0+ (for the base application)

### Clone and Run Tests

```bash
# Clone the repository
git clone https://github.com/Kunal70616c/jdbc-junit-unit-testing.git

# Navigate to project directory
cd jdbc-junit-unit-testing

# Run all tests
mvn test

# Run tests with coverage
mvn clean test jacoco:report

# View coverage report
open target/site/jacoco/index.html

# Run specific test class
mvn -Dtest=CustomerTest test

# Run test suite
mvn -Dtest=CustomerTestSuite test

# Run tests with specific tag
mvn test -Dgroups="qa"
```

---

## Understanding Test Output

### Console Output

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running sh.surge.kunal.banking.models.CustomerTest

✓ Customer Object Not Null Test (1)
✓ Customer Object Not Null Test (2)
✓ Customer Object Not Null Test (3)
✓ Customer Getters and Setters Test[1]
✓ Customer Getters and Setters Test[2]
✓ Savings Account Exception Test
✓ testFullName(String)[1]
✓ testAccountNo()
✓ testEmail(Customer)[1]

[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0

[INFO] BUILD SUCCESS
```

---

### Test Results Breakdown

```
Tests run: 15          Total number of test methods executed
Failures: 0            Assertions that failed
Errors: 0              Unexpected exceptions
Skipped: 0             Tests marked with @Disabled
Time elapsed: 2.5s     Total execution time
```

---

### Failure Output

```
[ERROR] testEmailValidation  Time elapsed: 0.05 s  <<< FAILURE!
org.opentest4j.AssertionFailedError: 
Expected :true
Actual   :false
Message  : Email format invalid

    at CustomerTest.testEmail(CustomerTest.java:45)
```

---

## Continuous Integration

### GitHub Actions Example

Create `.github/workflows/test.yml`:

```yaml
name: Run Tests

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 17
      uses: actions/setup-java@v2
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Run tests with Maven
      run: mvn clean test
    
    - name: Generate coverage report
      run: mvn jacoco:report
    
    - name: Upload coverage to Codecov
      uses: codecov/codecov-action@v2
      with:
        files: target/site/jacoco/jacoco.xml
```

---

## Troubleshooting

### Tests Not Running

**Problem:** Maven doesn't find tests

**Solutions:**
1. Ensure test classes end with `Test` or `Tests`
2. Verify Surefire plugin configuration
3. Check test files are in `src/test/java`
4. Run with `-X` for debug output: `mvn test -X`

---

### CSV File Not Found

**Problem:** `@CsvFileSource` fails to find file

**Solutions:**
1. Place CSV in `src/test/resources/`
2. Reference with `/customer.csv` (leading slash)
3. Verify file name matches exactly (case-sensitive)
4. Check CSV encoding is UTF-8

---

### Parameterized Tests Failing

**Problem:** Method source returns null

**Solutions:**
1. Ensure method is `static`
2. Verify return type is `Stream<Arguments>`
3. Check method name matches annotation
4. Provide fully qualified name if in different class

---

### Coverage Report Not Generated

**Problem:** JaCoCo report missing

**Solutions:**
1. Run `mvn clean test jacoco:report`
2. Check JaCoCo plugin configuration in pom.xml
3. Verify tests actually ran (`mvn test`)
4. Look for report in `target/site/jacoco/index.html`

---

## Advantages of This Testing Strategy

1. **Comprehensive Coverage**: Multiple testing approaches (unit, parameterized, nested)
2. **Maintainable**: External CSV data, organized structure
3. **Realistic Data**: Faker integration for varied test scenarios
4. **Organized**: Nested tests and suites for logical grouping
5. **Flexible**: Tags allow selective test execution
6. **Measurable**: JaCoCo provides concrete coverage metrics
7. **Scalable**: Easy to add new test cases via CSV or method sources
8. **CI/CD Ready**: Maven integration for automated testing

---

## Extending This Implementation

### Add More Test Data Sources

```java
@ParameterizedTest
@JsonFileSource(resources = "/customers.json")
public void testFromJson(Customer customer) {
    // Test with JSON data
}
```

### Add Integration Tests

```java
@Tag("integration")
@Test
public void testDatabaseIntegration() {
    // Test with real database
}
```

### Add Performance Tests

```java
@Test
@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
public void testPerformance() {
    // Must complete in 100ms
}
```

---

## Conclusion

This JUnit 5 testing implementation demonstrates modern Java testing practices:

- **Parameterized Tests** reduce code duplication
- **Nested Tests** improve organization
- **Test Suites** enable selective execution
- **JaCoCo** provides measurable quality metrics
- **Best Practices** ensure maintainable, reliable tests

By following these patterns, you create a robust test suite that:
- ✅ Catches bugs early
- ✅ Documents expected behavior
- ✅ Enables confident refactoring
- ✅ Scales with your codebase
- ✅ Integrates with CI/CD pipelines

---

## Additional Resources

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)
- [Effective Unit Testing](https://www.manning.com/books/effective-unit-testing)
- [Base JDBC Application](https://github.com/Kunal70616c/spring-jdbc-data-access.git)

---

## License

This project is open-source and available for educational purposes.

## Author

Kunal - [GitHub Profile](https://github.com/Kunal70616c)
