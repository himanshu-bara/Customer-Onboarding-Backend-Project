package com.oracle.testService;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ AccountServiceImplTest.class, CustomerServiceImplTest.class, KycServiceImplTest.class })
public class AllTests {

}
