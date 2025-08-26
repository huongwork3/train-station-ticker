-- Sample data for Train Station Database
-- This populates our database with realistic train schedule data

USE train_station_db;

-- Insert sample trains
INSERT INTO trains (train_number, train_name, route) VALUES
('T101', 'Express Boston', 'New York - Boston'),
('T202', 'Metro Chicago', 'Boston - Chicago'),
('T303', 'Coastal Express', 'New York - Miami'),
('T404', 'Mountain View', 'Chicago - Denver'),
('T505', 'City Connector', 'Boston - Philadelphia'),
('T606', 'Night Rider', 'Philadelphia - Washington DC'),
('T707', 'Morning Glory', 'Washington DC - Atlanta'),
('T808', 'Sunset Limited', 'Atlanta - New Orleans'),
('T909', 'Prairie Wind', 'Chicago - Minneapolis'),
('T1010', 'Ocean Breeze', 'Miami - Tampa');

-- Insert today's schedule (using CURDATE() for current date)
INSERT INTO schedules (train_id, destination, departure_time, arrival_time, platform, status, delay_minutes, schedule_date) VALUES
-- Morning departures
(1, 'Boston', '08:30:00', '12:30:00', 'A1', 'ON_TIME', 0, CURDATE()),
(2, 'Chicago', '09:15:00', '15:45:00', 'B2', 'DELAYED', 15, CURDATE()),
(3, 'Miami', '10:00:00', '18:30:00', 'A3', 'ON_TIME', 0, CURDATE()),
(4, 'Denver', '11:30:00', '19:15:00', 'C1', 'ON_TIME', 0, CURDATE()),
(5, 'Philadelphia', '12:00:00', '14:30:00', 'B1', 'DELAYED', 5, CURDATE()),

-- Afternoon departures
(6, 'Washington DC', '13:45:00', '16:20:00', 'A2', 'ON_TIME', 0, CURDATE()),
(7, 'Atlanta', '14:30:00', '20:15:00', 'C2', 'ON_TIME', 0, CURDATE()),
(8, 'New Orleans', '15:00:00', '23:45:00', 'B3', 'DELAYED', 25, CURDATE()),
(9, 'Minneapolis', '16:15:00', '22:30:00', 'A4', 'ON_TIME', 0, CURDATE()),
(10, 'Tampa', '17:30:00', '21:45:00', 'C3', 'ON_TIME', 0, CURDATE()),

-- Evening departures
(1, 'Boston', '18:30:00', '22:30:00', 'A1', 'ON_TIME', 0, CURDATE()),
(2, 'Chicago', '19:15:00', '01:45:00', 'B2', 'ON_TIME', 0, CURDATE()),
(3, 'Miami', '20:00:00', '04:30:00', 'A3', 'DELAYED', 10, CURDATE()),
(5, 'Philadelphia', '21:00:00', '23:30:00', 'B1', 'ON_TIME', 0, CURDATE()),
(6, 'Washington DC', '22:45:00', '01:20:00', 'A2', 'ON_TIME', 0, CURDATE());

-- Insert tomorrow's schedule for testing
INSERT INTO schedules (train_id, destination, departure_time, arrival_time, platform, status, delay_minutes, schedule_date) VALUES
(1, 'Boston', '08:30:00', '12:30:00', 'A1', 'ON_TIME', 0, DATE_ADD(CURDATE(), INTERVAL 1 DAY)),
(2, 'Chicago', '09:15:00', '15:45:00', 'B2', 'ON_TIME', 0, DATE_ADD(CURDATE(), INTERVAL 1 DAY)),
(3, 'Miami', '10:00:00', '18:30:00', 'A3', 'ON_TIME', 0, DATE_ADD(CURDATE(), INTERVAL 1 DAY)),
(4, 'Denver', '11:30:00', '19:15:00', 'C1', 'ON_TIME', 0, DATE_ADD(CURDATE(), INTERVAL 1 DAY)),
(5, 'Philadelphia', '12:00:00', '14:30:00', 'B1', 'ON_TIME', 0, DATE_ADD(CURDATE(), INTERVAL 1 DAY));
