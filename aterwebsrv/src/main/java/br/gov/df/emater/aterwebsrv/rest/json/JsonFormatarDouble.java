package br.gov.df.emater.aterwebsrv.rest.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import br.gov.df.emater.aterwebsrv.ferramenta.UtilitarioNumero;

public class JsonFormatarDouble extends JsonDeserializer<Double> {

	public JsonFormatarDouble() {
	}

	@Override
	public Double deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Double result = UtilitarioNumero.getInstance().stringToDouble(jp.getText());
		return result;
	}
}