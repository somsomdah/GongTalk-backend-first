package com.dasom.gongtalk.repository;


import com.dasom.gongtalk.domain.CrawlingInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlingInfoRepository extends CrudRepository<CrawlingInfo, Integer> {
}
