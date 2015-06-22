package services;

import java.util.List;

import api.rest.exceptions.DomainLogicException;

import com.googlecode.objectify.NotFoundException;

public class OfyRecommendationRepository {

	private final static OfyRecommendationRepository INSTANCE = new OfyRecommendationRepository();

	public static OfyRecommendationRepository getInstance() {

		return OfyRecommendationRepository.INSTANCE;

	}

	private OfyRecommendationRepository() {

	}

	public OfyRecommendation add(final OfyRecommendation recommendation) {

		OfyService.ofy()
		.save()
		.entity(recommendation)
		.now();

		return recommendation;

	}

	public boolean exists(final String id) {

		return OfyService.ofy()
				.load()
				.type(OfyRecommendation.class)
				.id(id)
				.now() != null;

	}

	public List<OfyRecommendation> findAll() {

		return OfyService.ofy()
				.load()
				.type(OfyRecommendation.class)
				.order("-creationDate")
				.list();

	}

	public OfyRecommendation findById(final String id) {

		try {

			return OfyService.ofy()
					.load()
					.type(OfyRecommendation.class)
					.id(id)
					.safe();

		} catch (NotFoundException nfe) {
			throw new DomainLogicException(
					"No se encontro la recommendacion con id "
							+ id
							+ "\n"
							+ "Esto puede ser porque se empezo con el datastore vacio durante el testing"
							+ "\nSe deben cargar las recommendaciones primero");

		}

	}

	public List<OfyRecommendation> findByOwner(final OfyUser owner) {

		return OfyService.ofy()
				.load()
				.type(OfyRecommendation.class)
				.filter("owner", owner)
				.order("-creationDate")
				.list();

	}

	public List<OfyRecommendation> findByOwnerAndStatus(final OfyUser owner,
			final OfyRecommendation.Status status) {

		return OfyService.ofy()
				.load()
				.type(OfyRecommendation.class)
				.filter("owner", owner)
				.filter("status", status)
				.order("-creationDate")
				.list();

	}

	public List<OfyRecommendation> findByTargetAndStatus(final OfyUser target,
			final OfyRecommendation.Status status) {

		return OfyService.ofy()
				.load()
				.type(OfyRecommendation.class)
				.filter("target", target)
				.filter("status", status)
				.order("-patchDate")
				.list();

	}

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
