package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.example.fetchers.AuthorByIdFetcher;
import org.example.fetchers.BookAuthorFetcher;
import org.example.fetchers.BookByIdFetcher;
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
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("hello", new HelloFetcher())
                        .dataFetcher("bookById", new BookByIdFetcher())
                        .dataFetcher("authorById", new AuthorByIdFetcher())
                )
                .type("Book", builder -> builder
                        .dataFetcher("author", new BookAuthorFetcher(new AuthorByIdFetcher()))
                )
                .build();

        // 3. Generate the GraphQL schema
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(
            typeDefinitionRegistry, runtimeWiring
        );
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

        // 4. Execute queries
//        String query = "{ hello }";


        // author will return null without the BookAuthorDataFetcher
        String query = """
                    {
                        bookById(id: "1") {
                            id
                            name
                            pageCount
                            author {
                                id
                                firstName
                                lastName
                            }
                        }
                    }
                """;


        Map<String, Object> data = graphQL.execute(query).getData();
        prettyPrint(data);

    }

    private static void prettyPrint(Object data) throws JsonProcessingException {
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data));
    }
}
