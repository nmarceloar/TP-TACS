/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import api.rest.Airline;
import com.google.appengine.repackaged.com.google.protobuf.ServiceException;
import javax.validation.ValidationException;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import services.AirlinesService;
import services.AirportsService;
import services.TripOptionsDescriptorService;
import services.TripOptionsService;
import services.impl.TripOptionsDescriptorServiceImpl;

/**
 *
 * @author flpitu88
 */
@RunWith(MockitoJUnitRunner.class)
public class TripOptionsDescriptorServiceTest {

    @InjectMocks
    private TripOptionsDescriptorService trpOpSrv
            = new TripOptionsDescriptorServiceImpl();

    @Mock
    private TripOptionsService tripOptionsService;

    @Mock
    private AirlinesService airlinesService;

    @Mock
    private AirportsService airportsService;

    @Before
    public void prepare() throws ServiceException, ValidationException {
        Airline aerolinea = new Airline("AA", " ", "Prueba Aero", "Argentina");
        when(airlinesService.findByCode(any(String.class))).thenReturn(aerolinea);
    }

    @Test
    @Ignore
    public void findTripOptionsTest() {
        trpOpSrv.findTripOptions("BUE", "ROM", DateTime.now(),
                DateTime.now(), 0, 5);
        Assert.assertTrue(true);
    }

}
