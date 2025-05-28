DROP TABLE IF EXISTS netflix_catalog_documentary;

DROP TABLE IF EXISTS netflix_catalog_comedie;

CREATE TABLE netflix_catalog_documentary (
    id VARCHAR(5),
    title TEXT,
	"cast" TEXT,
	country TEXT,
	releaseYear TEXT,
	duration TEXT,
    PRIMARY KEY (id)
); 

CREATE TABLE netflix_catalog_comedie (
    id VARCHAR(5),
    title TEXT,
	"cast" TEXT,
	country TEXT,
	releaseYear TEXT,
	duration TEXT,
    PRIMARY KEY (id)
); 
