package org.apache.olingo.client.sample;

import java.net.URI;
import java.util.List;

import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.format.ContentType;

public class SampleClient {
    private static final String SERVICE_ROOT = "http://services.odata.org/TripPinRESTierService/";

    private static final String ENTITY_SET_PEOPLE = "People";
    private static final String ENTITY_SET_AIRPORTS = "Airports";

    private final ODataClient client;

    public SampleClient() {
        client = ODataClientFactory.getClient();
        client.getConfiguration().setDefaultPubFormat(ContentType.JSON_NO_METADATA);
    }

    public List<ClientEntity> getAllPeople() {
        final URI peopleEntitySetURI = client.newURIBuilder(SERVICE_ROOT).appendEntitySetSegment(ENTITY_SET_PEOPLE).build();
        final ODataRetrieveResponse<ClientEntitySet> peopleEntitySetResponse = client.getRetrieveRequestFactory()
                .getEntitySetRequest(peopleEntitySetURI).execute();

        return peopleEntitySetResponse.getBody().getEntities();
    }

    public ClientEntity getPeople(String key) {
        final URI peopleEntityURI = client.newURIBuilder(SERVICE_ROOT).appendEntitySetSegment(ENTITY_SET_PEOPLE)
                .appendKeySegment(key).build();
        final ODataRetrieveResponse<ClientEntity> people = client.getRetrieveRequestFactory()
                .getEntityRequest(peopleEntityURI).execute();

        return people.getBody();
    }

    public List<ClientEntity> getAllAirports() {
        final URI airportsURI = client.newURIBuilder(SERVICE_ROOT)
                .appendEntitySetSegment(ENTITY_SET_AIRPORTS).build();
        final ODataRetrieveResponse<ClientEntitySet> airportsResponse = client.getRetrieveRequestFactory()
                .getEntitySetRequest(airportsURI).execute();

        return airportsResponse.getBody().getEntities();
    }

    public ClientEntity getAirport(String key) {
        final URI airportURI = client.newURIBuilder(SERVICE_ROOT).appendEntitySetSegment(ENTITY_SET_AIRPORTS)
                .appendKeySegment(key).build();
        final ODataRetrieveResponse<ClientEntity> airportResponse = client.getRetrieveRequestFactory()
                .getEntityRequest(airportURI).execute();

        return airportResponse.getBody();
    }

    public Edm getMetaDocument() {
        ODataRetrieveResponse<Edm> response = client.getRetrieveRequestFactory().getMetadataRequest(SERVICE_ROOT)
                .execute();

        return response.getBody();
    }
}