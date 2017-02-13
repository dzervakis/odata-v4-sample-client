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

        //Instantiate an OData client:
        final SampleClient myClient = new SampleClient();
        Integer count;

        try {
            //Get the names of all Entity Sets:
            final Edm metaDocument = myClient.getMetaDocument();
            final List<EdmEntitySet> entitySets = metaDocument.getSchemas().get(0).getEntityContainer().getEntitySets();
            System.out.println("Available entity sets:");
            for (EdmEntitySet entitySet : entitySets) {
                System.out.println("\t" + entitySet.getName());
            }
            System.out.println();

            //Example #1 - all entities
            System.out.println("Entity Set {Addresses}:");
            count = 0;
            for (ClientEntity addressEntity : myClient.getAllAddresses()) {
                System.out.println("#"+count);
                printEntity(addressEntity);
                System.out.println();
                count += 1;
            }
            System.out.println("=====================================================================================");
            System.out.println();

            //Example #2 - all entities
            System.out.println("Entity Set {Roadlines}:");
            count = 0;
            for (ClientEntity roadlineEntity : myClient.getAllRoadlines()) {
                System.out.println("#"+count);
                printEntity(roadlineEntity);
                System.out.println();
                count += 1;
            }
            System.out.println("=====================================================================================");
            System.out.println();

            //Example #3 - all entities from named Entity Set
            String entitySetName = "brt_201604__relief_hogezijde";
            System.out.println("Entity Set {" + entitySetName + "}:");
            count = 0;
            for (ClientEntity odataEntity : myClient.getAllEntities(entitySetName)) {
                System.out.println("#"+count);
                printEntity(odataEntity);
                System.out.println();
                count += 1;
            }
            System.out.println("=====================================================================================");
            System.out.println();

            //Example #1 - one entity, with key id (string)
            System.out.println();
            System.out.println("Address: #0003010000126006");
            printEntity(myClient.getAddress("0003010000126006"));  //printEntity(myClient.getAddress("0005100000006388"));
            System.out.println();

            //Example #2 - one entity, with key id (integer)
            System.out.println("Roadline: #103085369");
            printEntity(myClient.getRoadline(103085369));
            System.out.println();

            //Example #3 - one entity, with key id (object cast to integer)
            Integer entityKey = 104534812;
            System.out.println(entitySetName + ": #" + entityKey.toString());
            printEntity(myClient.getEntity(entitySetName,entityKey));
            System.out.println();

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