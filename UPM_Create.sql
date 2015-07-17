-- tables
-- Table ADDRESS
CREATE TABLE ADDRESS (
    ADDRESS_ID int    NOT NULL ,
    HOUSE_NO int    NULL ,
    STREET varchar(256)    NULL ,
    CITY varchar(256)    NULL ,
    STATE_ID int    NULL ,
    COUNTRY varchar(256)    NULL ,
    LOGIN_ID varchar(20)    NOT NULL ,
    CONSTRAINT ADDRESS_pk PRIMARY KEY (ADDRESS_ID)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Table STATES
CREATE TABLE STATES (
    STATE_ID int    NOT NULL ,
    STATE_NAME varchar(256)    NOT NULL ,
    CONSTRAINT STATES_pk PRIMARY KEY (STATE_ID)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Table USER
CREATE TABLE USER (
    LOGIN_ID varchar(20)    NOT NULL ,
    USER_NAME varchar(255)    NULL ,
    EMAIL_ID varchar(255)    NULL ,
    PASSWORD varchar(20)    NULL ,
    CONSTRAINT USER_pk PRIMARY KEY (LOGIN_ID)
);

-- Table USER_OTP
CREATE TABLE USER_OTP (
    ID int    NOT NULL ,
    LOGIN_ID varchar(20)    NOT NULL ,
    CREATED_DATE timestamp    NOT NULL ,
    OTP varchar(100)    NOT NULL ,
    CONSTRAINT USER_OTP_pk PRIMARY KEY (ID)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE "USER_IMAGES" (
 "LOGIN_ID"  varchar(128)  ,
  "IMAGE_DATA"  blob,
  PRIMARY KEY (`LOGIN_ID`)
)



-- End of file.



ALTER TABLE USER_OTP
ADD FOREIGN KEY (LOGIN_ID) REFERENCES USER (LOGIN_ID);

ALTER TABLE ADDRESS
ADD FOREIGN KEY (STATE_ID) REFERENCES STATES (STATE_ID);

ALTER TABLE ADDRESS
ADD FOREIGN KEY (LOGIN_ID) REFERENCES USER (LOGIN_ID);


-- Sequence
create sequence upmseq
start with 1 
nomaxvalue; 
-- State creations scripts

INSERT INTO STATES VALUES(1,'Andaman and Nicobar Islands');
INSERT INTO STATES VALUES(2,'Andhra Pradesh');
INSERT INTO STATES VALUES(3,'Arunachal Pradesh');
INSERT INTO STATES VALUES(4,'Assam');
INSERT INTO STATES VALUES(5,'Bihar');
INSERT INTO STATES VALUES(6,'Chandigarh');
INSERT INTO STATES VALUES(7,'Chhattisgarh');
INSERT INTO STATES VALUES(8,'Dadra and Nagar Haveli');
INSERT INTO STATES VALUES(9,'Daman and Diu');
INSERT INTO STATES VALUES(10,'Delhi');
INSERT INTO STATES VALUES(11,'Goa');
INSERT INTO STATES VALUES(12,'Gujarat');
INSERT INTO STATES VALUES(13,'Haryana');
INSERT INTO STATES VALUES(14,'Himachal Pradesh');
INSERT INTO STATES VALUES(15,'Jammu and Kashmir');
INSERT INTO STATES VALUES(16,'Jharkhand');
INSERT INTO STATES VALUES(17,'Karnataka');
INSERT INTO STATES VALUES(18,'Kerala');
INSERT INTO STATES VALUES(19,'Lakshadweep');
INSERT INTO STATES VALUES(20,'Madhya Pradesh');
INSERT INTO STATES VALUES(21,'Maharashtra');
INSERT INTO STATES VALUES(22,'Manipur');
INSERT INTO STATES VALUES(23,'Meghalaya');
INSERT INTO STATES VALUES(24,'Mizoram');
INSERT INTO STATES VALUES(25,'Nagaland');
INSERT INTO STATES VALUES(26,'Orissa');
INSERT INTO STATES VALUES(27,'Pondicherry');
INSERT INTO STATES VALUES(28,'Punjab');
INSERT INTO STATES VALUES(29,'Rajasthan');
INSERT INTO STATES VALUES(30,'Sikkim');
INSERT INTO STATES VALUES(31,'Tamil Nadu');
INSERT INTO STATES VALUES(32,'Tripura');
INSERT INTO STATES VALUES(33,'Uttaranchal');
INSERT INTO STATES VALUES(34,'Uttar Pradesh');
INSERT INTO STATES VALUES(35,'West Bengal');
commit;