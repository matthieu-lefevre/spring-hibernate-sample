package com.mlefevre.samples.hibernate3.repository;

import com.mlefevre.samples.hibernate3.entity.MyClass;

import java.util.List;

public interface MyClassRepository {


    List<MyClass> findAllLatestVersions();

}
