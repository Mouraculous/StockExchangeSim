drop table AUDIT if exists;
create table AUDIT (RECORD_ID UUID NOT NULL, OPERATION varchar NOT NULL, AUDIT_TIMESTAMP BIGINT NOT NULL);

drop table MESSAGE if exists;
create table MESSAGE (MESSAGE_ID UUID NOT NULL, FROM_USER varchar, TO_USER varchar, TEXT varchar NOT NULL,
                      TIMESTAMP BIGINT NOT NULL);

drop table MARKET_ORDERS if exists;
create table MARKET_ORDERS(ID UUID NOT NULL, ORDER_STATE varchar NOT NULL, TICKER_SYMBOL varchar NOT NULL, SIDE varchar NOT NULL,
ORDER_TYPE varchar NOT NULL, PRICE double, AMOUNT double, TIME_OF_ORDER timestamp, TRADER_ID bigint(20) not null);

drop table STOCK_QUOTES if exists;
create table STOCK_QUOTES(
    ID UUID not null,
    ASK_PRICE double not null,
    BID_PRICE double not null,
    LAST_PRICE double not null,
    AMOUNT_TRADED integer not null,
    TICKER varchar(255) not null,
    TIME_OF_ORDER timestamp not null
);

drop table TRADERS if exists;

create table TRADERS
(
   ID bigint(20) NOT NULL AUTO_INCREMENT,
   NAME varchar(255) not null,
  EMAIL varchar(255) not null,
  PHONE varchar(255) not null,
  ADDRESS varchar(255) not null,
   primary key(id)
);

insert into TRADERS
values(1, 'Ranga', 'ranga@email.com','123456', 'E1234567');
insert into TRADERS
values(2,'Ravi', 'ravi@email.com','7890123', 'E1234567');



drop table TICKERS if exists;
create table TICKERS(TICKER_SYMBOL varchar NOT NULL, COMPANY_NAME varchar NOT NULL, MARKET_SECTOR varchar NOT NULL);

insert into TICKERS values('USD','kukle inc','bilety za granice');
insert into TICKERS values('EUR','gugle inc','bilety za granice');
insert into TICKERS values('C2137','gugle inc','bilety krajowe');
insert into TICKERS values('GMDHOLDING','kukle inc','bilety krajowe');
insert into STOCK_QUOTES values('4e968c06-bd44-47b3-8eae-199f1b23403e',40.7, 40.6, 12.25, 100, 'USD', '2018-08-02T07:32:15+00:00');
insert into STOCK_QUOTES values('4e968c06-bd44-47b3-8eae-199f1b23403e',40.7, 40.6, 12.25, 100, 'GMDHOLDING', '2018-08-02T07:32:15+00:00');
insert into STOCK_QUOTES values('4e968c06-bd44-47b3-8eae-199f1b23403e',40.7, 40.6, 12.25, 100, 'C2137', '2018-08-02T07:32:15+00:00');
insert into STOCK_QUOTES values('4e968c06-bd44-47b3-8eae-199f1b23403e',40.7, 40.6, 12.25, 100, 'EUR', '2018-08-02T07:32:15+00:00');