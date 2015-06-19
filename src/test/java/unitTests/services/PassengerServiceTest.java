/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import apis.PassengerAPI;
import com.google.appengine.repackaged.com.google.protobuf.ServiceException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ValidationException;
import model.Passenger;
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
import services.PersistenceService;

/**
 *
 * @author flpitu88
 */
@RunWith(MockitoJUnitRunner.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class})
public class PassengerServiceTest {
    
    @InjectMocks
    private PassengerAPI psjServ = new PersistenceService();

    @Mock
    private PassengerDAO pDao;

    @Before
    public void prepare() throws ServiceException, ValidationException {
        
        final List<Passenger> lista = new ArrayList<>();
        Passenger pMartin = new Passenger("10206028316763565", "Martin", "De Ciervo", "11", new ArrayList()); 
        Passenger pFlavio = new Passenger("10153253398579452", "Flavio", "Pietrolati", "22", new ArrayList());
        lista.add(pMartin);
        lista.add(pFlavio);
        
        //Configuracion del mock
        when(pDao.getTodosLosPasajeros()).thenReturn(lista);
        when(pDao.getPasajeroById("10206028316763565")).thenReturn(pMartin);
        when(pDao.getPasajeroById("10153253398579452")).thenReturn(pFlavio);
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Passenger pas = (Passenger) args[0];
                lista.add(pas);
                return null;
            }
        }).when(pDao).guardarPasajero(any(Passenger.class));
        
    }
    
    @Test
    public void getAllPassengersTest() {
        List<Passenger> lista = psjServ.getListOfPassengers();
        Assert.assertNotNull(lista);
        Assert.assertEquals(2, lista.size());
        Assert.assertEquals("10206028316763565", lista.get(0).getIdUser());
        Assert.assertEquals("10153253398579452", lista.get(1).getIdUser());
    }

    @Test
    public void getPasajeroByIdTest() {
        String id = "10153253398579452";
        Passenger psj = psjServ.getPassengerById(id);
        Assert.assertEquals(id, psj.getIdUser());
    }

    @Test
    public void savePassengerTest() {
        Assert.assertEquals(2, psjServ.getListOfPassengers().size());
        Passenger p = new Passenger();
        p.setIdUser("111111");
        p.setName("NombrePrueba");
        p.setSurname("ApellidoPrueba");
        psjServ.createPassenger(p);
        Assert.assertEquals(3, psjServ.getListOfPassengers().size());
    }

}
