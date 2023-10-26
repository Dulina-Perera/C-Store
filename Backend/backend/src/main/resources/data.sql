\c "cstore";

DELETE FROM "order_item";
DELETE FROM "order";
DELETE FROM "cart_item";
DELETE FROM "cart";
DELETE FROM "user_address";
DELETE FROM "user_contact";
DELETE FROM "registered_user";
DELETE FROM "user";
DELETE FROM "inventory";
DELETE FROM "warehouse_contact";
DELETE FROM "warehouse";
DELETE FROM "varies_on";
DELETE FROM "variant";
DELETE FROM "property";
DELETE FROM "belongs_to";
DELETE FROM "product_image";
DELETE FROM "product";
DELETE FROM "image";
DELETE FROM "sub_category";
DELETE FROM "category";

-- ---------------------------------------------------------------------------------------------------------------------
--  "category"

INSERT INTO "category" ("category_name", "category_description") VALUES
	('Electronics', 'A category encompassing a wide range of electronic devices, gadgets, and accessories, offering solutions for various needs and applications.'),
	('Toys', 'An assortment of playthings, including action figures, dolls, puzzles, and more, designed to entertain and inspire imagination in children and adults alike.'),
	('Televisions & Displays', 'Featuring high-quality LCD and LED TVs, this category is dedicated to delivering stunning visual experiences in your home.'),
	('Mobile Devices', 'Explore the latest in mobile technology, including smartphones, tablets, and related accessories, designed for on-the-go convenience and connectivity.'),
	('Home & Kitchen Electronics', 'Discover electronic gadgets and accessories tailored for your home and kitchen, from smart appliances to lifestyle-enhancing devices.'),
	('Toys for Children', 'An exciting range of toys designed specifically for kids, including remote-control cars, video games, and creative playthings to ignite their imagination.'),
	('Laptops & Computers', 'From powerful laptops to computer accessories, this category offers top-quality computing solutions to meet your diverse needs.'),
	('Outdoor & Adventure Electronics', 'Equip yourself for outdoor adventures with electronic gadgets such as camping gear, GPS devices, and equipment built for exploration.'),
	('Computer Accessories & Peripherals', 'Find a wide selection of accessories related to computers, including keyboards, mice, monitors, and peripherals to enhance your computing experience.'),
	('Health & Medical Electronics', 'Explore electronic medical equipment suitable for healthcare professionals and individuals, including vital sign monitors and diagnostic devices.'),
	('Audio & Entertainment', 'Immerse yourself in high-quality audio experiences with a range of headphones and audio accessories designed for superior sound.'),
	('Smart Home & Automation', 'Smart home technology that automates and enhances your living space, offering convenience and efficiency in daily living.'),
	('Remote-controlled Toys', 'Dive into a world of remote-controlled toys, including cars, drones, helicopters, and boats, designed for thrilling entertainment.'),
	('Dolls, Figures & Board Games', 'Fuel imagination with a diverse selection of dolls, action figures, puzzles, and board games, perfect for quality family time.'),
	('Tablets & E-Readers', 'Browse a collection of tablets and e-readers for reading, browsing, and entertainment on the go, all in one category.'),
	('Electronic Car Accessories', 'Specialized electronic accessories designed for enhancing your driving experience, including dash cameras and in-car gadgets.'),
	('Educational Electronics', 'A category dedicated to electronics and kits designed for educational purposes, inspiring learning and creativity.');

-- SELECT *
-- FROM "category"
-- ORDER BY "category_id" ASC;

-- ---------------------------------------------------------------------------------------------------------------------
-- "sub Category"

INSERT INTO "sub_category" ("category_id", "sub_category_id") VALUES
	(1,3),
	(1,4),
	(1,5),
	(2,6),
	(1,7),
	(1,8),
	(1,9),
	(1,10),
	(1,11),
	(1,12),
	(2,13),
	(2,14),
	(1,15),
	(1,16),
	(1,17);

-- SELECT a."category_name" AS "category", c2."category_name" AS "sub_category"
-- FROM ("category" AS c1 NATURAL RIGHT OUTER JOIN "sub_category" AS sc) AS a LEFT OUTER JOIN "category" AS c2 ON (a."sub_category_id" = c2."category_id")
-- ORDER BY a."category_name";

