package com.quovantis.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quovantis.model.GenericMaster;

/**
 * Repository for Generic Master
 * @author Vivek Gupta
 *
 */
@Repository
public interface GenericMasterRepository extends JpaRepository<GenericMaster, Long> {

	@Query("from GenericMaster gm where gm.type in (:typeList)")
	List<GenericMaster> findByType(@Param("typeList") List<String>typeList);
}
