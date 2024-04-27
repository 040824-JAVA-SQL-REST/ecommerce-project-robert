drop table if exists order_product cascade;
drop table if exists users;
drop table if exists orders;
drop table if exists products;
drop view if exists view_order_history;
drop view if exists users_with_role;
drop view if exists view_cart_items;


drop table if exists roles;
--DDL SCRIPT --

create table roles (
	id varchar,
	name varchar not null unique,
	constraint pk_role_id primary key (id)
);

create table users (
	id varchar , 
	email varchar not null unique,
	password varchar not null,
	firstname varchar,
	lastname varchar,
	role_id varchar,
	constraint pk_user_id primary key (id),
	constraint fk_role_id foreign key (role_id) references roles (id)
);

create table orders (
	id varchar,
	total_price varchar,
	order_status varchar,
	order_date timestamp without time zone default current_timestamp,
	user_id varchar,
	constraint pk_order_id primary key (id),
	constraint fk_user_id foreign key (user_id) references users (id)
);

create table products (
	id varchar,
	name varchar,
	description varchar,
	price varchar,
	category varchar,
	is_available boolean,
	constraint pk_product_id primary key (id)
);

create table cart (
	id varchar,
	user_id varchar,
	total_cost varchar,
	is_CheckedOut boolean,
	constraint pk_cart_id primary key (id),
	constraint fk_user_id foreign key (user_id) references users (id)
);

create table cart_product(
	cart_id varchar,
	product_id varchar,
	quantity varchar,
	constraint pk_composite_key_cart_id_product_id primary key(cart_id, product_id),
	constraint fk_cart_id foreign key (cart_id) references cart(id),
	constraint fk_product_id foreign key (product_id) references products (id)
)

create view view_cart_items as 
select c.id as cart_id, p.name as product_name, p.description as product_description, p.price as product_price, p.category as product_category, f.quantity as product_quantity, c.total_cost as total_price
from cart_product f 
join cart c on f.cart_id = c.id 
join users u on c.user_id = u.id
join products p on f.product_id = p.id;

create view view_cart_items_for_debugging as 
select c.id as cart_id, c.user_id as user_id, u.email as user_email, p.id as product_id, p.name as product_name, p.price as product_price, f.quantity as product_quantity
from cart_product f 
join cart c on f.cart_id = c.id 
join users u on c.user_id = u.id
join products p on f.product_id = p.id;

create table order_product(
	order_id varchar ,
	product_id varchar,
	quantity varchar,
	constraint pk_composite_key_order_id_product_id primary key(order_id, product_id),
	constraint fk_order_id foreign key (order_id) references orders (id),
	constraint fk_product_id foreign key (product_id) references products (id)
);

create view view_order_history as 
select o.id as order_id, o.user_id as user_id, p.id as product_id, p.name as product_name, p.price as product_price, f.quantity as product_quantity
from order_product f join orders o on f.order_id = o.id 
join products p on f.product_id = p.id;

create view users_with_role as
select 
	u.id,
	u.email,
	u.password,
	u.firstname,
	u.lastname,
	r.id as role_id,
	r.name as role_name
from users u
join roles r on r.id = u.role_id;
