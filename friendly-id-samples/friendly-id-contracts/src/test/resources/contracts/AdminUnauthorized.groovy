package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should return 401 when accessing admin endpoint without authentication"

	request {
		method 'GET'
		url '/admin/status'
	}

	response {
		status 401
	}
}
