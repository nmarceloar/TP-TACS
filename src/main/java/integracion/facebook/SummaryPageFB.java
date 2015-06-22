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
public class SummaryPageFB implements Serializable {

	@JsonProperty("total_count")
	int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public SummaryPageFB(int total) {
		this.total = total;
	}

	public SummaryPageFB() {
	}

}
