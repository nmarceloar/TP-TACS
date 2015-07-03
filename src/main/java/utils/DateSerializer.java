/**
 *
 */

package utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateSerializer extends JsonSerializer<Date> {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	@Override
	public void serialize(Date date, JsonGenerator gen,
		SerializerProvider provider) throws IOException,
		JsonProcessingException {

		String formattedDate = this.dateFormat.format(date);

		gen.writeString(formattedDate);

	}
}
