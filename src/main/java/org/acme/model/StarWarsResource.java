package org.acme.model;

import static io.smallrye.graphql.client.core.Argument.arg;
import static io.smallrye.graphql.client.core.Argument.args;
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

import org.jboss.logging.annotations.Param;

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
	
	@Inject
	@GraphQLClient("star-wars-dynamic")
	DynamicGraphQLClient dynamicClient;
	
	/**
	 * Create a query that pulls Object and Sub Object
	 * @return
	 */
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
	
	/**
	 * Create Dynamic Queries with arguments
	 * @param limit
	 * @param offset
	 * @return
	 */
	private Document createQuery(int limit, int offset) {
		Document query = document(operation(
				 field("_helloworld_article", 
						 args(arg("limit", limit), arg("offset", offset)),
						 field("id"), field("rating"), field("title"))));
		return query;
	}

	

	
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
	
	/**
	 * Sample service that gets the data via rest parameters
	 * @param limit
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/dynamic/{offset}/{limit}")
	@Produces(MediaType.APPLICATION_JSON)
	@Blocking
	public List<Article> getAllArticlesViaDynamic(@Param int limit, @Param int offset) throws Exception {
		Document query = createQuery(limit,offset);
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
	@Path("/dynamic2")
	@Produces(MediaType.APPLICATION_JSON)
	@Blocking
	public List<Article> getAllArticlesWithAuthorWithLimits() throws Exception {
		Document query = createAggregateQuery();
		System.out.println(query.toString());
		Response response = dynamicClient.executeSync(query);
		System.out.println(response.toString());
		response.hasData();
		//return response.getData();
		return response.getObject(ArticleConnection.class, "_helloworld_article_aggregate").getNodes();
	}

	@GET
	@Path("/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String checkQuery() {
		Document query = createQuery(10,0);

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