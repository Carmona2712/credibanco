INSERT INTO public.cards (id, balance, card_number, create_at, expiration_date, first_lastname, first_name, status) VALUES (1, 2000, '1234569513038053', '2024-05-20 18:51:55.415000', '05/2027', null, null, 'Active');
INSERT INTO public.cards (id, balance, card_number, create_at, expiration_date, first_lastname, first_name, status) VALUES (3, 0, '1234568832460449', '2024-05-20 21:23:44.658000', '05/2027', null, null, 'Inactive');


INSERT INTO public.transactions (id, amount, create_at, date_transaction, type, card_id) VALUES (152, 500, '2024-05-20 20:38:16.707000', '2024-05-20 20:38:16.700256', 'Approved', 1);
INSERT INTO public.transactions (id, amount, create_at, date_transaction, type, card_id) VALUES (153, 100, '2024-05-20 20:39:04.059000', '2024-05-20 20:39:04.058521', 'Annulled', 1);