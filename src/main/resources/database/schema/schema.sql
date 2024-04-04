CREATE TABLE address
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    date_created datetime NULL,
    date_updated datetime NULL,
    address      VARCHAR(255) NULL,
    ward         VARCHAR(255) NULL,
    district     VARCHAR(255) NULL,
    city         VARCHAR(255) NULL,
    country      VARCHAR(255) NULL,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE app_user
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    date_created datetime NULL,
    date_updated datetime NULL,
    username     VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    `role`       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
);

CREATE TABLE booking
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    date_created datetime NULL,
    date_updated datetime NULL,
    hotel_id     BIGINT NULL,
    CONSTRAINT pk_booking PRIMARY KEY (id)
);

CREATE TABLE booking_type
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    date_created  datetime NULL,
    date_updated  datetime NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    image_url     VARCHAR(255) NULL,
    CONSTRAINT pk_booking_type PRIMARY KEY (id)
);

CREATE TABLE facility
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    date_created datetime NULL,
    date_updated datetime NULL,
    name         VARCHAR(255) NULL,
    image_url    VARCHAR(255) NULL,
    CONSTRAINT pk_facility PRIMARY KEY (id)
);

CREATE TABLE hotel
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    date_created  datetime NULL,
    date_updated  datetime NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    address_id    BIGINT NULL,
    CONSTRAINT pk_hotel PRIMARY KEY (id)
);

CREATE TABLE hotel_image
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    date_created  datetime NULL,
    date_updated  datetime NULL,
    image_url     VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    hotel_id      BIGINT NULL,
    CONSTRAINT pk_hotel_image PRIMARY KEY (id)
);

CREATE TABLE hotel_review
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    date_created datetime NULL,
    date_updated datetime NULL,
    hotel_id     BIGINT NULL,
    user_id      BIGINT NULL,
    rating       INT NOT NULL,
    content      VARCHAR(255) NULL,
    CONSTRAINT pk_hotel_review PRIMARY KEY (id)
);

CREATE TABLE room
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    date_created datetime NULL,
    date_updated datetime NULL,
    name         VARCHAR(255) NULL,
    hotel_id     BIGINT NULL,
    CONSTRAINT pk_room PRIMARY KEY (id)
);

CREATE TABLE user_info
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    date_created datetime NULL,
    date_updated datetime NULL,
    name         VARCHAR(255) NULL,
    address      VARCHAR(255) NULL,
    phone        VARCHAR(255) NULL,
    email        VARCHAR(255) NULL,
    avatar       VARCHAR(255) NULL,
    CONSTRAINT pk_user_info PRIMARY KEY (id)
);

ALTER TABLE app_user
    ADD CONSTRAINT uc_app_user_username UNIQUE (username);

ALTER TABLE hotel
    ADD CONSTRAINT uc_hotel_address UNIQUE (address_id);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_HOTEL FOREIGN KEY (hotel_id) REFERENCES hotel (id);

ALTER TABLE hotel_image
    ADD CONSTRAINT FK_HOTEL_IMAGE_ON_HOTEL FOREIGN KEY (hotel_id) REFERENCES hotel (id);

ALTER TABLE hotel
    ADD CONSTRAINT FK_HOTEL_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE hotel_review
    ADD CONSTRAINT FK_HOTEL_REVIEW_ON_HOTEL FOREIGN KEY (hotel_id) REFERENCES hotel (id);

ALTER TABLE hotel_review
    ADD CONSTRAINT FK_HOTEL_REVIEW_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);

ALTER TABLE room
    ADD CONSTRAINT FK_ROOM_ON_HOTEL FOREIGN KEY (hotel_id) REFERENCES hotel (id);
