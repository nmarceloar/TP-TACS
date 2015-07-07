/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.impl;

import api.rest.exceptions.DomainLogicException;
import api.rest.views.Airline;
import api.rest.views.Airport;
import api.rest.views.City;
import api.rest.views.PriceDetail;
import api.rest.views.Segment;
import api.rest.views.TripDetails;
import com.google.appengine.repackaged.org.joda.time.DateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import model2.Recommendation;
import model2.impl.OfyRecommendation;
import model2.impl.OfyTrip;
import model2.impl.OfyUser;
import org.junit.Assert;
import org.junit.Test;
import repository.OfyRecommendationsRepository;
import repository.OfyTripsRepository;
import repository.OfyUsersRepository;
import unitTests.services.BaseOfyTest;

/**
 *
 * @author flpitu88
 */
public class OfyRecommendationsRepositoryImplTest extends BaseOfyTest {

    OfyUsersRepository userRepo = new OfyUsersRepositoryImpl();
    OfyTripsRepository tripRepo = new OfyTripsRepositoryImpl();
    OfyRecommendationsRepository recommendationRepo = new OfyRecommendationsRepositoryImpl();

    @Test
    public void addTest() {

        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        target = userRepo.add(target);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        trip = tripRepo.add(trip);
        OfyRecommendation recommendation = OfyRecommendation.createFrom(owner,
                target, trip);

        Assert.assertTrue(!recommendationRepo.exists(recommendation.getId()));

        recommendation = recommendationRepo.add(recommendation);

        Assert.assertTrue(recommendationRepo.exists(recommendation.getId()));

    }

    @Test
    public void findAllTest() {
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        target = userRepo.add(target);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        trip = tripRepo.add(trip);
        OfyRecommendation recommendation = OfyRecommendation.createFrom(owner,
                target, trip);
        recommendationRepo.add(recommendation);

        List<OfyRecommendation> listado = recommendationRepo.findAll();
        Assert.assertEquals(1, listado.size());

    }

    @Test
    public void findByIdTest() {
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        target = userRepo.add(target);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        trip = tripRepo.add(trip);
        OfyRecommendation recommendation = OfyRecommendation.createFrom(owner,
                target, trip);
        recommendationRepo.add(recommendation);

        OfyRecommendation reco = recommendationRepo.findById(recommendation.getId());
        Assert.assertEquals(recommendation.getId(), reco.getId());

        Assert.assertEquals(trip.getId(), reco.getTrip().getId());
        reco.markAs(Recommendation.Status.ACCEPTED);
        Assert.assertEquals(DateTime.now().getDayOfMonth(), reco.getCreationDate().getDate());
        Assert.assertEquals(Recommendation.Status.ACCEPTED.name(),
                reco.getStatus().name());
        Assert.assertNotNull(reco.getPatchDate());
        Assert.assertEquals(DateTime.now().getDayOfMonth(), reco.getPatchDate().getDate());
    }

    @Test(expected = DomainLogicException.class)
    public void findByIdTestNotFound() {
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        target = userRepo.add(target);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        trip = tripRepo.add(trip);
        OfyRecommendation recommendation = OfyRecommendation.createFrom(owner,
                target, trip);
        recommendationRepo.add(recommendation);

        OfyRecommendation reco = recommendationRepo.findById("NN");
    }

    @Test
    public void findByOwnerTest() {
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        OfyUser otro = OfyUser.createFrom(3L, "name3", "link3", "mail3");
        owner = userRepo.add(owner);
        target = userRepo.add(target);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        trip = tripRepo.add(trip);
        OfyRecommendation recommendation = OfyRecommendation.createFrom(owner,
                target, trip);
        recommendationRepo.add(recommendation);

        List<OfyRecommendation> encontrados1 = recommendationRepo.findByOwner(owner);
        Assert.assertEquals(1, encontrados1.size());
        Assert.assertEquals("name1", encontrados1.get(0).getOwner().getName());
        List<OfyRecommendation> encontrados2 = recommendationRepo.findByOwner(target);
        Assert.assertEquals(0, encontrados2.size());
    }

    @Test
    public void findByOwnerAndStatusTest() {
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        target = userRepo.add(target);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        trip = tripRepo.add(trip);
        OfyRecommendation recommendation = OfyRecommendation.createFrom(owner,
                target, trip);
        recommendationRepo.add(recommendation);

        List<OfyRecommendation> listado = recommendationRepo.
                findByOwnerAndStatus(owner, Recommendation.Status.PENDING);

        Assert.assertEquals(1, listado.size());
    }

    @Test
    public void findByTargetAndStatusTest() {
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        target = userRepo.add(target);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        trip = tripRepo.add(trip);
        OfyRecommendation recommendation = OfyRecommendation.createFrom(owner,
                target, trip);
        recommendationRepo.add(recommendation);

        List<OfyRecommendation> listado = recommendationRepo.findByTargetAndStatus(
                target, Recommendation.Status.PENDING);

        Assert.assertEquals(1, listado.size());
    }

    @Test
    public void removeAllTest() {
        OfyUser owner = OfyUser.createFrom(1L, "name1", "link1", "mail1");
        OfyUser target = OfyUser.createFrom(2L, "name2", "link2", "mail2");
        owner = userRepo.add(owner);
        target = userRepo.add(target);
        OfyTrip trip = OfyTrip.createFrom(owner, buildTripDetails());
        trip = tripRepo.add(trip);
        OfyRecommendation recommendation = OfyRecommendation.createFrom(owner,
                target, trip);
        recommendationRepo.add(recommendation);

        Assert.assertEquals(1, recommendationRepo.findAll().size());
        recommendationRepo.removeAll();
        Assert.assertEquals(0, recommendationRepo.findAll().size());
    }

    /**
     * Meotodo para crear un detalle de viajes utilizado en los tests
     *
     * @return
     */
    private TripDetails buildTripDetails() {

        final City c1 = new City("BUE", "Buenos Aires", 4566.321, 56565.34);
        final City c2 = new City("ROM", "Roma", 4566.321, 56565.34);
        final PriceDetail pd = new PriceDetail("ARS", 5545.12);

        Airport fromAirport = new Airport("EZE", "Ezeiza", 123.12, 564.12);
        Airport toAirport = new Airport("MOR",
                "Aeropuerto de Roma",
                123.12,
                564.12);

        Airline airline = new Airline("AE1", "aerolinea1");

        Segment segment = new Segment(fromAirport,
                toAirport,
                airline,
                "fid1",
                new Date(),
                new Date(),
                "45:45");

        Segment segment2 = new Segment(toAirport,
                fromAirport,
                airline,
                "fid1",
                new Date(),
                new Date(),
                "45:45");

        final List<Segment> outit = Arrays.asList(segment);
        final List<Segment> init = Arrays.asList(segment2);

        TripDetails td = new TripDetails(c1, c2, pd, outit, init);

        return td;

    }

}
