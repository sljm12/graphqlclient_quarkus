package org.acme.model;

import java.util.List;

public class AuthorConnection {
	private List<Author> authors;
	private List<Article> articles;

    public List<Author> getFilms() {
        return authors;
    }

    public void setFilms(List<Author> films) {
        this.authors = authors;
    }

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}
