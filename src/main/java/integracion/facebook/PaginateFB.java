/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integracion.facebook;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author flpitu88
 */
public class PaginateFB implements Serializable {

	@JsonProperty("next")
	String next;

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public PaginateFB(String next) {
		this.next = next;
	}

	public PaginateFB() {
	}

}
