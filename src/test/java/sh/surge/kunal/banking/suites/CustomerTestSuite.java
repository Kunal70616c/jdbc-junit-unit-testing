package sh.surge.kunal.banking.suites;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@SelectPackages("sh.surge.kunal.banking.models")
@IncludeTags("qa") // IncludeTags is used to include tests with specific tags
@Suite // Suite is used to group tests
public class CustomerTestSuite {

}
