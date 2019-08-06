package ru.xe72.notes.db;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.xe72.notes.entity.Note;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional // Посмотреть зачем он нужен. И зачем вообще здесь транзакции
public class NotesSearch {

  @Autowired EntityManager em;

  public List<Note> searchNotes(String searchStr) {
    FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
//    em.getTransaction().begin();

    QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(Note.class).get();

    // Обычный, ищет по целым словам
//    Query luceneQuery =
//        qb.keyword().onFields("title", "text", "tags.nameSearch").matching(searchStr).createQuery();


    // Посмотреть
    Query luceneQuery =
        qb.keyword().fuzzy().onFields("title", "text", "tags.nameSearch").ignoreAnalyzer().matching(searchStr).createQuery();

    FullTextQuery fullTextQuery = ftem.createFullTextQuery(luceneQuery, Note.class);
    List result = fullTextQuery.getResultList();
//    em.getTransaction().commit();

    return result;
  }
}
