use zenon_frauds;

create table if not exists transactions
(
    id                    bigint auto_increment primary key,
    step                  integer        not null,
    type                  varchar(20)    not null,
    amount                decimal(20, 2) not null,
    name_origin           varchar(50)    not null,
    old_balance_origin    decimal(10, 2) not null,
    new_balance_origin    decimal(10, 2) not null,
    name_recipient        varchar(20)    not null,
    old_balance_recipient decimal(10, 2) not null,
    new_balance_recipient decimal(10, 2) not null,
    is_fraud              tinyint(1) default 0,
    is_flagged_fraud      tinyint(1) default 0
);

insert into transactions (id, step, type, amount, name_origin, old_balance_origin, new_balance_origin, name_recipient,
                          old_balance_recipient, new_balance_recipient, is_fraud, is_flagged_fraud)
values (null, 1, 'PAYMENT', 9839.64, 'C1231006815', 170136.0, 160296.36, 'M1979787155', 0.0, 0.0, 0, 0);

select t.step, t.step, t.type, t.amount, t.name_origin, t.old_balance_origin, t.new_balance_origin,
       t.name_recipient, t.old_balance_recipient, t.new_balance_recipient, t.is_fraud, t.is_flagged_fraud
from transactions t where name_origin = 'C1231006815';

select * from transactions;
select count(*) from transactions;
select * from transactions where name_origin = 'C1231006815';
select * from transactions where name_origin = 'x';