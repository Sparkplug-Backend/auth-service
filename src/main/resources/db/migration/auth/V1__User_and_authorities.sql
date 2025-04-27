CREATE TABLE base_user
(
    id            SERIAL         NOT NULL,
    username      VARCHAR(255)   NOT NULL,
    email         VARCHAR(255),
    phone_number  VARCHAR(255),
    password_hash VARCHAR(255)

        CHECK (email IS NOT NULL OR phone_number IS NOT NULL),

    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE admin
(
    id BIGINT NOT NULL REFERENCES base_user(id) ON DELETE CASCADE,

    CONSTRAINT pk_admin PRIMARY KEY (id)
);

CREATE TABLE admin_authority
(
    id   SERIAL NOT NULL,
    name VARCHAR(255),

    CONSTRAINT pk_admin_authority PRIMARY KEY (id)
);

CREATE TABLE admin_authority_admin
(
    admin_authority_id BIGINT NOT NULL,
    admin_id           BIGINT NOT NULL,

    PRIMARY KEY (admin_authority_id, admin_id),
    FOREIGN KEY (admin_authority_id) REFERENCES admin_authority(id) ON DELETE CASCADE,
    FOREIGN KEY (admin_id)           REFERENCES admin(id)           ON DELETE CASCADE
);

CREATE TABLE client
(
    id BIGINT NOT NULL REFERENCES base_user(id),

    CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE client_authority
(
    id   SERIAL NOT NULL,
    name VARCHAR(255),

    CONSTRAINT pk_client_authority PRIMARY KEY (id)
);

CREATE TABLE client_authority_client
(
    client_authority_id BIGINT NOT NULL,
    client_id           BIGINT NOT NULL,

    PRIMARY KEY (client_authority_id, client_id),
    FOREIGN KEY (client_authority_id) REFERENCES client_authority(id) ON DELETE CASCADE,
    FOREIGN KEY (client_id)           REFERENCES client(id)           ON DELETE CASCADE
);

ALTER TABLE base_user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE base_user
    ADD CONSTRAINT uc_user_phone_number UNIQUE (phone_number);

ALTER TABLE base_user
    ADD CONSTRAINT uc_user_username UNIQUE (username);

INSERT INTO client_authority(name) VALUES ('CLIENT_BASIC');
INSERT INTO client_authority(name) VALUES ('CLIENT_PLUS');

INSERT INTO admin_authority(name) VALUES ('ADMIN_BASIC');
INSERT INTO admin_authority(name) VALUES ('ADMIN_MANAGER');
INSERT INTO admin_authority(name) VALUES ('ADMIN_GOD');