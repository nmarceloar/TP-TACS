package repository.impl;

import java.util.List;

import model2.Recommendation;
import model2.User;
import model2.impl.OfyRecommendation;
import repository.RecommendationsRepository;
import services.impl.OfyService;
import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.NotFoundException;

public class OfyRecommendationRepository implements RecommendationsRepository {

	@Override
	public Recommendation add(final Recommendation recommendation) {

		OfyService.ofy()
			.save()
			.entity(recommendation)
			.now();

		return recommendation;

	}

	@Override
	public boolean exists(final String id) {

		return OfyService.ofy()
			.load()
			.type(OfyRecommendation.class)
			.id(id)
			.now() != null;

	}

	@Override
	public List<? extends Recommendation> findAll() {

		return OfyService.ofy()
			.load()
			.type(OfyRecommendation.class)
			.order("-creationDate")
			.list();

	}

	@Override
	public Recommendation findById(final String id) {

		try {

			return OfyService.ofy()
				.load()
				.type(OfyRecommendation.class)
				.id(id)
				.safe();

		} catch (NotFoundException nfe) {
			throw new DomainLogicException("No se encontro la recommendacion con id " + id
				+ "\n"
				+ "Esto puede ser porque se empezo con el datastore vacio durante el testing"
				+ "\nSe deben cargar las recommendaciones primero");

		}

	}

	@Override
	public List<? extends Recommendation> findByOwner(final User owner) {

		return OfyService.ofy()
			.load()
			.type(OfyRecommendation.class)
			.filter("owner", owner)
			.order("-creationDate")
			.list();

	}

	@Override
	public List<? extends Recommendation> findByOwnerAndStatus(
		final User owner, final Recommendation.Status status) {

		return OfyService.ofy()
			.load()
			.type(OfyRecommendation.class)
			.filter("owner", owner)
			.filter("status", status)
			.order("-creationDate")
			.list();

	}

	@Override
	public List<? extends Recommendation> findByTargetAndStatus(
		final User target, final Recommendation.Status status) {

		return OfyService.ofy()
			.load()
			.type(OfyRecommendation.class)
			.filter("target", target)
			.filter("status", status)
			.order("-patchDate")
			.list();

	}

	@Override
	public void removeAll() {

		OfyService.ofy()
			.delete()
			.keys(OfyService.ofy()
				.load()
				.type(OfyRecommendation.class)
				.keys()
				.list())
			.now();

	}

}
