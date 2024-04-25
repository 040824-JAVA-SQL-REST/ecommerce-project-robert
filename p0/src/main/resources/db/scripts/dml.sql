do $$
begin
    if exists (select from pg_catalog.pgtables where tablename = 'roles') then
      insert into roles (id, name) values ('7c2eb0d7-7902-4839-a39a-52f16d17768e', 'USER');
        insert into roles (id, name) values ('f69ba9a6-843f-4231-a05d-48fdc09e336b', 'ADMIN');
        end if;
end;
$$
