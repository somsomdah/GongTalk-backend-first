package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.School;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends CrudRepository<School, Integer>{

    List<School> findAll();
}