-- ---------------------------------------------------------------------------------------------------------------------
-- "image"



-- SELECT *
-- FROM "image";

-- ---------------------------------------------------------------------------------------------------------------------
-- "product"

INSERT INTO "product" ("product_name", "base_price", "brand", "description", "image_url") VALUES
	('iphone 14 pro',359900.00,'Apple','A high-end smartphone with advanced features.','static/images/1.jfif'),
	('Macbook Pro',429900.00,'Apple','Powerful laptop for professionals','static/images/2.jfif'),
	('TV',150000.00,'Samsung','4K Ultra HD OLED TV for stunning visuals','static/images/3.jfif'),
	('Toy gun',1200.00,'Gunhub','A toy gun for children','static/images/4.jfif'),
	('Galaxy M04 phone',45000.00,null,'Samsung,One year software and hardware warranty AND the charger included','static/images/5.jfif'),
	('Electronic Digital Weighing Kitchen Scale',1399.00,'Tare','Large LCD Display with automatic zero resetting + 2 Free batteries','static/image/6.jfif'),
	('TV', 89900.00,'Innovex','LED Frameless TV With 3 Years of Company Warranty','static/image/7.jfif'),
	('Quadcopter Drone With Camera',149000.00,'Hubsan','RC Foldable wide angle Full HD,Ultra HD,4K Camera','static/image/8.jfif'),
	('Micro SD SDCC Card',4000.00,'Kingston Technology','Ultra Large Capacity High Speed 2TB-1TB USB Drive Micro SD','static/image/9.jfif'),
	('Bluetooth Earphone with Ear Hook',9450.00,'FiiO','Suitable for Running Sports. Stereo Buttons Control With Microphone Wireless Headphones','static/image/10.jfif'),
	('Toy FARM TRACTOR',1650.00,null,'Made of top-grade plastic - for kids','static/image/11.jfif'),
	('Simulation Dinosaur',2450.00,'Wild Safari','Lifelike Mosasaurus Plesiosaur Dunkleosteus Action Figures','static/image/12.jfif'),
	('Gorilla female Monkey Model',540.00,'Schleich','Squeaky Sound Toy,Children Toy,Action Figure','static/image/13.jfif'),
	('High speed RC Car',7500.00,'Rastar','Remote Control AE86 Model GTR Vehicle RC Racing Cars','static/image/14.jfif'),
	('Bluetooth speaker',16600.00,'SONY','waterproof portable wireless','static/image/15.jfif'),
	('Smart Watch',3500.00,'Huawei','Smart Watch for Men, Fitness Tracker','static/image/16.jfif'),
	('Dash camera',9900.00,'Xiaomi','3 Channel Dash Cam 2K+1080P With GPS, WiFi','static/image/17.jfif'),
	('Arduino kit',3600.00,'Grove','Arduino starter kit, improved version','static/image/18.jfif'),
	('Bluetooth receiver',8100.00,'LDAC','Qualcomm QCC5125 lossless Bluetooth 5.1 receiver','static/image/19.jfif'),
	('Smart stunt dog Toy',11696.00,'Sigma','RC Robot Dog Voice Command Programmable Touch-sense','static/image/20.jfif'),
	('Six claw dancing robot',3500.00,'Mattel','Limited Edition children Toys Dance Music Robots','static/image/21.jfif'),
	('Unicorn plush toy',6700.00,'K-Nex','Soft Stuffed Toy Giant Size Cartoon Dolls Animal Horse','static/image/22.jfif'),
	('Football Table Interactive Games',12600.00,'Kidsbele','Tabletop Soccer Pinball Toys Classic Parent-Child Interactive','static/image/23.jfif'),
	('RC car Lambogini',4699.00,'ARRMA','2.4G Radio Remote Control Sports Cars For Children Racing High Speed Drive','static/image/24.jfif'),
	('Electric Domino Train Toy',1300.00,null,'Lego,Colorful Electric Domino Train Toys Set with 100Pcs Building Blocks','static/image/25.jfif'),
	('Luminous Fidget Spinner',750.00,null,'Light Up Changeable Hand Spinners Adult Glowing Spiner Stress Relief Toys','static/image/26.jfif'),
	('Electronic Unlock Installation',3000.00,'Fengweima','Electronic Cylinder Tuya Unlock Installation Keyless Tuya Fingerprint Smart Password lock','static/image/27.jfif'),
	('Wireless headset',9900.00,'Saramonic','Wireless full duplex gaming headset','static/image/28.jfif'),
	('Electronic thermometer',1600.00,'Kinsa','Digital Forehead Thermometer Electronic Contactless','static/image/29.jfif'),
	('Flying Disk spinning Gun',1549.00,'Kinsa','Digital Forehead Thermometer Electronic Contactless','static/image/30.jfif'),
	('Robot Vaccum',45000.00,'Shark','Shark AV993 IQ Robot Vacuum','static/image/31.jfif'),
	('Vivobook 15 X513 Laptop',122000.00,'Asus','Windows 10 Home - ASUS recommends Windows 10 Pro for business','static/image/32.jfif'),
	('LED SMART TV',419.99,'Sony','X77L 4K HDR LED','static/image/33.jfif'),
	('Gaming Mouse',89.99,'Corsair','M65 RGB ULTRA WIRELESS Tunable FPS','static/image/34.jfif'),
	('Mechnaical Gaming keyboard',99.99,'Corsair','K70 CORE RGB','static/image/35.jfif'),
	('Mini portable wireless mouse',550.00,'Razer','Minimum sound, Easy navigation, Connect and control upto 3 devices','static/image/36.jfif'),
	('Lenovo ideapad 3 Laptop',2000.00,'Lenovo','Has a slim body with immersive sound, A stylish laptop with excellent performance.','static/image/37.jfif'),
	('GloFX Xtra Lite Kaleidoscope Glasses',600.00,'GloFX','Plastic frame, Crystal lens, Lens width: 50 millimeters','static/image/38.jfif'),
	('Casio FX-991 Engineering/Scientific Calculator',4500.00,'Casio','Comes in black colour, super-fast calculation speed for high-stakes testing and for performing the most advanced mathematics','static/image/39.jfif'),
	('Fake Puff Cigarette',200.00,'Amscan','Enjoy the aesthetic of a real cigar without the smell or risks associated with actual smoke','static/image/40.jfif');

