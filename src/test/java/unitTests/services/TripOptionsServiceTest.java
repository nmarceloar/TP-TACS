/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unitTests.services;

import mocks.ItinerariosDespegarMock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author flavio
 */
@RunWith(MockitoJUnitRunner.class)
public class TripOptionsServiceTest {

	// @InjectMocks
	// private TripOptionsService trpServ =
	// TripOptionsServiceImpl.getInstance();
	//
    @Mock
    private ItinerariosDespegarMock despMock;
    
    @Test
    public void findTripOptionsTest(){
        
    }
}