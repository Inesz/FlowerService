INSERT INTO flowers (id, name, type) values (1, 'fuchsia', 'fuksja');
INSERT INTO flowers (id, name, type) values (2, 'geranium', 'pelargonia');
INSERT INTO flowers (id, name, type) values (3, 'mallow', 'malwa');

INSERT INTO orders (id, consumer) values (1, 'some consumer 1');
INSERT INTO orders (id, consumer) values (2, 'some consumer 2');
INSERT INTO orders (id, consumer) values (3, 'some consumer 3');

INSERT INTO items (id, flower_id, quantity) values (1, 1, 10);
INSERT INTO items (id, flower_id, quantity) values (2, 1, 2);
INSERT INTO items (id, flower_id, quantity) values (3, 3, 5);

CREATE OR REPLACE function get_name(flower_id bigint)
    returns text
    language plpgsql
as
'
Declare
Flower_name text;
Begin
select name
into Flower_name
from flowers
where id = flower_id;
return Flower_name;
End;
'