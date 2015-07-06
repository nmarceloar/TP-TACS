package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Passenger implements Serializable {

	@JsonProperty("nombre")
	private String name;

	@JsonProperty("apellido")
	private String surname;

	@JsonProperty("token")
	private String token;

	@JsonProperty("id")
	private String idUser;

	@JsonProperty("amigos")
	private List<String> friends;

	// @JsonProperty("recomendaciones")
	// private List<Integer> recommendations;

	// public List<Integer> getRecommendations() {
	// return recommendations;
	// }
	//
	// public void setRecommendations(List<Integer> recommendations) {
	// this.recommendations = recommendations;
	// }

	public Passenger(String id, String nombre, String apellido,
		String token, List<String> amigos) {

		this.name = nombre;
		this.surname = apellido;
		this.token = token;
		this.friends = amigos;
		// this.recommendations = new ArrayList<>();
		this.idUser = id;

	}

	public Passenger() {

	}

	public String getIdUser() {

		return idUser;
	}

	public void setIdUser(String idUser) {

		this.idUser = idUser;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getSurname() {

		return surname;
	}

	public void setSurname(String surname) {

		this.surname = surname;
	}

	public String getToken() {

		return token;
	}

	public void setToken(String token) {

		this.token = token;
	}

	public List<String> getFriends() {

		return friends;
	}

	public void setFriends(List<String> friends) {

		this.friends = friends;
	}

	public boolean esAmigo(Passenger pj) {

		boolean resul = false;
		for (String idAm : getFriends()) {
			if (idAm == pj.getIdUser()) {
				resul = true;
			}
		}
		return resul;
	}

	public void agregarAmigo(String idAmigo) {

		getFriends().add(idAmigo);
	}

	public void agregarAmigo(Passenger p) {

		getFriends().add(p.getIdUser());
	}

	public void agregarAmigos(List<String> amigos) {

		getFriends().addAll(amigos);
	}

	public void agregarAmigosPorPasajeros(List<Passenger> amigos) {

		List<String> listaIds = new ArrayList<>();
		for (Passenger p : amigos) {
			listaIds.add(p.getIdUser());
		}
		agregarAmigos(listaIds);
	}

}
