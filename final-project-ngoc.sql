CREATE TABLE `Role`
(
  `ID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(80),
  `CreatedAt` datetime,
  `UpdatedAt` datetime,
  PRIMARY KEY (`ID`)
);

CREATE TABLE `Account`
(
  `ID` int NOT NULL AUTO_INCREMENT,
  `Email` varchar(80) UNIQUE,
  `Password` varchar(2048),
  `Fullname` varchar(80),
  `RoleID` int,
  `CreatedAt` datetime,
  `UpdatedAt` datetime
);

CREATE TABLE `OAuthAccessToken`
(
  `ID` int PRIMARY KEY,
  `AccessToken` varchar(2048),
  `Expires` timestamp,
  `AccountID` int,
  `CreatedAt` datetime,
  `UpdatedAt` datetime
);

CREATE TABLE `Category`
(
  `ID` int PRIMARY KEY,
  `Name` varchar(200),
  `CreatedAt` datetime,
  `UpdatedAt` datetime
);

CREATE TABLE `Item`
(
  `ID` int PRIMARY KEY,
  `Name` varchar(200),
  `Price` float,
  `Amount` int,
  `ImageURLs` varchar(10240),
  `Description` varchar(5000),
  `CreatedAt` datetime,
  `UpdatedAt` datetime
);

CREATE TABLE `Item_Has_Category`
(
  `ID` int PRIMARY KEY,
  `CategoryID` int,
  `ItemID` int,
  `CreatedAt` datetime,
  `UpdatedAt` datetime
);

CREATE TABLE `Comment`
(
  `ID` int PRIMARY KEY,
  `ItemID` int,
  `AccountID` int,
  `Content` varchar(500),
  `CreatedAt` datetime,
  `UpdatedAt` datetime
);

CREATE TABLE `OrderAddress`
(
  `ID` int PRIMARY KEY,
  `Fullname` varchar(200),
  `Phone` varchar(15),
  `Address` varchar(200),
  `City` varchar(200),
  `Province` varchar(200),
  `District` varchar(200),
  `CreatedAt` datetime,
  `UpdatedAt` datetime
);

CREATE TABLE `Order`
(
  `ID` int PRIMARY KEY,
  `OrderAddressID` int,
  `AccountID` int,
  `Status` int,
  `CreatedAt` datetime,
  `UpdatedAt` datetime
);

CREATE TABLE `Order_Has_Item`
(
  `ID` int PRIMARY KEY,
  `OrderID` int,
  `ItemID` int,
  `DiscountPriceID` int,
  `CreatedAt` datetime,
  `UpdatedAt` datetime
);

CREATE TABLE `DiscountPrice`
(
  `ID` int PRIMARY KEY,
  `ItemID` int,
  `DiscountPrice` int,
  `Percent` int,
  `StartingDay` datetime,
  `EndingDay` datetime,
  `CreatedAt` datetime,
  `UpdatedAt` datetime
);

ALTER TABLE `Account` ADD FOREIGN KEY (`RoleID`) REFERENCES `Role` (`ID`);

ALTER TABLE `OAuthAccessToken` ADD FOREIGN KEY (`AccountID`) REFERENCES `Account` (`ID`);

ALTER TABLE `Item_Has_Category` ADD FOREIGN KEY (`ItemID`) REFERENCES `Item` (`ID`);

ALTER TABLE `Item_Has_Category` ADD FOREIGN KEY (`CategoryID`) REFERENCES `Category` (`ID`);

ALTER TABLE `Comment` ADD FOREIGN KEY (`ItemID`) REFERENCES `Item` (`ID`);

ALTER TABLE `Order` ADD FOREIGN KEY (`AccountID`) REFERENCES `Account` (`ID`);

ALTER TABLE `Order` ADD FOREIGN KEY (`OrderAddressID`) REFERENCES `OrderAddress` (`ID`);

ALTER TABLE `Order_Has_Item` ADD FOREIGN KEY (`OrderID`) REFERENCES `Order` (`ID`);

ALTER TABLE `Order_Has_Item` ADD FOREIGN KEY (`ItemID`) REFERENCES `Item` (`ID`);

ALTER TABLE `Order_Has_Item` ADD FOREIGN KEY (`DiscountPriceID`) REFERENCES `DiscountPrice` (`ID`);

ALTER TABLE `DiscountPrice` ADD FOREIGN KEY (`ItemID`) REFERENCES `Item` (`ID`);
