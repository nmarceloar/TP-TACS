package integracion.despegar;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JodaDateTimeSerializer extends JsonSerializer<DateTime> {

	private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm");

	public void serialize(final DateTime dateTime,
		final JsonGenerator generator, final SerializerProvider provider)
		throws IOException, JsonProcessingException {

		generator.writeString(JodaDateTimeSerializer.formatter.print(dateTime));
	}
}
