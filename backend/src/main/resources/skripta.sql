-- Delete all rows from the tables
DELETE FROM izvodac_ulaznica;
DELETE FROM korisnik_zanr;
DELETE FROM oglas;
DELETE FROM role;
DELETE FROM transakcija;
DELETE FROM ulaznica;
DELETE FROM izvodac;
DELETE FROM zanr;

INSERT INTO role (id_role, role)
VALUES
    (1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN');

INSERT INTO public.administrator(id_admin, email_admin)
VALUES (1, 'ticket4ticket4@gmail.com');

INSERT INTO zanr (id_zanra, ime_zanra, slika_zanra) VALUES
                                                        (1, 'POP', 'https://example.com/pop_image.jpg'),
                                                        (2, 'ROCK', 'https://example.com/rock_image.jpg'),
                                                        (3, 'HIP_HOP_RAP', 'https://example.com/hip_hop_rap_image.jpg'),
                                                        (4, 'RNB', 'https://example.com/rnb_image.jpg'),
                                                        (5, 'COUNTRY', 'https://example.com/country_image.jpg'),
                                                        (6, 'ELECTRONIC_DANCE', 'https://example.com/electronic_dance_image.jpg'),
                                                        (7, 'JAZZ', 'https://example.com/jazz_image.jpg'),
                                                        (8, 'BLUES', 'https://example.com/blues_image.jpg'),
                                                        (9, 'CLASSICAL', 'https://example.com/classical_image.jpg'),
                                                        (10, 'REGGAE', 'https://example.com/reggae_image.jpg'),
                                                        (11, 'METAL', 'https://example.com/metal_image.jpg'),
                                                        (12, 'FOLK', 'https://example.com/folk_image.jpg'),
                                                        (13, 'GOSPEL', 'https://example.com/gospel_image.jpg'),
                                                        (14, 'LATIN', 'https://example.com/latin_image.jpg'),
                                                        (15, 'ALTERNATIVE_INDIE', 'https://example.com/alternative_indie_image.jpg');


INSERT INTO izvodac (id_izvodaca, foto_izvodaca, ime_izvodaca, prezime_izvodaca, starost_izvodaca, zanr_id)
VALUES
    (1, 'https://example.com/pop_image.jpg', 'Svetlana', 'Ražnatović', 50, 12),
    (2, 'https://example.com/rock_image.jpg', 'Bijelo', 'Dugme', 60, 2),
    (3, 'https://example.com/hip_hop_rap_image.jpg', 'Podočnjaci', '', 35, 6),
    (4, 'https://example.com/rnb_image.jpg', 'Severina', 'Vučković', 51, 1),
    (5, 'https://example.com/country_image.jpg', 'Damir', 'Urban', 50, 2);
-- Insert tickets for TIP ULAZNICA 1 (22.03.2025, Nova Gorica)
INSERT INTO ulaznica (id_ulaznice, datum_koncerta, lokacija_koncerta, id_korisnika, odabrana_zona, vrsta_ulaznice, url_info, url_slika, sifra_ulaznice, status)
VALUES
    (1, '2025-03-22', 'Nova Gorica', NULL, 'VIP_LOZA', 'VIP', 'https://www.entrio.hr/event/ceca-u-novoj-gorici-22266', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/222/22266_marketing_banner_1522x682_1734094814.png?v=1734094814', 'VIP_NG_001', 'NEPREUZETA'),
    (2, '2025-03-22', 'Nova Gorica', NULL, 'TRIBINA_A', 'PREMIUM', 'https://www.entrio.hr/event/ceca-u-novoj-gorici-22266', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/222/22266_marketing_banner_1522x682_1734094814.png?v=1734094814', 'PREM_NG_002', 'NEPREUZETA'),
    (3, '2025-03-22', 'Nova Gorica', NULL, 'TRIBINA_B', 'STANDARD', 'https://www.entrio.hr/event/ceca-u-novoj-gorici-22266', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/222/22266_marketing_banner_1522x682_1734094814.png?v=1734094814', 'STAN_NG_003', 'NEPREUZETA'),
    (4, '2025-03-22', 'Nova Gorica', NULL, 'PARTER', 'FAMILY', 'https://www.entrio.hr/event/ceca-u-novoj-gorici-22266', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/222/22266_marketing_banner_1522x682_1734094814.png?v=1734094814', 'FAM_NG_004', 'NEPREUZETA'),
    (5, '2025-03-22', 'Nova Gorica', NULL, 'GALERIJA', 'STUDENT', 'https://www.entrio.hr/event/ceca-u-novoj-gorici-22266', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/222/22266_marketing_banner_1522x682_1734094814.png?v=1734094814', 'STU_NG_005', 'NEPREUZETA'),
    (6, '2025-03-22', 'Nova Gorica', NULL, 'VIP_LOZA', 'VIP', 'https://www.entrio.hr/event/ceca-u-novoj-gorici-22266', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/222/22266_marketing_banner_1522x682_1734094814.png?v=1734094814', 'VIP_NG_006', 'NEPREUZETA'),
    (7, '2025-03-22', 'Nova Gorica', NULL, 'TRIBINA_A', 'PREMIUM', 'https://www.entrio.hr/event/ceca-u-novoj-gorici-22266', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/222/22266_marketing_banner_1522x682_1734094814.png?v=1734094814', 'PREM_NG_007', 'NEPREUZETA'),
    (8, '2025-03-22', 'Nova Gorica', NULL, 'TRIBINA_B', 'STANDARD', 'https://www.entrio.hr/event/ceca-u-novoj-gorici-22266', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/222/22266_marketing_banner_1522x682_1734094814.png?v=1734094814', 'STAN_NG_008', 'NEPREUZETA'),
    (9, '2025-03-22', 'Nova Gorica', NULL, 'PARTER', 'FAMILY', 'https://www.entrio.hr/event/ceca-u-novoj-gorici-22266', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/222/22266_marketing_banner_1522x682_1734094814.png?v=1734094814', 'FAM_NG_009', 'NEPREUZETA'),
    (10, '2025-03-22', 'Nova Gorica', NULL, 'GALERIJA', 'STUDENT', 'https://www.entrio.hr/event/ceca-u-novoj-gorici-22266', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/222/22266_marketing_banner_1522x682_1734094814.png?v=1734094814', 'STU_NG_010', 'NEPREUZETA');

-- Insert tickets for TIP ULAZNICA 2 (29.03.2025, Zagreb, Arena Zagreb)
INSERT INTO ulaznica (id_ulaznice, datum_koncerta, lokacija_koncerta, id_korisnika, odabrana_zona, vrsta_ulaznice, url_info, url_slika, sifra_ulaznice, status)
VALUES
    (11, '2025-03-29', 'Zagreb, Arena Zagreb', NULL, 'VIP_LOZA', 'VIP', 'https://www.entrio.hr/event/bijelo-dugme-turneja-dozivjeti-stotu-arena-zagreb-19299', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/192/19299_marketing_banner_1522x682_1729596298.png?v=1729596299', 'VIP_ZA_001', 'NEPREUZETA'),
    (12, '2025-03-29', 'Zagreb, Arena Zagreb', NULL, 'TRIBINA_A', 'PREMIUM', 'https://www.entrio.hr/event/bijelo-dugme-turneja-dozivjeti-stotu-arena-zagreb-19299', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/192/19299_marketing_banner_1522x682_1729596298.png?v=1729596299', 'PREM_ZA_002', 'NEPREUZETA'),
    (13, '2025-03-29', 'Zagreb, Arena Zagreb', NULL, 'TRIBINA_B', 'STANDARD', 'https://www.entrio.hr/event/bijelo-dugme-turneja-dozivjeti-stotu-arena-zagreb-19299', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/192/19299_marketing_banner_1522x682_1729596298.png?v=1729596299', 'STAN_ZA_003', 'NEPREUZETA'),
    (14, '2025-03-29', 'Zagreb, Arena Zagreb', NULL, 'PARTER', 'FAMILY', 'https://www.entrio.hr/event/bijelo-dugme-turneja-dozivjeti-stotu-arena-zagreb-19299', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/192/19299_marketing_banner_1522x682_1729596298.png?v=1729596299', 'FAM_ZA_004', 'NEPREUZETA'),
    (15, '2025-03-29', 'Zagreb, Arena Zagreb', NULL, 'GALERIJA', 'STUDENT', 'https://www.entrio.hr/event/bijelo-dugme-turneja-dozivjeti-stotu-arena-zagreb-19299', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/192/19299_marketing_banner_1522x682_1729596298.png?v=1729596299', 'STU_ZA_005', 'NEPREUZETA'),
    (16, '2025-03-29', 'Zagreb, Arena Zagreb', NULL, 'VIP_LOZA', 'VIP', 'https://www.entrio.hr/event/bijelo-dugme-turneja-dozivjeti-stotu-arena-zagreb-19299', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/192/19299_marketing_banner_1522x682_1729596298.png?v=1729596299', 'VIP_ZA_006', 'NEPREUZETA'),
    (17, '2025-03-29', 'Zagreb, Arena Zagreb', NULL, 'TRIBINA_A', 'PREMIUM', 'https://www.entrio.hr/event/bijelo-dugme-turneja-dozivjeti-stotu-arena-zagreb-19299', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/192/19299_marketing_banner_1522x682_1729596298.png?v=1729596299', 'PREM_ZA_007', 'NEPREUZETA'),
    (18, '2025-03-29', 'Zagreb, Arena Zagreb', NULL, 'TRIBINA_B', 'STANDARD', 'https://www.entrio.hr/event/bijelo-dugme-turneja-dozivjeti-stotu-arena-zagreb-19299', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/192/19299_marketing_banner_1522x682_1729596298.png?v=1729596299', 'STAN_ZA_008', 'NEPREUZETA'),
    (19, '2025-03-29', 'Zagreb, Arena Zagreb', NULL, 'PARTER', 'FAMILY', 'https://www.entrio.hr/event/bijelo-dugme-turneja-dozivjeti-stotu-arena-zagreb-19299', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/192/19299_marketing_banner_1522x682_1729596298.png?v=1729596299', 'FAM_ZA_009', 'NEPREUZETA'),
    (20, '2025-03-29', 'Zagreb, Arena Zagreb', NULL, 'GALERIJA', 'STUDENT', 'https://www.entrio.hr/event/bijelo-dugme-turneja-dozivjeti-stotu-arena-zagreb-19299', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/192/19299_marketing_banner_1522x682_1729596298.png?v=1729596299', 'STU_ZA_010', 'NEPREUZETA');

-- Insert tickets for TIP ULAZNICA 3 (07.02.2025, Rijeka, Klub Crkva)
INSERT INTO ulaznica (id_ulaznice, datum_koncerta, lokacija_koncerta, id_korisnika, odabrana_zona, vrsta_ulaznice, url_info, url_slika, sifra_ulaznice, status)
VALUES
    (21, '2025-02-07', 'Rijeka, Klub Crkva', NULL, 'VIP_LOZA', 'VIP', 'https://www.entrio.hr/event/urban-beats-podocnjaci-u-rijeci-22315', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/223/22315_marketing_banner_1522x682_1734527063.png?v=1734527063', 'VIP_RI_001', 'NEPREUZETA'),
    (22, '2025-02-07', 'Rijeka, Klub Crkva', NULL, 'TRIBINA_A', 'PREMIUM', 'https://www.entrio.hr/event/urban-beats-podocnjaci-u-rijeci-22315', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/223/22315_marketing_banner_1522x682_1734527063.png?v=1734527063', 'PREM_RI_002', 'NEPREUZETA'),
    (23, '2025-02-07', 'Rijeka, Klub Crkva', NULL, 'TRIBINA_B', 'STANDARD', 'https://www.entrio.hr/event/urban-beats-podocnjaci-u-rijeci-22315', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/223/22315_marketing_banner_1522x682_1734527063.png?v=1734527063', 'STAN_RI_003', 'NEPREUZETA'),
    (24, '2025-02-07', 'Rijeka, Klub Crkva', NULL, 'PARTER', 'FAMILY', 'https://www.entrio.hr/event/urban-beats-podocnjaci-u-rijeci-22315', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/223/22315_marketing_banner_1522x682_1734527063.png?v=1734527063', 'FAM_RI_004', 'NEPREUZETA'),
    (25, '2025-02-07', 'Rijeka, Klub Crkva', NULL, 'GALERIJA', 'STUDENT', 'https://www.entrio.hr/event/urban-beats-podocnjaci-u-rijeci-22315', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/223/22315_marketing_banner_1522x682_1734527063.png?v=1734527063', 'STU_RI_005', 'NEPREUZETA'),
    (26, '2025-02-07', 'Rijeka, Klub Crkva', NULL, 'VIP_LOZA', 'VIP', 'https://www.entrio.hr/event/urban-beats-podocnjaci-u-rijeci-22315', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/223/22315_marketing_banner_1522x682_1734527063.png?v=1734527063', 'VIP_RI_006', 'NEPREUZETA'),
    (27, '2025-02-07', 'Rijeka, Klub Crkva', NULL, 'TRIBINA_A', 'PREMIUM', 'https://www.entrio.hr/event/urban-beats-podocnjaci-u-rijeci-22315', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/223/22315_marketing_banner_1522x682_1734527063.png?v=1734527063', 'PREM_RI_007', 'NEPREUZETA'),
    (28, '2025-02-07', 'Rijeka, Klub Crkva', NULL, 'TRIBINA_B', 'STANDARD', 'https://www.entrio.hr/event/urban-beats-podocnjaci-u-rijeci-22315', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/223/22315_marketing_banner_1522x682_1734527063.png?v=1734527063', 'STAN_RI_008', 'NEPREUZETA'),
    (29, '2025-02-07', 'Rijeka, Klub Crkva', NULL, 'PARTER', 'FAMILY', 'https://www.entrio.hr/event/urban-beats-podocnjaci-u-rijeci-22315', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/223/22315_marketing_banner_1522x682_1734527063.png?v=1734527063', 'FAM_RI_009', 'NEPREUZETA'),
    (30, '2025-02-07', 'Rijeka, Klub Crkva', NULL, 'GALERIJA', 'STUDENT', 'https://www.entrio.hr/event/urban-beats-podocnjaci-u-rijeci-22315', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/223/22315_marketing_banner_1522x682_1734527063.png?v=1734527063', 'STU_RI_010', 'NEPREUZETA');

-- Insert tickets for TIP ULAZNICA 4 (14.02.2025, Kaštelir, Sportska dvorana Kaštelir Labinci)
INSERT INTO ulaznica (id_ulaznice, datum_koncerta, lokacija_koncerta, id_korisnika, odabrana_zona, vrsta_ulaznice, url_info, url_slika, sifra_ulaznice, status)
VALUES
    (31, '2025-02-14', 'Kaštelir, Sportska dvorana Kaštelir Labinci', NULL, 'VIP_LOZA', 'VIP', 'https://www.entrio.hr/event/valentinovo-u-kasteliru-severina-nex-22193', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/221/22193_marketing_banner_1522x682_1733836779.png?v=1733836780', 'VIP_KA_001', 'NEPREUZETA'),
    (32, '2025-02-14', 'Kaštelir, Sportska dvorana Kaštelir Labinci', NULL, 'TRIBINA_A', 'PREMIUM', 'https://www.entrio.hr/event/valentinovo-u-kasteliru-severina-nex-22193', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/221/22193_marketing_banner_1522x682_1733836779.png?v=1733836780', 'PREM_KA_002', 'NEPREUZETA'),
    (33, '2025-02-14', 'Kaštelir, Sportska dvorana Kaštelir Labinci', NULL, 'TRIBINA_B', 'STANDARD', 'https://www.entrio.hr/event/valentinovo-u-kasteliru-severina-nex-22193', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/221/22193_marketing_banner_1522x682_1733836779.png?v=1733836780', 'STAN_KA_003', 'NEPREUZETA'),
    (34, '2025-02-14', 'Kaštelir, Sportska dvorana Kaštelir Labinci', NULL, 'PARTER', 'FAMILY', 'https://www.entrio.hr/event/valentinovo-u-kasteliru-severina-nex-22193', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/221/22193_marketing_banner_1522x682_1733836779.png?v=1733836780', 'FAM_KA_004', 'NEPREUZETA'),
    (35, '2025-02-14', 'Kaštelir, Sportska dvorana Kaštelir Labinci', NULL, 'GALERIJA', 'STUDENT', 'https://www.entrio.hr/event/valentinovo-u-kasteliru-severina-nex-22193', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/221/22193_marketing_banner_1522x682_1733836779.png?v=1733836780', 'STU_KA_005', 'NEPREUZETA'),
    (36, '2025-02-14', 'Kaštelir, Sportska dvorana Kaštelir Labinci', NULL, 'VIP_LOZA', 'VIP', 'https://www.entrio.hr/event/valentinovo-u-kasteliru-severina-nex-22193', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/221/22193_marketing_banner_1522x682_1733836779.png?v=1733836780', 'VIP_KA_006', 'NEPREUZETA'),
    (37, '2025-02-14', 'Kaštelir, Sportska dvorana Kaštelir Labinci', NULL, 'TRIBINA_A', 'PREMIUM', 'https://www.entrio.hr/event/valentinovo-u-kasteliru-severina-nex-22193', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/221/22193_marketing_banner_1522x682_1733836779.png?v=1733836780', 'PREM_KA_007', 'NEPREUZETA'),
    (38, '2025-02-14', 'Kaštelir, Sportska dvorana Kaštelir Labinci', NULL, 'TRIBINA_B', 'STANDARD', 'https://www.entrio.hr/event/valentinovo-u-kasteliru-severina-nex-22193', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/221/22193_marketing_banner_1522x682_1733836779.png?v=1733836780', 'STAN_KA_008', 'NEPREUZETA'),
    (39, '2025-02-14', 'Kaštelir, Sportska dvorana Kaštelir Labinci', NULL, 'PARTER', 'FAMILY', 'https://www.entrio.hr/event/valentinovo-u-kasteliru-severina-nex-22193', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/221/22193_marketing_banner_1522x682_1733836779.png?v=1733836780', 'FAM_KA_009', 'NEPREUZETA'),
    (40, '2025-02-14', 'Kaštelir, Sportska dvorana Kaštelir Labinci', NULL, 'GALERIJA', 'STUDENT', 'https://www.entrio.hr/event/valentinovo-u-kasteliru-severina-nex-22193', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/221/22193_marketing_banner_1522x682_1733836779.png?v=1733836780', 'STU_KA_010', 'NEPREUZETA');

-- Insert tickets for TIP ULAZNICA 5 (16.01.2025, Zagreb, Tvornica Kulture)
INSERT INTO ulaznica (id_ulaznice, datum_koncerta, lokacija_koncerta, id_korisnika, odabrana_zona, vrsta_ulaznice, url_info, url_slika, sifra_ulaznice, status)
VALUES
    (41, '2025-01-16', 'Zagreb, Tvornica Kulture', NULL, 'VIP_LOZA', 'VIP', 'https://www.entrio.hr/event/urban4-tvornica-kulture-21480', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/214/21480_marketing_banner_1522x682_1734961019.png?v=1734961020', 'VIP_ZG_001', 'NEPREUZETA'),
    (42, '2025-01-16', 'Zagreb, Tvornica Kulture', NULL, 'TRIBINA_A', 'PREMIUM', 'https://www.entrio.hr/event/urban4-tvornica-kulture-21480', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/214/21480_marketing_banner_1522x682_1734961019.png?v=1734961020', 'PREM_ZG_002', 'NEPREUZETA'),
    (43, '2025-01-16', 'Zagreb, Tvornica Kulture', NULL, 'TRIBINA_B', 'STANDARD', 'https://www.entrio.hr/event/urban4-tvornica-kulture-21480', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/214/21480_marketing_banner_1522x682_1734961019.png?v=1734961020', 'STAN_ZG_003', 'NEPREUZETA'),
    (44,'2025-01-16', 'Zagreb, Tvornica Kulture', NULL, 'PARTER', 'FAMILY', 'https://www.entrio.hr/event/urban4-tvornica-kulture-21480', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/214/21480_marketing_banner_1522x682_1734961019.png?v=1734961020', 'FAM_ZG_004', 'NEPREUZETA'),
    (45, '2025-01-16', 'Zagreb, Tvornica Kulture', NULL, 'GALERIJA', 'STUDENT', 'https://www.entrio.hr/event/urban4-tvornica-kulture-21480', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/214/21480_marketing_banner_1522x682_1734961019.png?v=1734961020', 'STU_ZG_005', 'NEPREUZETA'),
    (46, '2025-01-16', 'Zagreb, Tvornica Kulture', NULL, 'VIP_LOZA', 'VIP', 'https://www.entrio.hr/event/urban4-tvornica-kulture-21480', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/214/21480_marketing_banner_1522x682_1734961019.png?v=1734961020', 'VIP_ZG_006', 'NEPREUZETA'),
    (47,'2025-01-16', 'Zagreb, Tvornica Kulture', NULL, 'TRIBINA_A', 'PREMIUM', 'https://www.entrio.hr/event/urban4-tvornica-kulture-21480', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/214/21480_marketing_banner_1522x682_1734961019.png?v=1734961020', 'PREM_ZG_007', 'NEPREUZETA'),
    (48, '2025-01-16', 'Zagreb, Tvornica Kulture', NULL, 'TRIBINA_B', 'STANDARD', 'https://www.entrio.hr/event/urban4-tvornica-kulture-21480', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/214/21480_marketing_banner_1522x682_1734961019.png?v=1734961020', 'STAN_ZG_008', 'NEPREUZETA'),
    (49, '2025-01-16', 'Zagreb, Tvornica Kulture', NULL, 'PARTER', 'FAMILY', 'https://www.entrio.hr/event/urban4-tvornica-kulture-21480', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/214/21480_marketing_banner_1522x682_1734961019.png?v=1734961020', 'FAM_ZG_009', 'NEPREUZETA'),
    (50, '2025-01-16', 'Zagreb, Tvornica Kulture', NULL, 'GALERIJA', 'STUDENT', 'https://www.entrio.hr/event/urban4-tvornica-kulture-21480', 'https://cdn.entr.io/images/events/campaigns/organizer_uploads/214/21480_marketing_banner_1522x682_1734961019.png?v=1734961020', 'STU_ZG_010', 'NEPREUZETA');

-- For idizvodaca = 1, idulaznice 1 to 10
INSERT INTO izvodac_ulaznica (idizvodaca, idulaznice) VALUES
                                                          (1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
                                                          (1, 6), (1, 7), (1, 8), (1, 9), (1, 10);

-- For idizvodaca = 2, idulaznice 11 to 20
INSERT INTO izvodac_ulaznica (idizvodaca, idulaznice) VALUES
                                                          (2, 11), (2, 12), (2, 13), (2, 14), (2, 15),
                                                          (2, 16), (2, 17), (2, 18), (2, 19), (2, 20);

-- For idizvodaca = 3, idulaznice 21 to 30
INSERT INTO izvodac_ulaznica (idizvodaca, idulaznice) VALUES
                                                          (3, 21), (3, 22), (3, 23), (3, 24), (3, 25),
                                                          (3, 26), (3, 27), (3, 28), (3, 29), (3, 30);

-- For idizvodaca = 4, idulaznice 31 to 40
INSERT INTO izvodac_ulaznica (idizvodaca, idulaznice) VALUES
                                                          (4, 31), (4, 32), (4, 33), (4, 34), (4, 35),
                                                          (4, 36), (4, 37), (4, 38), (4, 39), (4, 40);

-- For idizvodaca = 5, idulaznice 41 to 50
INSERT INTO izvodac_ulaznica (idizvodaca, idulaznice) VALUES
                                                          (5, 41), (5, 42), (5, 43), (5, 44), (5, 45),
                                                          (5, 46), (5, 47), (5, 48), (5, 49), (5, 50);