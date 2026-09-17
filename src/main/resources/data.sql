-- PROJECT FIREFLY - RESTART-SAFE SAMPLE DATA
-- MySQL version.
-- INSERT IGNORE prevents duplicate primary-key / join-table rows on restart.
-- Existing rows with the same IDs are left unchanged.


INSERT IGNORE INTO artist (artist_id, artist_name) VALUES
(1,'Bruno Mars'),(2,'Lady Gaga'),(3,'The Weeknd'),(4,'Dua Lipa'),
(5,'Ed Sheeran'),(6,'Ariana Grande'),(7,'Drake'),(8,'Billie Eilish'),
(9,'Post Malone'),(10,'SZA'),(11,'Kendrick Lamar'),(12,'Doja Cat');

INSERT IGNORE INTO album (album_id, album_name, artist_id, release_date) VALUES
(1,'24K Magic',1,'2016-11-18'),
(2,'Mayhem',2,'2025-03-07'),
(3,'After Hours',3,'2020-03-20'),
(4,'Future Nostalgia',4,'2020-03-27'),
(5,'Divide',5,'2017-03-03'),
(6,'Sweetener',6,'2018-08-17'),
(7,'Scorpion',7,'2018-06-29'),
(8,'Happier Than Ever',8,'2021-07-30'),
(9,'Hollywood''s Bleeding',9,'2019-09-06'),
(10,'SOS',10,'2022-12-09'),
(11,'DAMN.',11,'2017-04-14'),
(12,'Planet Her',12,'2021-06-25');

INSERT IGNORE INTO songs (song_id, song_name, duration, album_id) VALUES
(1,'24K Magic',226,1),
(2,'That''s What I Like',206,1),
(3,'Versace on the Floor',261,1),
(4,'Disease',229,2),
(5,'Abracadabra',223,2),
(6,'Blinding Lights',200,3),
(7,'Save Your Tears',215,3),
(8,'After Hours',361,3),
(9,'Don''t Start Now',183,4),
(10,'Physical',193,4),
(11,'Levitating',203,4),
(12,'Shape of You',233,5),
(13,'Perfect',263,5),
(14,'Galway Girl',170,5),
(15,'No Tears Left to Cry',205,6),
(16,'God Is a Woman',197,6),
(17,'Breathin',198,6),
(18,'God''s Plan',198,7),
(19,'Nice for What',210,7),
(20,'In My Feelings',217,7),
(21,'Happier Than Ever',298,8),
(22,'Therefore I Am',174,8),
(23,'Your Power',245,8),
(24,'Circles',215,9),
(25,'Hollywood''s Bleeding',156,9),
(26,'Goodbyes',175,9),
(27,'Kill Bill',153,10),
(28,'Snooze',201,10),
(29,'Nobody Gets Me',180,10),
(30,'HUMBLE.',177,11),
(31,'DNA.',185,11),
(32,'LOVE.',213,11),
(33,'Woman',173,12),
(34,'Need to Know',210,12),
(35,'Get Into It (Yuh)',138,12);

INSERT IGNORE INTO song_artists (song_id, artist_id) VALUES
(1,1),(2,1),(3,1),(4,2),(5,2),(6,3),(7,3),(8,3),
(9,4),(10,4),(11,4),(12,5),(13,5),(14,5),(15,6),(16,6),
(17,6),(18,7),(19,7),(20,7),(21,8),(22,8),(23,8),(24,9),
(25,9),(26,9),(27,10),(28,10),(29,10),(30,11),(31,11),(32,11),
(33,12),(34,12),(35,12);

INSERT IGNORE INTO song_genres (song_id, genre) VALUES
(1,'POP'),(1,'FUNK'),(2,'POP'),(2,'RNB'),(3,'RNB'),
(4,'POP'),(4,'ELECTRONIC'),(5,'POP'),(5,'ELECTRONIC'),
(6,'POP'),(6,'ELECTRONIC'),(7,'POP'),(7,'RNB'),
(8,'RNB'),(8,'ELECTRONIC'),(9,'POP'),(9,'ELECTRONIC'),
(10,'POP'),(10,'ELECTRONIC'),(11,'POP'),(12,'POP'),(13,'POP'),
(14,'POP'),(15,'POP'),(16,'POP'),(16,'RNB'),(17,'POP'),
(18,'RAP'),(18,'HIP_HOP'),(19,'RAP'),(19,'HIP_HOP'),
(20,'RAP'),(20,'RNB'),(21,'POP'),(21,'INDIE'),(22,'POP'),
(23,'INDIE'),(24,'POP'),(25,'HIP_HOP'),(26,'HIP_HOP'),(26,'POP'),
(27,'RNB'),(28,'RNB'),(29,'RNB'),(29,'POP'),
(30,'RAP'),(30,'HIP_HOP'),(31,'RAP'),(31,'HIP_HOP'),
(32,'RAP'),(32,'RNB'),(33,'POP'),(33,'RNB'),
(34,'RAP'),(34,'RNB'),(35,'RAP'),(35,'HIP_HOP');
