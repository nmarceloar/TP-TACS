package services.impl;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;

import services.impl.OfyService;

import com.google.appengine.api.datastore.dev.HighRepJobPolicy;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

public class BaseOfyTest {

	private LocalServiceTestHelper helper;
	private Closeable session;

	public BaseOfyTest() {

		helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig().setApplyAllHighRepJobPolicy());

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