package com.mlefevre.samples.hibernate3.repository;

import com.mlefevre.samples.hibernate3.entity.MyClass;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MyClassRepositoryImpl implements MyClassRepository {

    @Autowired
   private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<MyClass> findAllLatestVersions() {
        Projection projection = Projections.projectionList()
                .add(Projections.max("version").as("version"))
                .add(Projections.groupProperty("title").as("title"));


        List<MyClass> results = this.sessionFactory.getCurrentSession()
                .createCriteria(MyClass.class)
                .setProjection(projection)
                .setResultTransformer(Transformers.aliasToBean(MyClass.class))
                .list();

        return results;
    }

}