-- SELECT *
-- FROM "product"
-- ORDER BY "product_id";

-- ---------------------------------------------------------------------------------------------------------------------
-- "product Image"



-- SELECT *
-- FROM "product_image";

-- ---------------------------------------------------------------------------------------------------------------------
-- "belongs to"

INSERT INTO "belongs_to" ("category_id", "product_id") VALUES
	(4,1),
	(7,2),
	(3,3),
	(6,4),
	(4,5),
	(5,6),
	(3,7),
	(8,8),
	(13,8),
	(9,9),
	(11,10),
	(6,11),
	(6,12),
	(14,12),
	(6,13),
	(14,13),
	(6,14),
	(13,14),
	(11,15),
	(12,16),
	(16,17),
	(17,18),
	(9,19),
	(6,20),
	(13,20),
	(6,21),
	(6,22),
	(14,22),
	(14,23),
	(6,24),
	(13,24),
	(6,25),
	(2,26),
	(9,27),
	(11,28),
	(10,29),
	(1,30),
	(2,30),
	(12,31),
	(7,32),
	(3,33),
	(9,34),
	(9,35),
	(9,36),
	(7,37),
	(2,38),
	(17,39),
	(2,40);

-- SELECT *
-- FROM belongs_to;

-- ---------------------------------------------------------------------------------------------------------------------
-- "property"

