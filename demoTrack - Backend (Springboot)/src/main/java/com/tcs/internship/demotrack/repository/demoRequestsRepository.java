package com.tcs.internship.demotrack.repository;

import com.tcs.internship.demotrack.model.DemoRequests;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface demoRequestsRepository extends CrudRepository<DemoRequests, Long> {

	DemoRequests findByRequestNumber(String requestNumber);

	List<DemoRequests> findByRequesterUserId(String requesterUserId);

	boolean existsByRequestNumber(String requestNumber);
}
