package ua.hudyma;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import lombok.extern.log4j.Log4j2;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Log4j2
class SpringRestApplicationTests {
    public static final String AWS_DYNAMODB = "aws.dynamodb";
    public static final String USERS = "Users";
    public static AmazonDynamoDB amazonDynamoDB;
    public static DynamoDB dynamoDB;

    @BeforeEach
    public void setupClass() {
        Properties properties = loadFromFileInClasspath("application.properties")
                .orElseThrow();
        String accessKey = properties.getProperty(AWS_DYNAMODB + ".accessKey");
        String secretKey = properties.getProperty(AWS_DYNAMODB + ".secretKey");
        String region = properties.getProperty(AWS_DYNAMODB + ".region");
        String endpoint = properties.getProperty(AWS_DYNAMODB + ".endpoint");

        amazonDynamoDB = AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder
                                .EndpointConfiguration(endpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(accessKey, secretKey))).build();
        dynamoDB = new DynamoDB(amazonDynamoDB);
    }

    @Test
    void checkAWSInstancesNotNull() {
        assertNotNull(amazonDynamoDB);
        assertNotNull(dynamoDB);
    }

    @Test
    void canRetrieveItemsFromTable() {
        Table table = dynamoDB.getTable(USERS);
        assertNotNull(table);
        Item item = table.getItem("id", "1");
        log.info(item.asMap());
        assertEquals(item.asMap(), Map.of(
                "name", "John",
                "id", "1",
                "email", "john@gmail.com"));
    }

    private static Optional<Properties> loadFromFileInClasspath(String fileName) {
        InputStream stream = null;
        try {
            Properties config = new Properties();
            Path configLocation = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
            stream = Files.newInputStream(configLocation);
            config.load(stream);
            return Optional.of(config);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
