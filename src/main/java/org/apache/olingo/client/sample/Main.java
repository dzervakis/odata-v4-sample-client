package org.apache.olingo.client.sample;

import java.util.List;

import org.apache.olingo.client.api.communication.ODataClientErrorException;
import org.apache.olingo.client.api.domain.ClientComplexValue;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmEntitySet;

public class Main {
    public static void main(String[] args) {
        final SampleClient myClient = new SampleClient();

        try {
            final Edm metaDocument = myClient.getMetaDocument();
            final List<EdmEntitySet> entitySets = metaDocument.getSchemas().get(0).getEntityContainer().getEntitySets();
            System.out.println("Available entity sets:");
            for (EdmEntitySet entitySet : entitySets) {
                System.out.println("\t" + entitySet.getName());
            }
            System.out.println();

            System.out.println("People:");
            int count = 0;
            for (ClientEntity peopleEntity : myClient.getAllPeople()) {
                System.out.println("#"+count);
                printEntity(peopleEntity);
                System.out.println();
                count += 1;
            }

            System.out.println("Airports:");
            count = 0;
            for (ClientEntity airportEntity : myClient.getAllAirports()) {
                System.out.println("#"+count);
                printEntity(airportEntity);
                System.out.println();
                count += 1;
            }

            System.out.println();
            System.out.println("People: #scottketchum");
            printEntity(myClient.getPeople("scottketchum"));
            System.out.println();

            System.out.println("Airport: #KSFO");
            printEntity(myClient.getAirport("KSFO"));
            System.out.println();

            //printEntity(myClient.getAirport(10)); // There is no airport with Id = 10
        } catch (ODataClientErrorException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printEntity(ClientEntity entity) {
        for (ClientProperty property : entity.getProperties()) {
            if (property.hasComplexValue()) {
                printComplexProperty(property);
            } else {
                System.out.println(property.getName() + ":= " + property.getValue());
            }
        }
        System.out.println("-------------------------");
    }

    private static void printComplexProperty(ClientProperty property) {
        final ClientComplexValue complexValue = property.getComplexValue();
        System.out.println(property.getName() + ":");

        for (ClientProperty complexProp : complexValue) {
            System.out.println("\t" + complexProp.getName() + ":= " + complexProp.getValue());
        }
    }
}