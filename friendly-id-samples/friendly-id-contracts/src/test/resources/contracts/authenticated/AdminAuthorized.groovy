package contracts.authenticated

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should return OK when accessing admin endpoint with authentication"

	request {
		method 'GET'
		url '/admin/status'
	}

	response {
		status 200
		body "OK"
	}
}
