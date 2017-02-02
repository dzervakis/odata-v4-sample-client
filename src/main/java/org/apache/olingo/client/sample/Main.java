package org.apache.olingo.client.sample;

import java.util.List;

import org.apache.olingo.client.api.communication.ODataClientErrorException;
import org.apache.olingo.commons.api.domain.ODataComplexValue;
import org.apache.olingo.commons.api.domain.v4.ODataEntity;
import org.apache.olingo.commons.api.domain.v4.ODataProperty;
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
            for (ODataEntity peopleEntity : myClient.getAllPeople()) {
                System.out.println("#"+count);
                printEntity(peopleEntity);
                System.out.println();
                count += 1;
            }

            System.out.println("Airports:");
            count = 0;
            for (ODataEntity airportEntity : myClient.getAllAirports()) {
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

    private static void printEntity(ODataEntity entity) {
        for (ODataProperty property : entity.getProperties()) {
            if (property.hasComplexValue()) {
                printComplexProperty(property);
            } else {
                System.out.println(property.getName() + ":= " + property.getValue());
            }
        }
        System.out.println("-------------------------");
    }

    private static void printComplexProperty(ODataProperty property) {
        final ODataComplexValue<ODataProperty> complexValue = property.getComplexValue();
        System.out.println(property.getName() + ":");

        for (ODataProperty complexProp : complexValue) {
            System.out.println("\t" + complexProp.getName() + ":= " + complexProp.getValue());
        }
    }
}