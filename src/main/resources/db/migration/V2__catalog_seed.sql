INSERT INTO categories (id, name, color_hex) VALUES
                                                 ('11111111-1111-1111-1111-111111111111','Força', '#60A5FA'),
                                                 ('22222222-2222-2222-2222-222222222222','Sabedoria', '#F472B6'),
                                                 ('33333333-3333-3333-3333-333333333333','Energia', '#34D399'),
                                                 ('44444444-4444-4444-4444-444444444444','Disciplina', '#FBBF24'),
                                                 ('55555555-5555-5555-5555-555555555555','Social', '#A78BFA')
ON CONFLICT (id) DO NOTHING;

-- Templates
INSERT INTO habit_templates (id, category_id, name, description, default_weekly_target, difficulty, base_points) VALUES
                                                                                                                     ('aaa11111-1111-1111-1111-111111111111','11111111-1111-1111-1111-111111111111','Treinar musculação','Treino de força',3,'MEDIUM',10),
                                                                                                                     ('aaa11111-1111-1111-1111-111111111112','11111111-1111-1111-1111-111111111111','Flexões','Séries curtas',4,'EASY',8),
                                                                                                                     ('bbb22222-2222-2222-2222-222222222221','22222222-2222-2222-2222-222222222222','Meditar 10 min','Atenção plena',5,'EASY',8),
                                                                                                                     ('bbb22222-2222-2222-2222-222222222222','22222222-2222-2222-2222-222222222222','Ler 20 min','Leitura focada',5,'MEDIUM',10),
                                                                                                                     ('ccc33333-3333-3333-3333-333333333331','33333333-3333-3333-3333-333333333333','Beber 2L de água','Hidratação diária',7,'EASY',6),
                                                                                                                     ('ccc33333-3333-3333-3333-333333333332','33333333-3333-3333-3333-333333333333','Dormir >=7h','Higiene do sono',5,'MEDIUM',10),
                                                                                                                     ('ddd44444-4444-4444-4444-444444444441','44444444-4444-4444-4444-444444444444','Planejar o dia','Revisar tarefas',7,'EASY',6),
                                                                                                                     ('ddd44444-4444-4444-4444-444444444442','44444444-4444-4444-4444-444444444444','Pomodoro 4 ciclos','Foco e pausas',5,'HARD',12),
                                                                                                                     ('eee55555-5555-5555-5555-555555555551','55555555-5555-5555-5555-555555555555','Falar com um amigo','Contato significativo',3,'EASY',6),
                                                                                                                     ('eee55555-5555-5555-5555-555555555552','55555555-5555-5555-5555-555555555555','Tempo com a família','Qualidade > quantidade',3,'MEDIUM',10)
ON CONFLICT (id) DO NOTHING;
