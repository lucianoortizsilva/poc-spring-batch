DROP TABLE IF EXISTS netflix_catalog;

DROP TABLE IF EXISTS flight;

CREATE TABLE netflix_catalog (
    id VARCHAR(5),
    title TEXT,
	"cast" TEXT,
	country TEXT,
	releaseYear TEXT,
	duration TEXT,
	listedIn TEXT,
    PRIMARY KEY (id)
); 

CREATE TABLE flight (
    id VARCHAR(33),
    flightDate TEXT,
	startingAirport TEXT,
	destinationAirport TEXT,
	segmentsAirlineName TEXT,
    PRIMARY KEY (id)
);