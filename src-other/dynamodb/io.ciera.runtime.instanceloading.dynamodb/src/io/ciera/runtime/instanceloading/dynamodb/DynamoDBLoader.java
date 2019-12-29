package io.ciera.runtime.instanceloading.dynamodb;

import java.util.Iterator;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.Page;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.RangeKeyCondition;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

import io.ciera.runtime.instanceloading.ChangeLog;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

public abstract class DynamoDBLoader implements IDynamoDBLoader {

    private AmazonDynamoDB dynamodb;

    public DynamoDBLoader() {
        dynamodb = AmazonDynamoDBClientBuilder.defaultClient();
    }

    public DynamoDB getDb() {
        return new DynamoDB(dynamodb);
    }

    public List<String> getTableNames() {
        ListTablesResult listTablesResult = dynamodb.listTables();
        return listTablesResult.getTableNames();
    }

    public Iterable<Item> getItems(String tableName, int componentId) {
        Table table = getDb().getTable(tableName);
        ItemCollection<QueryOutcome> query = table.query(new QuerySpec()
          .withHashKey(new KeyAttribute("componentId", componentId)));
        return new Iterable<Item>() {
            @Override
            public Iterator<Item> iterator() {
                return new CompoundIterator<>(query.pages());
            }
        };

    }

    public void createTable(String tableName) {
    	TableUtils.createTableIfNotExists(dynamodb, new CreateTableRequest()
          .withTableName(tableName)
          .withAttributeDefinitions(new AttributeDefinition().withAttributeName("componentId").withAttributeType("N"),
            new AttributeDefinition().withAttributeName("instanceId").withAttributeType("S"))
          .withKeySchema(new KeySchemaElement().withAttributeName("componentId").withKeyType(KeyType.HASH),
            new KeySchemaElement().withAttributeName("instanceId").withKeyType(KeyType.RANGE))
          .withProvisionedThroughput(new ProvisionedThroughput()
            .withReadCapacityUnits(100L)
            .withWriteCapacityUnits(50L)));
    	try {
    	    TableUtils.waitUntilActive(dynamodb, tableName);
    	} catch (InterruptedException e) {}
    }

    public Item getItem(Table table, int componentId, UniqueId instanceId) throws XtumlException {
        ItemCollection<QueryOutcome> result = table.query(new QuerySpec()
          .withHashKey(new KeyAttribute("componentId", componentId))
          .withRangeKeyCondition(new RangeKeyCondition("instanceId").eq(instanceId.serialize())));
        for (Page<Item, QueryOutcome> page : result.pages()) {
            for (Item item : page) {
                return item; // return the first item we find
            }
        }
        return null;
    }

    public void deleteItem(Table table, int componentId, UniqueId instanceId) throws XtumlException {
        Item item = getItem(table, componentId, instanceId);
        if (null != item) {
            table.deleteItem(new KeyAttribute("componentId", componentId), new KeyAttribute("instanceId", instanceId));
        }
    }

    @Override
    public void serialize() throws XtumlException {
        serialize(new ChangeLog());
    }
    
    @Override
    public void initialize() {
    	/*
    	for (String tableName : getTableNames()) {
    		TableUtils.deleteTableIfExists(dynamodb, new DeleteTableRequest().withTableName(tableName));
    	}
    	*/
    }

    private static class CompoundIterator<E, O extends Iterable<I>, I extends Iterable<E>> implements Iterator<E> {

        private Iterator<I> outerIter;
        private Iterator<E> innerIter;

        public CompoundIterator(O outer) {
            outerIter = outer.iterator();
            innerIter = outerIter.hasNext() ? outerIter.next().iterator() : null;
        }

        @Override
        public boolean hasNext() {
            if (null != innerIter) {
                if (!innerIter.hasNext()) {
                    if (outerIter.hasNext()) innerIter = outerIter.next().iterator();
                }
                return innerIter.hasNext();
            }
            else return false;
        }
        @Override
        public E next() {
            if (hasNext()) return innerIter.next();
            else return null;
        }

    }

}