INSERT INTO "property" ("property_name", "value", "image_url", "price_increment") VALUES
	('Color','Deep Purple',NULL,0.00),
	('Color','Gold',NULL,0.00),
	('Color','Silver',NULL,0.00),
	('Color','Space Black',NULL,0.00),
	('Storage','128GB',NULL,0.00),
	('Storage','256GB',NULL,20000.00),
	('Storage','512GB',NULL,50000.00),
	('Storage','1TB',NULL,120000.00),
	('Color','Midnight',NULL,0.00),
	('Color','Silver',NULL,0.00),
	('Color','Space grey',NULL,0.00),
	('Color','Starlight',NULL,0.00),
	('Storage','512GB',NULL,0.00),
	('Storage','1TB',NULL,40000.00),
	('Storage','2TB',NULL,70000.00),
	('Size','32 inches',NULL,0.00),
	('Size','40 inches',NULL,40000.00),
	('Size','55 inches',NULL,80000.00),
	('Size','85 inches',NULL,150000.00),
	('Color','Red',NULL,0.00),
	('Color','Green',NULL,0.00),
	('Color','Blue',NULL,0.00),
	('Color','Black',NULL,0.00),
	('Size','9 cm',NULL,0.00),
	('Size','15 cm',NULL,450.00),
	('Color','Blue',NULL,0.00),
	('Color','Gold',NULL,0.00),
	('Color','Mint green',NULL,0.00),
	('Color','White',NULL,0.00),
	('Storage','64GB',NULL,0.00),
	('Storage','128GB',NULL,8000.00),
	('RAM','4GB',NULL,0.00),
	('RAM','6GB',NULL,6000.00),
	('RAM','8GB',NULL,10000.00),
	('Max Scale','3kg',NULL,0.00),
	('Max Scale','6kg',NULL,1100.00),
	('Precision','0.05g',NULL,0.00),
	('Precision','0.01g',NULL,990.00),
	('Size','32 inches',NULL,0.00),
	('Size','40 inches',NULL,45000.00),
	('Size','65 inches',NULL,105500.00),
	('Color','White',NULL,0.00),
	('Color','Silver',NULL,0.00),
	('Camera Quality','Full HD',NULL,0.00),
	('Camera Quality','Ultra HD',NULL,40000.00),
	('Camera Quality','4k',NULL,89000.00),
	('Storage','8GB',NULL,0.00),
	('Storage','16GB',NULL,3000.00),
	('Storage','32GB',NULL,8800.00),
	('Storage','64GB',NULL,14500.00),
	('Storage','128GB',NULL,19000.00),
	('Color','Black',NULL,0.00),
	('Color','White',NULL,0.00),
	('Color','Pink',NULL,0.00),
	('Size','Mini',NULL,0.00),
	('Size','Large',NULL,1350.00),
	('Version','T-Rex',NULL,0.00),
	('Version','Pachysephalus',NULL,580.00),
	('Version','Carnosaur',NULL,480.00),
	('Size','Small',NULL,0.00),
	('Size','Medium',NULL,150.00),
	('Size','Large',NULL,250.00),
	('Color','Red',NULL,0.00),
	('Color','Green',NULL,0.00),
	('Drive','Rear',NULL,0.00),
	('Drive','4x4',NULL,0.00),
	('Color','Black',NULL,0.00),
	('Color','Pink',NULL,0.00),
	('Color','Black',NULL,0.00),
	('Color','Pink',NULL,0.00),
	('Color','Red',NULL,0.00),
	('Camera Quality','Full HD',NULL,0.00),
	('Camera Quality','Ultra HD',NULL,0.00),
	('Camera Quality','4K',NULL,0.00),
	('Size','Small',NULL,0.00),
	('Size','Large',NULL,0.00),
	('Color','Black',NULL,0.00),
	('Color','Dark Silver',NULL,0.00),
	('Range','25m',NULL,0.00),
	('Range','100m',NULL,0.00),
	('Color','Blue',NULL,0.00),
	('Color','Pink',NULL,0.00),
	('Color','Orange',NULL,0.00),
	('Color','Blue',NULL,0.00),
	('Color','White',NULL,0.00),
	('Color','Pink',NULL,0.00),
	('Color','Green',NULL,0.00),
	('Color','Pink',NULL,0.00),
	('Size','Yellow',NULL,0.00),
	('Size','Medium',NULL,2000.00),
	('Size','Large',NULL,6000.00),
	('Board Color','Blue',NULL,0.00),
	('Board Color','Green',NULL,0.00),
	('Number of pieces','12',NULL,0.00),
	('Number of pieces','20',NULL,1900.00),
	('Color','Yellow',NULL,0.00),
	('Color','Blue',NULL,0.00),
	('Range','50m',NULL,0.00),
	('Range','120m',NULL,1500.00),
	('Length','4m',NULL,0.00),
	('Length','7m',NULL,0.00),
	('Sign_boards','15',NULL,120.00),
	('Color','Red',NULL,0.00),
	('Color','Blue',NULL,0.00),
	('Color','Black',NULL,0.00),
	('Color','Black',NULL,0.00),
	('Color','Black',NULL,0.00),
	('Color','White',NULL,0.00),
	('Color','Pink',NULL,0.00),
	('Color','Grey',NULL,0.00),
	('Color','White',NULL,0.00),
	('Disk Color','Pink',NULL,0.00),
	('Disk Color','Yellow',NULL,0.00),
	('Disk Color','Blue',NULL,0.00),
	('Color','Black',NULL,0.00),
	('Color','Bespoke Black',NULL,0.00),
	('Color','Dreamy White',NULL,0.00),
	('Color','Cobalt Blue',NULL,0.00),
	('Storage','512GB',NULL,0.00),
	('Storage','1TB',NULL,16000.00),
	('RAM','8GB',NULL,7900.00),
	('RAM','16GB',NULL,16000.00),
	('Size','43 inches',NULL,0.00),
	('Size','50 inches',NULL,25000.00),
	('Size','65 inches',NULL,39000.00),
	('Size','75 inches',NULL,48000.00),
	('Size','85 inches',NULL,62000.00),
	('Color','Black',NULL,0.00),
	('Color','White',NULL,0.00),
	('Color','Black',NULL,0.00),
	('Color','Steel Grey',NULL,0.00),
	('Color','White',NULL,150.00),
	('Color','Black',NULL,0.00),
	('Memory','8/512 GB',NULL,0.00),
	('Memory','16/512 GB',NULL,1000.00),
	('Frame color','Pink',NULL,45.00),
	('Frame color','Gold',NULL,50.00),
	('Frame color','Black',NULL,0.00),
	('Model','FX-991CW',NULL,0.00),
	('Model','FX-991ES',NULL,3000.00),
	('Model','FX-991EX',NULL,5000.00),
	('Count','6',NULL,0.00),
	('Count','8',NULL,80.00),
	('Count','10',NULL,160.00);

