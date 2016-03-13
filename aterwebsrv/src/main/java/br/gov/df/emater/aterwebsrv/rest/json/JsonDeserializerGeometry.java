package br.gov.df.emater.aterwebsrv.rest.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class JsonDeserializerGeometry extends JsonDeserializer<Geometry> {

	private GeometryFactory factory = new GeometryFactory();

	@Override
	public Geometry deserialize(JsonParser jsonParser, DeserializationContext arg1) throws IOException, JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
		JsonNode node = oc.readTree(jsonParser);
		Geometry result = geometry(node);
		return result;
	}

	private Geometry geometry(JsonNode node) {
		Geometry result = null;
		String type = node.get("type").textValue();
		ArrayNode coordinates = (ArrayNode) node.get("coordinates");

		if ("Point".equals(type)) {
			result = point(coordinates);
		} else if ("MultiPoint".equals(type)) {
			result = multiPoint(coordinates);
		} else if ("LineString".equals(type)) {
			result = lineString(coordinates);
		} else if ("MultiLineString".equals(type)) {
			result = multiLineString(coordinates);
		} else if ("Polygon".equals(type)) {
			result = polygon(coordinates);
		} else if ("MultiPolygon".equals(type)) {
			result = multiPolygon(coordinates);
		} else if ("GeometryCollection".equals(type)) {
			result = geometryCollection((ArrayNode) node.get("geometries"));
		}

		return result;
	}

	private Geometry point(ArrayNode coordinates) {
		Coordinate coordinate = toCoordinate(coordinates);
		Point result = factory.createPoint(coordinate);
		return result;
	}

	private Geometry multiPoint(ArrayNode nodes) {
		Coordinate[] coordinates = toCoordinateArray(nodes);
		MultiPoint result = factory.createMultiPoint(coordinates);
		return result;
	}

	private LineString lineString(ArrayNode nodes) {
		Coordinate[] coordinates = toCoordinateArray(nodes);
		LineString result = factory.createLineString(coordinates);
		return result;
	}

	private MultiLineString multiLineString(ArrayNode nodes) {
		LineString[] lineStrings = new LineString[nodes.size()];
		for (int i = 0; i < lineStrings.length; ++i) {
			lineStrings[i] = lineString((ArrayNode) nodes.get(i));
		}
		MultiLineString result = factory.createMultiLineString(lineStrings);
		return result;
	}

	private Polygon polygon(ArrayNode nodes) {
		LinearRing outerRing = toLinearRing((ArrayNode) nodes.get(0));
		LinearRing[] innerRings = new LinearRing[nodes.size() - 1];
		for (int i = 0; i < innerRings.length; ++i) {
			innerRings[i] = toLinearRing((ArrayNode) nodes.get(i + 1));
		}
		Polygon result = factory.createPolygon(outerRing, innerRings);
		return result;
	}

	private MultiPolygon multiPolygon(ArrayNode nodes) {
		Polygon[] polygons = new Polygon[nodes.size()];
		for (int i = 0; i < polygons.length; ++i) {
			polygons[i] = polygon((ArrayNode) nodes.get(i));
		}
		MultiPolygon result = factory.createMultiPolygon(polygons);
		return result;
	}

	private GeometryCollection geometryCollection(ArrayNode nodes) {
		Geometry[] geometries = new Geometry[nodes.size()];
		for (int i = 0; i < geometries.length; ++i) {
			geometries[i] = geometry(nodes.get(i));
		}
		GeometryCollection result = factory.createGeometryCollection(geometries);
		return result;
	}

	private LinearRing toLinearRing(ArrayNode nodes) {
		Coordinate[] coordinates = toCoordinateArray(nodes);
		LinearRing result = factory.createLinearRing(coordinates);
		return result;
	}

	private Coordinate[] toCoordinateArray(ArrayNode nodes) {
		Coordinate[] result = new Coordinate[nodes.size()];
		for (int i = 0; i < result.length; ++i) {
			result[i] = toCoordinate((ArrayNode) nodes.get(i));
		}
		return result;
	}

	private Coordinate toCoordinate(ArrayNode node) {
		double x = 0, y = 0, z = 0;
		if (node.size() > 1) {
			x = node.get(0).asDouble();
			y = node.get(1).asDouble();
		}
		if (node.size() > 2) {
			z = node.get(1).asDouble();
		}
		Coordinate result = new Coordinate(x, y, z);
		return result;
	}
}