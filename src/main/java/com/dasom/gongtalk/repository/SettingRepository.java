package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.user.Setting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends CrudRepository<Setting, Integer>{

}
