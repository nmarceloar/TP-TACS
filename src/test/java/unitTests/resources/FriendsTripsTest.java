///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package unitTests.resources;
//
//import api.rest.resources.FriendsTrips;
//import api.rest.views.City;
//import com.google.appengine.repackaged.com.google.protobuf.ServiceException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import javax.validation.ValidationException;
//import model2.Trip;
//import model2.impl.OfyTrip;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import static org.mockito.Mockito.when;
//import org.mockito.runners.MockitoJUnitRunner;
//import services.TripsService;
//
///**
// *
// * @author flpitu88
// */
//@RunWith(MockitoJUnitRunner.class)
//public class FriendsTripsTest {
//    
//    @InjectMocks
//    private FriendsTrips frdRes = new FriendsTrips();
//
//    @Mock
//    private TripsService tripsService;
//
//    @Before
//    public void prepare() throws ServiceException, ValidationException {
//        List<? extends Trip> lista = new ArrayList<OfyTrip>();
//        when(tripsService.findByOwner(1)).thenReturn(null);
//    }
//    
//    @Test
//    public void findTripsByOwnerTest(){
//        
//    }
//    
//}
