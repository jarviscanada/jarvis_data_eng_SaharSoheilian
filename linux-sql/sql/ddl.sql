-- switch to 'host_agent' database
-- ??

CREATE TABLE IF NOT EXISTS PUBLIC.host_info (
    id                      SERIAL NOT NULL,
    hostname                VARCHAR NOT NULL,
    PRIMARY KEY (id),        -- primary key constraint
    UNIQUE(hostname),       -- unique hostname constraint
	cpu_number				INT NOT NULL,
    cpu_architecture        VARCHAR NOT NULL,
    cpu_model               VARCHAR NOT NULL,
    cpu_mhz                 FLOAT NOT NULL,
    L2_cache                INT NOT NULL,
    total_mem               INT NOT NULL,
    "timestamp"             TIMESTAMP NOT NULL
  );

CREATE TABLE IF NOT EXISTS PUBLIC.host_usage (
    id                      SERIAL NOT NULL,
	PRIMARY KEY(id),
    "timestamp"             TIMESTAMP NOT NULL,
    host_id                 SERIAL NOT NULL,
	memory_free             SMALLINT NOT NULL,
	cpu_idle                SMALLINT NOT NULL,
	cpu_kernel              SMALLINT NOT NULL,
	disk_io                 SMALLINT NOT NULL,
	disk_available          INT NOT NULL,
	CONSTRAINT fk_host          -- add foreign key constraint
		FOREIGN KEY (host_id)
			REFERENCES host_info(id)
  );

