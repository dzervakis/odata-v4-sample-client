package org.apache.olingo.client.sample;

import java.net.URI;
import java.util.List;

import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.v4.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.domain.v4.ODataEntity;
import org.apache.olingo.commons.api.domain.v4.ODataEntitySet;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.format.ODataFormat;

public class SampleClient {
    private static final String SERVICE_ROOT = "http://services.odata.org/TripPinRESTierService/";

    private static final String ENTITY_SET_PEOPLE = "People";
    private static final String ENTITY_SET_AIRPORTS = "Airports";

    private final ODataClient client;

    public SampleClient() {
        client = ODataClientFactory.getV4();
        client.getConfiguration().setDefaultPubFormat(ODataFormat.JSON_NO_METADATA);
    }

    public List<ODataEntity> getAllPeople() {
        final URI peopleEntitySetURI = client.newURIBuilder(SERVICE_ROOT).appendEntitySetSegment(ENTITY_SET_PEOPLE).build();
        final ODataRetrieveResponse<ODataEntitySet> peopleEntitySetResponse = client.getRetrieveRequestFactory()
                .getEntitySetRequest(peopleEntitySetURI).execute();

        return peopleEntitySetResponse.getBody().getEntities();
    }

    public ODataEntity getPeople(String key) {
        final URI peopleEntityURI = client.newURIBuilder(SERVICE_ROOT).appendEntitySetSegment(ENTITY_SET_PEOPLE)
                .appendKeySegment(key).build();
        final ODataRetrieveResponse<ODataEntity> people = client.getRetrieveRequestFactory()
                .getEntityRequest(peopleEntityURI).execute();

        return people.getBody();
    }

    public List<ODataEntity> getAllAirports() {
        final URI airportsURI = client.newURIBuilder(SERVICE_ROOT)
                .appendEntitySetSegment(ENTITY_SET_AIRPORTS).build();
        final ODataRetrieveResponse<ODataEntitySet> airportsResponse = client.getRetrieveRequestFactory()
                .getEntitySetRequest(airportsURI).execute();

        return airportsResponse.getBody().getEntities();
    }

    public ODataEntity getAirport(String key) {
        final URI airportURI = client.newURIBuilder(SERVICE_ROOT).appendEntitySetSegment(ENTITY_SET_AIRPORTS)
                .appendKeySegment(key).build();
        final ODataRetrieveResponse<ODataEntity> airportResponse = client.getRetrieveRequestFactory()
                .getEntityRequest(airportURI).execute();

        return airportResponse.getBody();
    }

    public Edm getMetaDocument() {
        ODataRetrieveResponse<Edm> response = client.getRetrieveRequestFactory().getMetadataRequest(SERVICE_ROOT)
                .execute();

        return response.getBody();
    }
}