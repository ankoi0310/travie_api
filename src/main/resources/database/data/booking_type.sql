INSERT INTO booking_type (name, description, check_in_time, check_out_time, is_hourly, is_overnight, is_by_day, unit)
VALUES ('Theo giờ', 'Từ 07:00 đến 22:00', '07:00:00', '22:00:00', 1, 0, 0, 'HOUR'),
       ('Qua đêm', 'Từ 22:00 đến 11:00', '22:00:00', '11:00:00', 0, 1, 0, 'NIGHT'),
       ('Theo ngày', 'Từ 15:00 đến 11:00', '15:00:00', '11:00:00', 0, 0, 1, 'DAY');
