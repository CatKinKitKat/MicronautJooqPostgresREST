insert into webshop (handle, url, a, b, c)
values ('test', 'http://www.test.com', '33', '33', '34');

insert into webshop (handle, url, a, b, c, interest_rate, currency, run_jobs,
                     multi_supply)
values ('optiply', 'http://www.optiply.nl', '50', '25', '25', '25', 'USD',
        'false', 'true');

insert into webshopemails (webshop_id, address)
values (1, 'test@test.com');

insert into webshopemails (webshop_id, address)
values (1, 'tester@test.com');

insert into webshopemails (webshop_id, address)
values (1, 'tested@test.com');

insert into webshopemails (webshop_id, address)
values (2, 'test@optiply.nl');

insert into webshopemails (webshop_id, address)
values (2, 'optiply@optiply.nl');

select *
from webshop;

select *
from webshopemails;