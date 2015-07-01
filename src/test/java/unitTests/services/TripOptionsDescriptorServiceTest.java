/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import services.TripOptionsDescriptorService;
import services.TripOptionsDescriptorServiceImpl;

/**
 *
 * @author flavio
 */
@RunWith(MockitoJUnitRunner.class)
public class TripOptionsDescriptorServiceTest {
    
    @InjectMocks
    private TripOptionsDescriptorService psjServ = TripOptionsDescriptorServiceImpl.getInstance();
    
    
    
}
