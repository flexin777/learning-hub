package ua.com.learninghub.model.dao;

import ua.com.learninghub.model.entities.RuleType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by vasax32 on 16.07.14.
 */

public class RuleTypeDao {
    private static EntityManagerFactory entityManagerFactory = HibernateUtil.buildEntityManagerFactory();

    public List<RuleType > selectAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT rule FROM RuleType rule");
        List<RuleType > rules = query.getResultList();
        entityManager.close();
        return rules;
    }

    public RuleType  selectById(int id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT rule FROM RuleType rule WHERE rule.idRuleType = :c_id");
        query.setParameter("c_id",id);
        RuleType  rule = (RuleType)query.getSingleResult();
        entityManager.close();
        return rule;
    }

    public int update(RuleType rule) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        RuleType  ruleUpd = (RuleType) entityManager.find(RuleType.class, rule.getIdRuleType());

        if (ruleUpd == null) return 0;

        entityManager.getTransaction().begin();
        ruleUpd.setName(rule.getName());
        ruleUpd.setEnabled(rule.isEnabled());
        entityManager.getTransaction().commit();
        entityManager.close();
        return 1;
    }

    public void insert(RuleType ruleType){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(ruleType);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
