package org.apache.olingo.client.sample;

import java.net.URI;
import java.util.List;

import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.format.ContentType;

public class SampleClient {
    private static final String SERVICE_ROOT = "http://localhost:8080/odata/";

    //example entity sets
    private static final String ENTITY_SET_ADDRESSES = "bagagn_201701__adressen";
    private static final String ENTITY_SET_ROADLINES = "brt_201611__inrichtingselement_lijn"; // gkn_201608__gkn_totaal

    private final ODataClient client;

    /*
     * Instance of an OData Client.
     */
    public SampleClient() {
        client = ODataClientFactory.getClient();
        client.getConfiguration().setDefaultPubFormat(ContentType.JSON_NO_METADATA);
    }

    /*
     * Examples
     */
    public List<ClientEntity> getAllAddresses() {
        final URI addressEntitySetURI = client.newURIBuilder(SERVICE_ROOT).appendEntitySetSegment(ENTITY_SET_ADDRESSES).build();
        final ODataRetrieveResponse<ClientEntitySet> addressEntitySetResponse = client.getRetrieveRequestFactory()
                .getEntitySetRequest(addressEntitySetURI).execute();
        return addressEntitySetResponse.getBody().getEntities();
    }

    public ClientEntity getAddress(String key) {
        final URI addressEntityURI = client.newURIBuilder(SERVICE_ROOT).appendEntitySetSegment(ENTITY_SET_ADDRESSES)
                .appendKeySegment(key).build();
        final ODataRetrieveResponse<ClientEntity> address = client.getRetrieveRequestFactory()
                .getEntityRequest(addressEntityURI).execute();
        return address.getBody();
    }

    public List<ClientEntity> getAllRoadlines() {
        final URI roadlinesURI = client.newURIBuilder(SERVICE_ROOT)
                .appendEntitySetSegment(ENTITY_SET_ROADLINES).build();
        final ODataRetrieveResponse<ClientEntitySet> roadlinesResponse = client.getRetrieveRequestFactory()
                .getEntitySetRequest(roadlinesURI).execute();
        return roadlinesResponse.getBody().getEntities();
    }

    public ClientEntity getRoadline(Integer key) {
        final URI roadlineURI = client.newURIBuilder(SERVICE_ROOT).appendEntitySetSegment(ENTITY_SET_ROADLINES)
                .appendKeySegment(key).build();
        final ODataRetrieveResponse<ClientEntity> roadlineResponse = client.getRetrieveRequestFactory()
                .getEntityRequest(roadlineURI).execute();
        return roadlineResponse.getBody();
    }
    /*
     * //end of examples
     */

    /*********************
     * Generalized calls *
     *********************/

    /*
     * Get all the Entity Sets
     */
    public Edm getMetaDocument() {
        ODataRetrieveResponse<Edm> response = client.getRetrieveRequestFactory().getMetadataRequest(SERVICE_ROOT)
                .execute();
        return response.getBody();
    }

    /*
     * Get all the Entity Sets
     */
    public List<ClientEntity> getAllEntities(String entitySetName) {
        final URI entitySetURI = client.newURIBuilder(SERVICE_ROOT)
                .appendEntitySetSegment(entitySetName).build();
        final ODataRetrieveResponse<ClientEntitySet> entitySetResponse = client.getRetrieveRequestFactory()
                .getEntitySetRequest(entitySetURI).execute();
        return entitySetResponse.getBody().getEntities();
    }

    public ClientEntity getEntity(String entitySetName, Object key) {
        final URI entityURI = client.newURIBuilder(SERVICE_ROOT).appendEntitySetSegment(entitySetName)
                .appendKeySegment(key).build();
        final ODataRetrieveResponse<ClientEntity> entityResponse = client.getRetrieveRequestFactory()
                .getEntityRequest(entityURI).execute();
        return entityResponse.getBody();
    }

}