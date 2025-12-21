org.springframework.cloud.contract.spec.Contract.make {
	request {
		method 'GET'
		url '/foos/caffe/bars/latte'
		headers {
			applicationJsonUtf8()
		}
	}
	response {
		status 200
		body(
				name: 'Bar',
				_links: [
						self: [
								href: 'http://localhost/foos/caffe/bars/latte'
						],
						foos: [
								href: 'http://localhost/foos'
						]
				]
		)
		headers {
			applicationJsonUtf8()
		}
	}
}
