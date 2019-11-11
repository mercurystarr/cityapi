# cityapi
Simple Example of a REST API using SpringBoot and SpringWeb

### Prerequisites

You will need the following to run this application

* [Java 8](https://java.com/en/download/)
* [Maven 3](https://maven.apache.org/download.cgi)

### Start REST Webservice

```
spring-boot:run
```

### API Details

#### List all Cities

+ **URL:** `/city/list`

+ **Method:** GET

+ **Sample Call:** 

```
 curl 'http://localhost:8081/city/list' 
```

+ **Success Response:** 200 OK

+ **Sample Response Body:** 
```json
[
  {
    "id": 1,
    "name": "Tokyo",
    "latitude": 35.685,
    "longitude": 139.751389
  },
  {
    "id": 2,
    "name": "London",
    "latitude": 51.514125,
    "longitude": -0.093689
  },
  {
    "id": 3,
    "name": "Toronto",
    "latitude": 43.666667,
    "longitude": -79.416667
  },
  {
    "id": 4,
    "name": "Vancouver",
    "latitude": 49.25,
    "longitude": -123.133333
  },
  {
    "id": 5,
    "name": "Los Angeles",
    "latitude": 34.0522222,
    "longitude": -118.2427778
  },
  {
    "id": 6,
    "name": "Chicago",
    "latitude": 41.85,
    "longitude": -87.65
  },
  {
    "id": 7,
    "name": "Moscow",
    "latitude": 55.752222,
    "longitude": 37.615556
  },
  {
    "id": 8,
    "name": "Beijing",
    "latitude": 29.346443,
    "longitude": 116.198733
  },
  {
    "id": 9,
    "name": "Paris",
    "latitude": 48.866667,
    "longitude": 2.333333
  },
  {
    "id": 10,
    "name": "Singapore",
    "latitude": 1.2930556,
    "longitude": 103.8558333
  },
  {
    "id": 11,
    "name": "Montreal",
    "latitude": 45.5,
    "longitude": -73.583333
  },
  {
    "id": 12,
    "name": "Sydney",
    "latitude": -33.861481,
    "longitude": 151.205475
  },
  {
    "id": 13,
    "name": "Bangkok",
    "latitude": -6.6952,
    "longitude": 111.5409
  },
  {
    "id": 14,
    "name": "Guadalajara",
    "latitude": 32.544167,
    "longitude": -114.864722
  },
  {
    "id": 15,
    "name": "Miami",
    "latitude": 25.7738889,
    "longitude": -80.1938889
  }
]
```


#### Get a city 

+ **URL:** `/city/:id`

+ **Method:** GET

+ **Sample Call:** 

```
 curl 'http://localhost:8081/city/1' 
```

+ **Success Response:** 200 OK

+ **Sample Response Body:** 
```json
{
  "id": 1,
  "name": "Tokyo",
  "latitude": 35.685,
  "longitude": 139.751389
}
```

##### Delete a city 
+ **URL:** `/city/:id`

+ **Method:** DELETE

+ **Sample Call:** 

``` 
curl -X DELETE 'http://localhost:8081/city/10'
```

+ **Success Response:** 200 OK

#### Add a city 

+ **URL:** `/city`

+ **Method:** POST

+ **Request Body:** `{"name":"Mexico City","latitude":19.42847,"longitude":-99.12766}`

+ **Sample Call:** 

``` 
curl -H "Content-Type: application/json" -X POST -d '{"name":"Mexico City","latitude":19.42847,"longitude":-99.12766}' 'http://localhost:8081/city' 
```

+ **Success Response:** 201 CREATED

+ **Sample Response Body:** 
```
577227762T
```

#### Update a city 

+ **URL:** `/city`

+ **Method:** PUT

+ **Request Body:** `{"id":1,"name":"Osaka","latitude":34.6937,"longitude":135.5023}`

+ **Sample Call:** 

``` 
curl -X PUT -H "Content-Type: application/json" -d '{"id":1,"name":"Osaka","latitude":34.6937,"longitude":135.5023}' 'http://localhost:8081/city' 
```

+ **Success Response:** 201 CREATED

#### Get distance between two cities

+ **URL:** `/city/distance`

+ **Method:** GET

+ **Sample Call:** 

``` 
 curl 'http://localhost:8081/city/distance?from=1&to=3'
```

+ **URL params:** `from=[fromId], to=[toId]`
 
+ **Success Response:** 200 OK

+ **Sample Response Body:** 
```
10343.813377282666
```
