package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.io.GATLineParserTest;
import test.io.GTTLineParserTest;

@RunWith(Suite.class)
@SuiteClasses({
	GTTLineParserTest.class,
	GATLineParserTest.class
})
public class AllTests {

}
