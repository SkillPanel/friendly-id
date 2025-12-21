package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should return 401 when creating item without authentication"

	request {
		method 'POST'
		url '/items'
		headers {
			contentType applicationJson()
		}
		body(
			id: "unauthorizedItem"
		)
	}

	response {
		status 401
	}
}
