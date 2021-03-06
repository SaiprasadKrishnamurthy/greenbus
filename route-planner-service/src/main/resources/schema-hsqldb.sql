CREATE TABLE IF NOT EXISTS route_info(
   id INTEGER  NOT NULL PRIMARY KEY
  ,busId      INTEGER NOT NULL
  ,busNo     VARCHAR(30)
  ,origin      VARCHAR(100)
  ,destination      VARCHAR(100)
  ,frequencyinminutes      INTEGER NOT NULL
  ,legSeqNo      INTEGER NOT NULL
  ,maxTravelTimeMinutes      INTEGER NOT NULL
  ,distanceInKm      INTEGER NOT NULL
  ,stageSeqId      INTEGER NOT NULL
  ,busType  VARCHAR(50)
  ,disabled    BIT NOT NULL
);

CREATE TABLE IF NOT EXISTS stage_pricing(
   id INTEGER  NOT NULL PRIMARY KEY
  ,busType            VARCHAR(50) NOT NULL
  ,farePerStage        DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS bus_stop(
   id INTEGER  NOT NULL PRIMARY KEY
  ,name      VARCHAR(100) NOT NULL
  ,city      VARCHAR(20)
  ,pincode    VARCHAR(10)
  ,landmark    VARCHAR(100)
  ,latlong      VARCHAR(30)
);

CREATE INDEX RouteInfo_Origin ON route_info (origin);
CREATE INDEX RouteInfo_Destination ON route_info (destination);
CREATE INDEX RouteInfo_busId ON route_info (busId);
CREATE INDEX RouteInfo_busNo ON route_info (busNo);
CREATE INDEX RouteInfo_busType ON route_info (busType);
CREATE INDEX bus_stop_name ON bus_stop (name);

CREATE INDEX StagePricing_busType ON stage_pricing (busType);

