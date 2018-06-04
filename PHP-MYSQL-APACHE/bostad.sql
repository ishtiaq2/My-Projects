//set names utf8; //at databases level;
use search_bostader; 

drop table bostader;

create table bostader (
lan varchar(64),
objekttyp varchar(64),
adress varchar(64),
area float,
rum int,
pris float,
avgift float
)  character set utf8 collate utf8_swedish_ci;

insert into bostader values('Stockholm','Bostadsrätt','Polhemsgatan 1',30,1,1000,1234);
insert into bostader values('Stockholm','Bostadsrätt','Polhemsgatan 2',60,2,2000,2345);
insert into bostader values('Stockholm','Villa','Storgatan 1',130,5,3000,3456);
insert into bostader values('Stockholm','Villa','Storgatan 2',160,6,4000,3456);
insert into bostader values('Stockholm','Bostadsrätt','Gamlastan 3',30,4,4000,4456);	 
insert into bostader values('Stockholm','Villa','Storgatan 3',160,6,4000,3456);
insert into bostader values('Stockholm','Bostadsrätt','Gamlastan 3',30,1,4000,4456);	 
insert into bostader values('Stockholm','Bostadsrätt','Gamlastan 3',30,1,4000,4456);	 
insert into bostader values('Uppsala','Bostadsrätt','Gröna gatan 1',30,1,500,1234);
insert into bostader values('Uppsala','Bostadsrätt','Gröna gatan 2',60,2,600,2345);
insert into bostader values('Uppsala','Villa','Kungsängsvägen 1',130,5,700,3456);
insert into bostader values('Uppsala','Villa','Kungsängsvägen 2',160,6,800,3456);
insert into bostader values('Uppsala','Villa','Kungsängsvägen 3',160,6,800,3456);
insert into bostader values('Göteborg','Bostadsrätt','Polhemsgatan 1',30,1,1000,1234);
insert into bostader values('Göteborg','Bostadsrätt','Polhemsgatan 2',60,2,2000,2345);
insert into bostader values('Göteborg','Villa','Storgatan 1',130,5,3000,3456);
insert into bostader values('Göteborg','Villa','Storgatan 2',160,6,4000,3456);
insert into bostader values('Göteborg','Villa','Storgatan 3',160,6,4000,3456);
insert into bostader values('Malmo','Bostadsrätt','Malmo 3',30,1,4000,3456);
insert into bostader values('Goteborg','Bostadsrätt','Malmo 3',30,1,4000,3456);

SELECT * FROM bostader