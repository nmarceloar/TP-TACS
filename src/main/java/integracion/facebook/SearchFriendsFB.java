/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integracion.facebook;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author flpitu88
 */
public class SearchFriendsFB implements Serializable {

	@JsonProperty("data")
	List<UserRegisteredFB> usuarios;

	@JsonProperty("paging")
	PaginateFB paginate;

	@JsonProperty("summary")
	SummaryPageFB sumary;

	public List<UserRegisteredFB> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UserRegisteredFB> usuarios) {
		this.usuarios = usuarios;
	}

	public PaginateFB getPaginate() {
		return paginate;
	}

	public void setPaginate(PaginateFB paginate) {
		this.paginate = paginate;
	}

	public SummaryPageFB getSumary() {
		return sumary;
	}

	public void setSumary(SummaryPageFB sumary) {
		this.sumary = sumary;
	}

	public SearchFriendsFB(List<UserRegisteredFB> usuarios,
		PaginateFB paginate, SummaryPageFB sumary) {
		this.usuarios = usuarios;
		this.paginate = paginate;
		this.sumary = sumary;
	}

	public SearchFriendsFB() {
	}

}
