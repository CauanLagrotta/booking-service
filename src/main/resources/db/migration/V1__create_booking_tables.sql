CREATE TABLE booking (
    id BIGINT NOT NULL AUTO_INCREMENT,
    saloon_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    start_time DATETIME(6) NOT NULL,
    end_time DATETIME(6) NOT NULL,
    status TINYINT NOT NULL DEFAULT 0,
    total_price INT NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_booking_saloon_id (saloon_id),
    INDEX idx_booking_customer_id (customer_id),
    INDEX idx_booking_start_time (start_time),
    INDEX idx_booking_end_time (end_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS booking_seq (next_val BIGINT) ENGINE=InnoDB;
INSERT INTO booking_seq VALUES (1);

CREATE TABLE booking_service_ids (
    booking_id BIGINT NOT NULL,
    service_ids BIGINT NOT NULL,
    PRIMARY KEY (booking_id, service_ids),
    INDEX idx_booking_service_ids_service (service_ids),
    CONSTRAINT fk_booking_service_ids_booking FOREIGN KEY (booking_id) REFERENCES booking(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
