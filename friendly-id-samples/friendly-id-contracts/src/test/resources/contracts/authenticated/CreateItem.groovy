package contracts.authenticated

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should create item when authenticated"

	request {
		method 'POST'
		url '/items'
		headers {
			contentType applicationJson()
		}
		body(
			id: "authItemId"
		)
	}

	response {
		status 200
		headers {
			contentType applicationJson()
		}
		body(
			id: "authItemId",
			rawId: $(regex('[a-f0-9-]{36}')),
			friendlyUuid: "authItemId",
			friendlyId: "authItemId"
		)
	}
}