-- SELECT *
-- FROM "property"
-- ORDER BY "property_id";

-- ---------------------------------------------------------------------------------------------------------------------
-- "variant"

INSERT INTO "variant" ("weight") VALUES
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(206.00),
	(2600.00),
	(2600.00),
	(2600.00),
	(2600.00),
	(2600.00),
	(2600.00),
	(2600.00),
	(2600.00),
	(2600.00),
	(2600.00),
	(2600.00),
	(2600.00),
	(8300.00),
	(12500.00),
	(18400.00),
	(23200.00),
	(400.00),
	(750.00),
	(400.00),
	(750.00),
	(400.00),
	(750.00),
	(400.00),
	(750.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(172.00),
	(1050.00),
	(1050.00),
	(1350.00),
	(1350.00),
	(10300.00),
	(15600.00),
	(18000.00),
	(5500.00),
	(5500.00),
	(5500.00),
	(5500.00),
	(5500.00),
	(5500.00),
	(25.00),
	(25.00),
	(25.00),
	(25.00),
	(25.00),
	(13.25),
	(13.25),
	(13.25),
	(560.00),
	(790.00),
	(600.00),
	(550.00),
	(410.00),
	(400.00),
	(520.00),
	(780.00),
	(2500.00),
	(2500.00),
	(2850.00),
	(2850.00),
	(1100.00),
	(1100.00),
	(1100.00),
	(1100.00),
	(1100.00),
	(250.00),
	(250.00),
	(250.00),
	(900.00),
	(1300.00),
	(190.00),
	(190.00),
	(210.00),
	(210.00),
	(1500.00),
	(1500.00),
	(800.00),
	(800.00),
	(800.00),
	(1100.00),
	(1900.00),
	(1900.00),
	(2500.00),
	(1100.00),
	(1900.00),
	(2500.00),
	(2500.00),
	(2500.00),
	(2500.00),
	(2500.00),
	(2400.00),
	(2400.00),
	(2400.00),
	(2400.00),
	(500.00),
	(800.00),
	(580.00),
	(880.00),
	(80.00),
	(80.00),
	(80.00),
	(160.00),
	(150.00),
	(150.00),
	(150.00),
	(35.00),
	(250.00),
	(250.00),
	(250.00),
	(6500.00),
	(2300.00),
	(2300.00),
	(2300.00),
	(2300.00),
	(2300.00),
	(2300.00),
	(2300.00),
	(2300.00),
	(2300.00),
	(2300.00),
	(2300.00),
	(2300.00),
	(5200.00),
	(7500.00),
	(12000.00),
	(16500.00),
	(19000.00),
	(250.00),
	(250.00),
	(850.00),
	(850.00),
	(200.00),
	(200.00),
	(600.00),
	(700.00),
	(75.00),
	(75.00),
	(75.00),
	(95.00),
	(98.00),
	(102.00),
	(150.00),
	(200.00),
	(240.00);

-- SELECT *
-- FROM "variant"
-- ORDER BY "variant_id";

-- ---------------------------------------------------------------------------------------------------------------------
--  "varies_on"

INSERT INTO "varies_on" ("product_id", "property_id", "variant_id") VALUES
	(1,1,1),
	(1,5,1),
	(1,1,2),
	(1,6,2),
	(1,1,3),
	(1,7,3),
	(1,1,4),
	(1,8,4),
	(1,2,5),
	(1,5,5),
	(1,2,6),
	(1,6,6),
	(1,2,7),
	(1,7,7),
	(1,2,8),
	(1,8,8),
	(1,3,9),
	(1,5,9),
	(1,3,10),
	(1,6,10),
	(1,3,11),
	(1,7,11),
	(1,3,12),
	(1,8,12),
	(1,4,13),
	(1,5,13),
	(1,4,14),
	(1,6,14),
	(1,4,15),
	(1,7,15),
	(1,4,16),
	(1,8,16),
	(2,9,17),
	(2,13,17),
	(2,9,18),
	(2,14,18),
	(2,9,19),
	(2,15,19),
	(2,10,20),
	(2,13,20),
	(2,10,21),
	(2,14,21),
	(2,10,22),
	(2,15,22),
	(2,11,23),
	(2,13,23),
	(2,11,24),
	(2,14,24),
	(2,11,25),
	(2,15,25),
	(2,12,26),
	(2,13,26),
	(2,12,27),
	(2,14,27),
	(2,12,28),
	(2,15,28),
	(3,16,29),
	(3,17,30),
	(3,18,31),
	(3,19,32),
	(4,20,33),
	(4,24,33),
	(4,20,34),
	(4,25,34),
	(4,21,35),
	(4,24,35),
	(4,21,36),
	(4,25,36),
	(4,22,37),
	(4,24,37),
	(4,22,38),
	(4,25,38),
	(4,23,39),
	(4,24,39),
	(4,23,40),
	(4,25,40),
	(5,26,41),
	(5,30,41),
	(5,32,41),
	(5,26,42),
	(5,30,42),
	(5,33,42),
	(5,26,43),
	(5,30,43),
	(5,34,43),
	(5,26,44),
	(5,31,44),
	(5,32,44),
	(5,26,45),
	(5,31,45),
	(5,33,45),
	(5,26,46),
	(5,31,46),
	(5,34,46),
	(5,27,47),
	(5,30,47),
	(5,32,47),
	(5,27,48),
	(5,30,48),
	(5,33,48),
	(5,27,49),
	(5,30,49),
	(5,34,49),
	(5,27,50),
	(5,31,50),
	(5,32,50),
	(5,27,51),
	(5,31,51),
	(5,33,51),
	(5,27,52),
	(5,31,52),
	(5,34,52),
	(5,28,53),
	(5,30,53),
	(5,32,53),
	(5,28,54),
	(5,30,54),
	(5,33,54),
	(5,28,55),
	(5,30,55),
	(5,34,55),
	(5,28,56),
	(5,31,56),
	(5,32,56),
	(5,28,57),
	(5,31,57),
	(5,33,57),
	(5,28,58),
	(5,31,58),
	(5,34,58),
	(5,29,59),
	(5,30,59),
	(5,32,59),
	(5,29,60),
	(5,30,60),
	(5,33,60),
	(5,29,61),
	(5,30,61),
	(5,34,61),
	(5,29,62),
	(5,31,62),
	(5,32,62),
	(5,29,63),
	(5,31,63),
	(5,33,63),
	(5,29,64),
	(5,31,64),
	(5,34,64),
	(6,35,65),
	(6,37,65),
	(6,35,66),
	(6,38,66),
	(6,36,67),
	(6,37,67),
	(6,36,68),
	(6,38,68),
	(7,39,69),
	(7,40,70),
	(7,41,71),
	(8,42,72),
	(8,44,72),
	(8,42,73),
	(8,45,73),
	(8,42,74),
	(8,46,74),
	(8,43,75),
	(8,44,75),
	(8,43,76),
	(8,45,76),
	(8,43,77),
	(8,46,77),
	(9,47,78),
	(9,48,79),
	(9,49,80),
	(9,50,81),
	(9,51,82),
	(10,52,83),
	(10,53,84),
	(10,54,85),
	(11,55,86),
	(11,56,87),
	(12,57,88),
	(12,58,89),
	(12,59,90),
	(13,60,91),
	(13,61,92),
	(13,62,93),
	(14,63,94),
	(14,65,94),
	(14,63,95),
	(14,66,95),
	(14,64,96),
	(14,65,96),
	(14,64,97),
	(14,66,97),
	(15,67,98),
	(15,68,99),
	(16,69,100),
	(16,70,101),
	(16,71,102),
	(17,72,103),
	(17,73,104),
	(17,74,105),
	(18,75,106),
	(18,76,107),
	(19,77,108),
	(19,79,108),
	(19,77,109),
	(19,80,109),
	(19,78,110),
	(19,79,110),
	(19,78,111),
	(19,80,111),
	(20,81,112),
	(20,82,113),
	(21,83,114),
	(21,84,115),
	(21,85,116),
	(22,86,117),
	(22,89,117),
	(22,86,118),
	(22,90,118),
	(22,87,119),
	(22,90,119),
	(22,87,120),
	(22,91,120),
	(22,88,121),
	(22,89,121),
	(22,88,122),
	(22,90,122),
	(22,88,123),
	(22,91,123),
	(23,92,124),
	(23,94,124),
	(23,92,125),
	(23,95,125),
	(23,93,126),
	(23,94,126),
	(23,93,127),
	(23,95,127),
	(24,96,128),
	(24,98,128),
	(24,97,129),
	(24,98,129),
	(24,96,130),
	(24,99,130),
	(24,97,131),
	(24,99,131),
	(25,100,132),
	(25,101,133),
	(25,100,134),
	(25,102,134),
	(25,101,135),
	(25,102,135),
	(26,103,136),
	(26,104,137),
	(26,105,138),
	(27,106,139),
	(28,107,140),
	(28,108,141),
	(28,109,142),
	(29,111,143),
	(30,112,144),
	(30,113,145),
	(30,114,146),
	(31,115,147),
	(32,116,148),
	(32,119,148),
	(32,121,148),
	(32,116,149),
	(32,119,149),
	(32,122,149),
	(32,116,150),
	(32,120,150),
	(32,121,150),
	(32,116,151),
	(32,120,151),
	(32,122,151),
	(32,117,152),
	(32,119,152),
	(32,121,152),
	(32,117,153),
	(32,119,153),
	(32,122,153),
	(32,117,154),
	(32,120,154),
	(32,121,154),
	(32,117,155),
	(32,120,155),
	(32,122,155),
	(32,118,156),
	(32,119,156),
	(32,121,156),
	(32,118,157),
	(32,119,157),
	(32,122,157),
	(32,118,158),
	(32,120,158),
	(32,121,158),
	(32,118,159),
	(32,120,159),
	(32,122,159),
	(33,123,160),
	(33,124,161),
	(33,125,162),
	(33,126,163),
	(33,127,164),
	(34,128,165),
	(34,129,166),
	(35,130,167),
	(35,131,168),
	(36,132,169),
	(36,133,170),
	(37,134,171),
	(37,135,172),
	(38,136,173),
	(38,137,174),
	(38,138,175),
	(39,139,176),
	(39,140,177),
	(39,141,178),
	(40,142,179),
	(40,143,180),
	(40,144,181);

-- SELECT *
-- FROM "varies_on"
-- ORDER BY "varies_on_id";

-- ---------------------------------------------------------------------------------------------------------------------
--  "warehouse" table

INSERT INTO "warehouse" ("street_number", "street_name", "city", "zipcode") VALUES
    ('123', 'Galle road', 'Katubedda', 12345);

-- SELECT *
-- FROM "warehouse";

-- ---------------------------------------------------------------------------------------------------------------------
--  "warehouse_contact"

INSERT INTO "warehouse_contact" ("telephone_number", "warehouse_id") VALUES
    ('0776969481', 1);

-- SELECT *
-- FROM "warehouse_contact";

-- ---------------------------------------------------------------------------------------------------------------------
-- "inventory"

INSERT INTO "inventory" ("warehouse_id", "variant_id", "sku", "count") VALUES
	(1, 1, 'SKU123', 50),
	(1, 2, 'SKU456', 30);

-- SELECT *
-- FROM "inventory";

-- ---------------------------------------------------------------------------------------------------------------------
--  "user"

INSERT INTO "user" ("role") VALUES
	('REG_CUST'),
	('REG_CUST'),
	('GUEST_CUST');

-- SELECT *
-- FROM "user";

-- ---------------------------------------------------------------------------------------------------------------------
-- "registered_customer"

INSERT INTO "registered_user" ("user_id", "email", "password", "first_name", "last_name", "locked", "enabled") VALUES
	(1, 'dulinaperera@gmail.com', 'password123', 'Dulina', 'Perera', FALSE, TRUE),
	(2, 'nethumrathnayake@gmail.com', 'IloveMethmini', 'Nethum', 'Rathnayake', FALSE, TRUE);

-- SELECT *
-- FROM "registered_user";

-- ---------------------------------------------------------------------------------------------------------------------
-- "customer_contact"

INSERT INTO "user_contact" ("user_id", "telephone_number") VALUES
	(1, '0702632639'),
	(2, '0714283876');

-- SELECT *
-- FROM "user_contact";

-- ---------------------------------------------------------------------------------------------------------------------
-- "customer_address"

INSERT INTO "user_address" ("user_id", "street_number", "street_name", "city", "zipcode") VALUES
	(1, '5/16', 'Sri Dhammaruchi Mawatha', 'Wadduwa', 12560),
	(2, '123', 'Kesbewa Road', 'Kesbewaa', 12345);

-- SELECT *
-- FROM "user_address";

-- ---------------------------------------------------------------------------------------------------------------------
--  "cart"

INSERT INTO "cart" ("user_id", "total_price") VALUES
	(1, 0),
	(2, 0),
	(3, 0);

-- SELECT *
-- FROM "cart";

-- ---------------------------------------------------------------------------------------------------------------------
-- "cart_item" table

INSERT INTO "cart_item" ("user_id", "variant_id", "count") VALUES
	(1, 1, 2),
	(2, 3, 1),
	(3, 5, 3);

SELECT *
FROM "cart_item";

-- ---------------------------------------------------------------------------------------------------------------------
-- "order" table

INSERT INTO "order" ("date", "total_payment", "payment_method", "delivery_method", "email", "street_number", "street_name", "city", "zipcode", "telephone_number") VALUES
	('2023-01-15 08:30:00', 149.97, 'Credit Card', 'Express Shipping', 'dulinaperera@gmail.com', '456', 'Godagama', 'Kaluthara', 98765, '0702632639'),
	('2023-03-20 10:00:00', 239.97, 'Credit Card', 'Express Shipping', 'nethumrathnayake@gmail.com', '456', 'Kawdana', 'Dehiwala', 98765, '0705654028');

-- SELECT *
-- FROM "order";

-- ---------------------------------------------------------------------------------------------------------------------
-- "order_item"

INSERT INTO "order_item" ("order_id", "variant_id", "warehouse_id", "count") VALUES
	(2, 3, 1, 1),
	(2, 5, 1, 2);

-- SELECT *
-- FROM "order_item";
