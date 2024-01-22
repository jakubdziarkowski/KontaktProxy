# Proxy API for Kontakt.io Building Information

This project provides a proxy API implemented in Java using the Spring framework. The rate limiter is implemented using Bucket4j, and java-dotenv is used for storing secrets, such as the Kontakt.io API-Key.

## Project Overview

In addition to the fields specified in the task description, I decided to include building ID, floor ID, and floor name as they are essential and commonly used fields. If these fields need to be hidden from the proxy API client intentionally, they can be easily excluded from the respective model classes.

I defined the `id` in the path parameter of our request as a String, following the documentation [here](https://developer.kontakt.io/docs/dev-ctr-loc-occ-api/b83219d93ef3c-retrieve-a-building), even though the response `id` is of type number. This results in a 404 error for `GET /building/some_string` instead of a 400 error.

Attributes `properties` and `imageXyGeojson` in the `Floor` class are defined as follows:
```java
private Map<String, Object> properties;
private Map<String, Object> imageXyGeojson;
```
This allows these objects to be returned in the same format as they come from the original API. This approach is not only convenient but also necessary due to inconsistencies between the documentation and the actual API response.

# README.md

## Response Examples

### Example 1

**Response Example from Documentation:**
```json
{
  "properties": {
    "EgQhanFqevOm": "string",
    "OfrViMjzwNPS": "string",
    "taVMBxTzdnKH": "string"
  }
}
```

**Actual Response Example:**
```json
{
  "properties": {
    "outlineCreated": true
  }
}
```

### Example 2

**Response Example from Documentation:**
```json
{
  "imageXyGeojson": {
    "type": "string",
    "geometry": {
      "type": "string",
      "coordinates": [
        {
          "0": [
            {
              "0": [
                {}
              ],
              "1": [
                {}
              ],
              "2": [
                {}
              ],
              "3": [
                {}
              ],
              "4": [
                {}
              ]
            }
          ]
        }
      ]
    }
  }
}
```

**Actual Response Example:**
```json
{
  "imageXyGeojson": {
    "type": "Feature",
    "geometry": {
      "type": "Polygon",
      "coordinates": [
        [
          [
            -6.3,
            8.9
          ],
          [
            17.9,
            8.9
          ],
          [
            17.9,
            -10.5
          ],
          [
            -6.3,
            -10.5
          ],
          [
            -6.3,
            8.9
          ]
        ]
      ]
    }
  }
}
```

## Additional Requirements

### 1. Ensure the service can handle 50 requests per second, with full scalability and thread safety

I tested, among other things, using "gradle performanceTest", which underneath runs the following test:

### Test Script

```javascript
import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
	vus: 100,
	duration: '30s'
};

export default function() {
	const response = http.get('http://localhost:8080/building/480414');
	if (response.status !== 200) {
		console.error('Error:', response.status, response.body);
		if (response.status === 429) {
			sleep(1);
		}
	}
}
```

### Test Output Example

```plaintext
# Test Output
data_received..................: 18 MB  585 kB/s
data_sent......................: 1.9 MB 63 kB/s
http_req_blocked...............: avg=86.82µs  min=0s       med=0s       max=19.54ms p(90)=0s       p(95)=0s      
http_req_connecting............: avg=10.96µs  min=0s       med=0s       max=2.99ms  p(90)=0s       p(95)=0s      
http_req_duration..............: avg=150.75ms min=126.92ms med=130.99ms max=3.56s   p(90)=135.16ms p(95)=144.22ms
{ expected_response:true }...: avg=150.75ms min=126.92ms med=130.99ms max=3.56s   p(90)=135.16ms p(95)=144.22ms
http_req_failed................: 0.00%  ✓ 0          ✗ 19932
http_req_receiving.............: avg=422.61µs min=0s       med=343µs    max=32.54ms p(90)=896µs    p(95)=954.23µs
http_req_sending...............: avg=7.42µs   min=0s       med=0s       max=2.37ms  p(90)=0s       p(95)=0s      
http_req_tls_handshaking.......: avg=0s       min=0s       med=0s       max=0s      p(90)=0s       p(95)=0s      
http_req_waiting...............: avg=150.32ms min=126.92ms med=130.57ms max=3.56s   p(90)=134.79ms p(95)=143.86ms
http_reqs......................: 19932  661.393817/s
iteration_duration.............: avg=150.94ms min=127.07ms med=131.08ms max=3.56s   p(90)=135.29ms p(95)=144.35ms
iterations.....................: 19932  661.393817/s
vus............................: 100    min=100      max=100
vus_max........................: 100    min=100      max=100
```

## Thread Safety and Scalability

The proxy service is implemented as a Spring Boot application with an embedded Tomcat server. Both Spring and Tomcat are designed to handle multi-threading efficiently. Tomcat, by default, uses a pool of worker threads to process incoming requests.

Additionally, a rate limiter has been implemented based on IP addresses to control the request rate. If rate limiting is not required, it can be easily removed by eliminating the usage of `ipRateLimiter.allowRequest(ipAddress)` in the `BuildingController`. Rate limiter settings can be modified in the `src\main\resources\application.properties` file.

It's important to note that the current implementation of the rate limiter relies on `ConcurrentHashMap` for thread safety. While effective, it is not a scalable solution, and consideration should be given to using a distributed cache like Redis for improved scalability in a production environment. Configuration for the rate limiter is available in the `application.properties` file, with the current setting:

```plaintext
rate.limiter.requests-per-second=100
```


### 2. Implement a comprehensive set of end-to-end tests that can be executed using build tools such as Maven or Gradle.

Multiple unit tests and an integration test for the `BuildingController` and `BuildingService` were implemented. These tests can be executed using `gradle test`. They cover a significant portion of the application's logic and can be considered end-to-end tests. However, tests involving Kontakt.io API itself were not implemented as they would require actual API calls rather than mocks.

### 3. Emphasize good design principles and maintain high code quality.

✅

### 4. Strive to make the solution nearly production-ready. If additional work is necessary, describe it in the README file.

✅

## Additional Notes

The Kontakt.io API documentation mentions a rate limit of 40 requests per second. However, during performance testing, the proxy service successfully handled over a thousand requests per second without receiving error responses. If the Kontakt.io rate limiter functions as described in the documentation, it's crucial to address potential issues where one client or a group of clients could monopolize the entire rate limit, hindering other clients' API usage.

In a production solution, it is advisable to replace java-dotenv with a dedicated tool such as AWS Secrets Manager, or at the very least, ensure proper encryption of the .env file and restrict access permissions to it. Files containing sensitive information, like the .env file, should never be present in the repository (it was added to .gitignore). The content of the file appears as follows (with the Api-Key value replaced by secret_value):

```
KONTAKT_API_KEY=secret_value
```


Authentication for the proxy service was not implemented, as the task assumes we are an authenticated user.