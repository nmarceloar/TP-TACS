/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;

import model.Trip;

/**
 *
 * @author flavio
 */
public interface TripsDAO {

	public void saveTrip(Trip v);

	public Trip searchTripById(int id);

	public List<Trip> searchTripByPassenger(String fr);

	public List<Trip> getTrips();

	public String deleteTrip(int id);

}
