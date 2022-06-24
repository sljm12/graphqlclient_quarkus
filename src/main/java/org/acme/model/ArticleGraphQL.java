package org.acme.model;
import java.util.List;

import org.eclipse.microprofile.graphql.Query;

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;

@GraphQLClientApi(configKey = "star-wars-typesafe")
public interface ArticleGraphQL {
	/**
	 * @Query would be the query name that we are calling.  
	 * @param limit
	 * @param offset
	 * @return
	 */
	@Query("_helloworld_article")
	List<Article> getArticles(int limit, int offset);
	
	@Query("_helloworld_article_aggregate")
	//@Query("getArticlesAuthor")
	ArticleConnection getArticlesAuthor();
}
