package com.cxynw.repository.base;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<D,I> extends JpaRepositoryImplementation<D,I> {

}
