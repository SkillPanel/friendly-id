package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "should return item with all ID formats"

	request {
		method 'GET'
		url '/items/testItemId'
	}

	response {
		status 200
		headers {
			contentType applicationJson()
		}
		body(
			id: "testItemId",
			rawId: $(regex('[a-f0-9-]{36}')),
			friendlyUuid: "testItemId",
			friendlyId: "testItemId"
		)
	}
}
