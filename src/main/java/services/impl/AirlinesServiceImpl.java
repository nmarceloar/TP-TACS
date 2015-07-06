/**
 *
 */

package services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import services.AirlinesService;
import api.rest.Airline;

public class AirlinesServiceImpl implements AirlinesService {

	private final ConcurrentMap<String, Airline> airlines;

	public AirlinesServiceImpl() {

		this.airlines = new ConcurrentHashMap<String, Airline>();
		this.fillMap();

	}

	private void fillMap() {

		BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass()
			.getClassLoader()
			.getResourceAsStream("airlines.csv")));

		String line;

		try {

			while ((line = reader.readLine()) != null) {

				String[] splitted = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

				String airportCode = splitted[3].replace("\"", "");

				if (!airportCode.isEmpty() && airportCode.matches("[A-Z]{2}")) {

					String callsign = splitted[5].replace("\"", "");
					String name = splitted[1].replace("\"", "");
					String country = splitted[6].replace("\"", "");

					this.airlines.put(airportCode,
						new Airline(airportCode, callsign, name, country));

				}

			}

		} catch (IOException ex) {

			throw new RuntimeException(ex);

		} finally {

			try {

				reader.close();

			} catch (IOException ex) {

				throw new RuntimeException(ex);

			}

		}

	}

	@Override
	public Airline findByCode(final String twoLetterIataCode) {

		return this.airlines.get(twoLetterIataCode);

	}

	@Override
	public List<Airline> findAll() {

		return new ArrayList<Airline>(airlines.values());

	}

}
