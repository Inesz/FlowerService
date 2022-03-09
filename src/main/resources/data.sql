CREATE OR REPLACE FUNCTION update_data()
    RETURNS TRIGGER
    language plpgsql
AS
'
BEGIN
IF (TG_OP = ''INSERT'') THEN
INSERT INTO flowers_history(date, new_name, old_name) VALUES(now(), NEW.name, null);
ELSIF (TG_OP = ''UPDATE'') THEN
INSERT INTO flowers_history(date, new_name, old_name) VALUES(now(), NEW.name, OLD.name);
ELSIF (TG_OP = ''DELETE'') THEN
    INSERT INTO flowers_history(date, new_name, old_name)
    VALUES (now(), null, OLD.name);
END IF;
return null;
END;
';

CREATE TRIGGER update_flower_history AFTER INSERT OR UPDATE OR DELETE ON flowers FOR EACH ROW EXECUTE PROCEDURE update_data();
INSERT INTO flowers (id, name, type) values (4, 'tmp1', 'tmp1');
UPDATE flowers SET name = 'tmp2' where id=4;
DELETE FROM flowers WHERE id = 4;

INSERT INTO flowers (id, name, type) values (1, 'fuchsia', 'fuksja');
INSERT INTO flowers (id, name, type) values (2, 'geranium', 'pelargonia');
INSERT INTO flowers (id, name, type) values (3, 'mallow', 'malwa');
INSERT INTO flowers (id, name, type) values (4, 'Centaurea cyanus', 'Chaber bławatek');
INSERT INTO flowers (id, name, type) values (5, 'Papaver rhoeas', 'Mak polny');
INSERT INTO flowers (id, name, type) values (6, 'Calendula officinalis', 'Nagietek lekarski');

INSERT INTO orders (id, consumer) values (1, 'some customer 1');
INSERT INTO orders (id, consumer) values (2, 'some customer 2');
INSERT INTO orders (id, consumer) values (3, 'some customer 3');
INSERT INTO orders (id, consumer) values (4, 'some customer 4');
INSERT INTO orders (id, consumer) values (5, 'some customer 5');

INSERT INTO items (id, order_id, flower_id, quantity) values (1, 1, 1, 10);
INSERT INTO items (id, order_id, flower_id, quantity) values (2, 1, 1, 2);
INSERT INTO items (id, order_id, flower_id, quantity) values (3, 2, 3, 5);
INSERT INTO items (id, order_id, flower_id, quantity) values (4, 2, 1, 2);
INSERT INTO items (id, order_id, flower_id, quantity) values (5, 2, 1, 2);

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
';