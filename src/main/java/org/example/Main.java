package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.example.fetchers.HelloFetcher;

import java.io.File;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        // 1. Parse the schema file
        File schemaFile = new File("src/main/resources/schema.graphqls");
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schemaFile);

        // 2. Configure RuntimeWiring
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring().type(
    "Query", builder -> builder
                    .dataFetcher("hello", new HelloFetcher())
        ).build();

        // 3. Generate the GraphQL schema
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(
            typeDefinitionRegistry, runtimeWiring
        );
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

        // 4. Execute a sample query
        String query = "{ hello }";
        Map<String, Object> data = graphQL.execute(query).getData();

        System.out.println(
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data)
        );

    }
}
