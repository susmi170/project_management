CREATE TABLE IF NOT EXISTS PROJECT(
  ID int not null AUTO_INCREMENT,
  NAME varchar(100) not null,
  DESCRIPTION varchar(100) not null,
  STARTDATE DATE not null,
  ENDDATE DATE not null,
  STATUS varchar(100) not null,
  CREATEDBY varchar(100) not null,
  UPDATEDBY varchar(100) not null,
  PRIMARY KEY ( ID )
);