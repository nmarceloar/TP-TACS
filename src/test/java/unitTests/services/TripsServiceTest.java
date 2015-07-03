/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import com.google.appengine.repackaged.com.google.protobuf.ServiceException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ValidationException;

import model.Segment;
import model.Trip;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import static org.mockito.Matchers.any;

import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import repository.PassengerDAO;
import repository.TripsDAO;
import services.TripsAPI;
import services.impl.PersistenceService;

/**
 *
 * @author flpitu88
 */
@RunWith(MockitoJUnitRunner.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class})
public class TripsServiceTest {
    
    @InjectMocks
    private TripsAPI tripServ = new PersistenceService();

//    @Mock
//    private RecommendationDAO rDao;
//
    @Mock
    private PassengerDAO pDao;

    @Mock
    private TripsDAO tDao;
    
    @Before
    public void prepare() throws ServiceException, ValidationException {
        final List<Trip> listaViajes = new ArrayList<>();
        
        Segment seg1 = new Segment("Buenos Aires", "San Pablo",
                DateTime.now().plusDays(5).toString(),
                DateTime.now().plusDays(30).toString(),
                Integer.toString(25), "LAN", "A01");

        Trip viaje1 = new Trip("10153253398579452","Buenos Aires", "San Pablo","4500 ARS", Arrays.asList(seg1),1);
        listaViajes.add(viaje1);
        
        Segment seg2 = new Segment("Roma", "Paris",
                DateTime.now().plusDays(15).toString(),
                DateTime.now().plusDays(45).toString(),
                Integer.toString(30), "AA", "AA-054");

        Trip viaje2 = new Trip("10153253398579452","Roma", "Paris","4500 ARS", Arrays.asList(seg2),2);
        listaViajes.add(viaje2);
        
        Segment seg3 = new Segment("Berlin", "Amsterdam",
                DateTime.now().plusDays(6).toString(),
                DateTime.now().plusDays(12).toString(),
                Integer.toString(6), "LAN", "L23");

        Trip viaje3 = new Trip("10206028316763565","Berlin", "Amsterdam","4500 ARS", Arrays.asList(seg3),3);
        listaViajes.add(viaje3);
        
        Segment seg4 = new Segment("Buenos Aires", "San Pablo",
                DateTime.now().plusDays(8).toString(),
                DateTime.now().plusDays(12).toString(),
                Integer.toString(4), "Gol", "GO-012");

        Trip viaje4 = new Trip("10206028316763565","Fortaleza", "Madrid","4500 ARS", Arrays.asList(seg4),4);
        listaViajes.add(viaje4);
        
        // Configuracion del Mock
        when(tDao.searchTripByPassenger("10153253398579452")).thenReturn(Arrays.asList(viaje1,viaje2));
        when(tDao.searchTripByPassenger("10206028316763565")).thenReturn(Arrays.asList(viaje3,viaje4));
        when(tDao.getTrips()).thenReturn(listaViajes);
        when(tDao.searchTripById(1)).thenReturn(viaje1);
        when(pDao.getIdsAmigos("10153253398579452")).thenReturn(Arrays.asList("10206028316763565"));
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Trip v = (Trip) args[0];
                listaViajes.add(v);
                return null;
            }
        }).when(tDao).saveTrip(any(Trip.class));
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Integer id = (Integer) args[0];
                listaViajes.remove(listaViajes.size()-1);
                return null;
            }
        }).when(tDao).deleteTrip(any(Integer.class));
    }
    
    @Test
    public void getTripsOfPassengerTest() {
        List<Trip> lista = tripServ.getTripsOfPassenger("10153253398579452");
        Assert.assertNotNull(lista);
        Assert.assertEquals(2, lista.size());
    }

    @Test
    public void getTripsTest() {
        List<Trip> lista = tripServ.getTrips();
        Assert.assertEquals(1, lista.get(0).getIdTrip());
        Assert.assertEquals(2, lista.get(1).getIdTrip());
        Assert.assertEquals(3, lista.get(2).getIdTrip());
        Assert.assertEquals(4, lista.get(3).getIdTrip());
    }

    @Test
    public void getTripTest() {
        Assert.assertEquals(1, tripServ.getTrip(1).getIdTrip());
    }

    @Test
    public void getTripsOfFriendsOfUserTest() {
        List<Trip> viajes = tripServ.getTripsOfFriendsOfUser("10153253398579452");
        Assert.assertEquals(2, viajes.size());
        Assert.assertEquals(3, viajes.get(0).getIdTrip());
        Assert.assertEquals(4, viajes.get(1).getIdTrip());
    }
    
    @Test
    public void saveTripTest() {
        Assert.assertEquals(4, tripServ.getTrips().size());
        Segment seg1 = new Segment("Paris", "Berlin",
                DateTime.now().plusDays(5).toString(),
                DateTime.now().plusDays(30).toString(),
                Integer.toString(25), "AER", "AE-01");
        Trip viaje1 = new Trip("10153253398579452", "Paris", "Berlin",
                "13154 ARS", Arrays.asList(seg1),5);
        tripServ.saveTrip(viaje1);
        Assert.assertEquals(5, tripServ.getTrips().size());
    }
    
    @Test
    public void deleteTripTest() {
        List<Trip> viajes = tripServ.getTrips();
        Assert.assertEquals(4, viajes.size());
        Assert.assertEquals(1, viajes.get(0).getIdTrip());
        Assert.assertEquals(2, viajes.get(1).getIdTrip());
        Assert.assertEquals(3, viajes.get(2).getIdTrip());
        Assert.assertEquals(4, viajes.get(3).getIdTrip());
        tripServ.deleteTrip(5);
        viajes = tripServ.getTrips();
        Assert.assertEquals(3, viajes.size());
        Assert.assertEquals(1, viajes.get(0).getIdTrip());
        Assert.assertEquals(2, viajes.get(1).getIdTrip());
        Assert.assertEquals(3, viajes.get(2).getIdTrip());
    }
    
}
