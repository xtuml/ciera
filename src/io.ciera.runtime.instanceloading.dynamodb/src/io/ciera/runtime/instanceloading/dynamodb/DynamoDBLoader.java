package io.ciera.runtime.instanceloading.dynamodb;

import java.io.File;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.Page;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

import io.ciera.runtime.instanceloading.ChangeLog;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

public abstract class DynamoDBLoader implements IDynamoDBLoader {
	
	private static final String HOST = "localhost";
	private static final int PORT = 8000;
	
	private AmazonDynamoDB dynamodb;
	private List<String> tableNames;
	
	public DynamoDBLoader() {
		dynamodb = null;
        DynamoDBProxyServer server = null;
        try {
		    server = ServerRunner.createServerFromCommandLineArgs(new String[]{ "-dbPath", System.getProperty("user.dir") + File.separator + "gen/code_generation", "-port", Integer.toString(getPort())});
			server.start();
            dynamodb = AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://" + getHost() + ":" + getPort(), "us-west-2"))
                    .withCredentials(new AWSStaticCredentialsProvider(new AWSCredentials() {
                        @Override
                        public String getAWSAccessKeyId() {
                            return "dummy";
                        }

                        @Override
                        public String getAWSSecretKey() {
                            return "dummy";
                        }
                    }))
                    .build();
        } catch (Exception e) {
        } finally {
            if(server != null) {
                try {
					server.stop();
				} catch (Exception e) {}
            }
            if(dynamodb != null) {
                dynamodb.shutdown();
            }
        }
        tableNames = null;
	}
	
	public AmazonDynamoDB getDb() {
		return dynamodb;
	}
	
	public List<String> getTableNames() {
		if (null == tableNames) {
		    ListTablesResult listTablesResult = dynamodb.listTables();
		    tableNames = listTablesResult.getTableNames();
		}
		return tableNames;
	}
	
	public void createTable(String tableName) {
        dynamodb.createTable(new CreateTableRequest()
          .withTableName(tableName)
          .withAttributeDefinitions(new AttributeDefinition()
            .withAttributeName("instanceId").withAttributeType("S"))
          .withKeySchema(new KeySchemaElement()
            .withAttributeName("instanceId").withKeyType(KeyType.HASH))
          .withProvisionedThroughput(new ProvisionedThroughput()
            .withReadCapacityUnits(10L)
            .withWriteCapacityUnits(5L)));
	}
	
	public Item getItem(Table table, UniqueId instanceId) throws XtumlException {
		ItemCollection<QueryOutcome> result = table.query(new QuerySpec().withKeyConditionExpression("instanceId = :id").withValueMap(new ValueMap().withString(":id", instanceId.serialize())));
		for (Page<Item, QueryOutcome> page : result.pages()) {
			for (Item item : page) {
				return item; // return the first item we find
			}
		}
		return null;
	}
	
	public void deleteItem(Table table, UniqueId instanceId) throws XtumlException {
		Item item = getItem(table, instanceId);
		if (null != item) {
			table.deleteItem("instanceId", instanceId.serialize());
		}
	}

	@Override
	public void serialize() throws XtumlException {
		serialize(new ChangeLog());
	}
	
	@Override
	public String getHost() {
		return HOST;
	}
	
	@Override
	public int getPort() {
		return PORT;
	}

}
