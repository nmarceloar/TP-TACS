package unitTests.services;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

public class BaseOfyTest {

	private LocalServiceTestHelper helper;
	private Closeable session;

	public BaseOfyTest() {
		helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	}

	@Before
	public void setup() {

		session = ObjectifyService.begin();
		helper.setUp();

	}

	@After
	public void tearDown() throws IOException {

		this.session.close();
		this.helper.tearDown();

	}

}