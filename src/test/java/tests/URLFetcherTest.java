//
//package tests;
//
//import integracion.despegar.City;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.appengine.api.urlfetch.HTTPHeader;
//import com.google.appengine.api.urlfetch.HTTPRequest;
//import com.google.appengine.api.urlfetch.HTTPResponse;
//import com.google.appengine.api.urlfetch.URLFetchService;
//import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
//import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
//import com.google.appengine.tools.development.testing.LocalURLFetchServiceTestConfig;
//
//public class URLFetcherTest {
//	
//	private static final LocalServiceTestHelper helper =
//	    new LocalServiceTestHelper(new LocalURLFetchServiceTestConfig());
//	
//	@Before
//	public void setUp() throws Exception {
//	
//		helper.setUp();
//	}
//	
//	@After
//	public void tearDown() throws Exception {
//	
//		helper.tearDown();
//	}
//	
//	@Test
//	public void testServiceDisponibility() {
//	
//		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
//		
//		Assert.assertTrue(fetcher != null);
//		
//	}
//	
//	@SuppressWarnings("unused")
//	@Test
//	public void testSyncFetch() {
//	
//		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
//		
//		try {
//			
//			URL url = new URL("http://www.ole.com.ar");
//			
//			HTTPResponse response = fetcher.fetch(url);
//			
//			byte[] bytes;
//			
//			System.out.println(response.getFinalUrl());
//			
//			System.out.println(response.getResponseCode());
//			
//			List<HTTPHeader> headers = response.getHeaders();
//			
//			for (HTTPHeader header : headers) {
//				
//				System.out.println(header.getName());
//				
//				System.out.println(header.getValue());
//				
//			}
//			
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//			
//		}
//		
//	}
//	
//	@Test
//	public void testASyncFetch() throws InterruptedException,
//	    ExecutionException, JsonParseException, JsonMappingException,
//	    IOException {
//	
//		System.out.println("THREADID= " + Thread.currentThread()
//		    .getId());
//		
//		HTTPRequest request = null;
//		
//		request =
//		    new HTTPRequest(new URL("https://api.despegar.com/v3/autocomplete"
//		        + "?query=buenos" + "&locale=es_AR" + "&city_result=10"));
//		
//		request.setHeader(new HTTPHeader("X-Apikey",
//		    "19638437094c4892a8af7cdbed49ee43"));
//		
//		request.setHeader(new HTTPHeader("Accept", "application/json"));
//		
//		long startTime = System.currentTimeMillis();
//		
//		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
//		
//		System.out.println("<h2>Asynchronous fetch</h2>");
//		
//		ArrayList<Future<HTTPResponse>> asyncResponses =
//		    new ArrayList<Future<HTTPResponse>>();
//		
//		for (int i = 0; i < 20; i++) {
//			
//			System.out.println(request.getURL()
//			    .toString());
//			asyncResponses.add(fetcher.fetchAsync(request));
//			
//		}
//		
//		for (Future<HTTPResponse> future : asyncResponses) {
//			
//			System.out.println("THREADID= " + Thread.currentThread()
//			    .getId());
//			HTTPResponse response = future.get();
//			
//			List<City> cities =
//			    new ObjectMapper().readValue(response.getContent(),
//			        new TypeReference<List<City>>() {
//			        });
//			
//			for (City city : cities) {
//				
//				System.out.println(city);
//				
//			}
//			
//			// System.out.println(new String(response.getContent()));
//			
//		}
//		
//		long totalProcessingTime = System.currentTimeMillis() - startTime;
//		
//		System.out.println("TOTAL TIME: " + totalProcessingTime + "ms");
//		
//	}
//	
//}
