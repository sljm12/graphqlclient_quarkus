package org.acme.model;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

import java.util.List;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;


@Path("/")
public class StarWarsResource {
	
	@Inject
	@GraphQLClient("star-wars-dynamic")
	private ArticleGraphQL clientAPI;
	
	private Document createAggregateQuery() {
		Document query = document(operation(
				 field("_helloworld_article_aggregate", 
						 field("nodes",
								 field("author",field("id"),field("name")), 
								 field("id"), 
								 field("title"),
								 field("rating")))));
		return query;
	}
	
	private Document createQuery() {
		Document query = document(operation(
				 field("_helloworld_article", field("id"), field("rating"), field("title"))));
		return query;
	}

	@Inject
	@GraphQLClient("star-wars-dynamic")
	DynamicGraphQLClient dynamicClient;

	
	@GET
	@Path("/typesafe")
	@Produces(MediaType.APPLICATION_JSON)
	@Blocking
	public List<Article> getAllArticlesViaTS() {
		return clientAPI.getArticles(10,0);
	}
	
	@GET
	@Path("/typesafe1")
	@Produces(MediaType.APPLICATION_JSON)
	@Blocking
	public List<Article> getAllArticlesAuthorsViaTS() {
		return clientAPI.getArticlesAuthor().getNodes();
	}
	
	@GET
	@Path("/dynamic")
	@Produces(MediaType.APPLICATION_JSON)
	@Blocking
	public List<Article> getAllArticlesViaDynamic() throws Exception {
		Document query = createQuery();
		System.out.println(query.toString());
		Response response = dynamicClient.executeSync(query);
		System.out.println(response.toString());
		response.hasData();
		//return response.getData();
		return response.getList(Article.class, "_helloworld_article");
	}
	
	@GET
	@Path("/dynamic1")
	@Produces(MediaType.APPLICATION_JSON)
	@Blocking
	public JsonObject getAllArticlesWithAuthor() throws Exception {
		Document query = createAggregateQuery();
		System.out.println(query.toString());
		Response response = dynamicClient.executeSync(query);
		System.out.println(response.toString());
		response.hasData();
		return response.getData();
		//return response.getList(Article.class, "_helloworld_article_aggregate");
	}

	@GET
	@Path("/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String checkQuery() {
		Document query = createQuery();

		return query.build();
	}
	
	@GET
	@Path("/query1")
	@Produces(MediaType.APPLICATION_JSON)
	public String checkQuery1() {
		Document query = createAggregateQuery();

		return query.build();
	}
	
}