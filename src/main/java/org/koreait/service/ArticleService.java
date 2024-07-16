package org.koreait.service;

import org.koreait.container.Container;
import org.koreait.dto.Article;
import org.koreait.dao.ArticleDao;

import java.util.List;
import java.util.Map;

public class ArticleService {
    private ArticleDao articleDao;
    public ArticleService() {
        this.articleDao = Container.articleDao;
    }

    public int doWrite(int memberId, String title, String body) {
        return articleDao.doWrite(memberId,title, body);

    }

    public List<Article> getArticles() {
        return articleDao.getArticles();
    }
    public Article getArticleById(int id) {
        return articleDao.getArticleById(id);
    }
    public void doUpdate(int id, String title, String body) {
        articleDao.doUpdate(id, title, body);
    }
    public void doDelete(int id) {
        articleDao.doDelete(id);
    }
}