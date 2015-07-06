package repository.impl;

import java.util.List;

import model2.Recommendation;
import model2.impl.OfyRecommendation;
import model2.impl.OfyUser;
import repository.OfyRecommendationsRepository;
import services.impl.OfyService;
import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.NotFoundException;

public class OfyRecommendationsRepositoryImpl implements
	OfyRecommendationsRepository {

	@Override
	public OfyRecommendation add(final OfyRecommendation recommendation) {

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
	public List<OfyRecommendation> findAll() {

		return OfyService.ofy()
			.load()
			.type(OfyRecommendation.class)
			.order("-creationDate")
			.list();

	}

	@Override
	public OfyRecommendation findById(final String id) {

		try {

			return OfyService.ofy()
				.load()
				.type(OfyRecommendation.class)
				.id(id)
				.safe();

		} catch (final NotFoundException nfe) {
			throw new DomainLogicException("No se encontro la recommendacion con id " + id
				+ "\n"
				+ "Esto puede ser porque se empezo con el datastore vacio durante el testing"
				+ "\nSe deben cargar las recommendaciones primero");

		}

	}

	@Override
	public List<OfyRecommendation> findByOwner(final OfyUser owner) {

		return OfyService.ofy()
			.load()
			.type(OfyRecommendation.class)
			.filter("owner", owner)
			.order("-creationDate")
			.list();

	}

	@Override
	public List<OfyRecommendation> findByOwnerAndStatus(
		final OfyUser owner, final Recommendation.Status status) {

		return OfyService.ofy()
			.load()
			.type(OfyRecommendation.class)
			.filter("owner", owner)
			.filter("status", status)
			.order("-creationDate")
			.list();

	}

	@Override
	public List<OfyRecommendation> findByTargetAndStatus(
		final OfyUser target, final Recommendation.Status status) {

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
