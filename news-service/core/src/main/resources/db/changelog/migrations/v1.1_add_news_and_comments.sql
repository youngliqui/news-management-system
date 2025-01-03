INSERT INTO news (title, text)
VALUES ('Новый закон о защите данных вступает в силу',
        'С 1 ноября 2024 года вступает в силу новый закон, который усиливает защиту персональных данных граждан. Эксперты ожидают, что это приведет к значительным изменениям в сфере онлайн-бизнеса.'),
       ('Исследование: кофе может улучшить память',
        'Недавние исследования показывают, что кофе может не только бодрить, но и улучшать кратковременную память. Ученые рекомендуют пить кофе в умеренных количествах.'),
       ('Климатические изменения: что нас ждет в будущем?',
        'Ученые представили новый отчет о климатических изменениях, в котором предупреждают о возможных последствиях для планеты в ближайшие десятилетия.'),
       ('Технологии будущего: искусственный интеллект',
        'Искусственный интеллект продолжает развиваться, и его применение охватывает все больше сфер жизни. Каковы перспективы развития этой технологии?'),
       ('Спорт: сборная выиграла чемпионат мира',
        'Сборная страны по футболу одержала победу на чемпионате мира, обыграв команду соперников со счетом 3:1. Это первая победа за последние 20 лет.'),
       ('Культура: выставка современного искусства',
        'В городе открылась выставка современного искусства, где представлены работы известных художников со всего мира. Посетители смогут увидеть уникальные произведения.'),
       ('Наука: новое открытие в области медицины',
        'Учёные сделали прорыв в лечении редкого заболевания, разработав новый метод терапии, который уже показал положительные результаты на клинических испытаниях.'),
       ('Путешествия: лучшие направления на 2025 год',
        'Эксперты составили список лучших направлений для путешествий в 2025 году, включая экзотические страны и культурные столицы Европы.'),
       ('Экономика: рост цен на жилье продолжается',
        'По данным статистики, цены на жилье продолжают расти, что вызывает беспокойство у потенциальных покупателей и арендаторов.'),
       ('Здоровье: важность физической активности',
        'Специалисты подчеркивают важность регулярной физической активности для поддержания здоровья и профилактики заболеваний.'),
       ('Технологии: смартфоны нового поколения',
        'На рынке появились новые смартфоны с уникальными функциями и улучшенной производительностью. Как выбрать лучший вариант?'),
       ('Образование: дистанционное обучение становится нормой',
        'Дистанционное обучение продолжает набирать популярность, и многие учебные заведения переходят на гибридные форматы обучения.'),
       ('Экология: борьба с пластиковыми отходами',
        'Организации по охране окружающей среды запускают новые инициативы по сокращению пластиковых отходов и популяризации переработки.'),
       ('Спорт: олимпийские игры 2024',
        'Олимпийские игры 2024 года обещают стать самыми зрелищными за всю историю благодаря новым видам спорта и участию звезд мирового уровня.'),
       ('Кулинария: рецепты осенних блюд',
        'Осень — время сбора урожая. Мы собрали лучшие рецепты осенних блюд, которые порадуют вас и ваших близких.'),
       ('Наука: космическая миссия к Марсу',
        'NASA анонсировало новую миссию по исследованию Марса с использованием современных технологий и робототехники.'),
       ('Финансы: как правильно инвестировать?',
        'Эксперты делятся советами по инвестициям в условиях нестабильной экономики и рассказывают о популярных инструментах для начинающих инвесторов.'),
       ('История: памятники культуры под угрозой',
        'Множество памятников истории и культуры находятся под угрозой исчезновения из-за человеческой деятельности и природных катастроф.'),
       ('Технологии: будущее виртуальной реальности',
        'Виртуальная реальность продолжает развиваться, открывая новые горизонты для развлечений, образования и бизнеса.'),
       ('Психология: как справиться со стрессом?',
        'Специалисты предлагают эффективные методы борьбы со стрессом в условиях современной жизни, включая медитацию и физическую активность.');

INSERT INTO comments (username, text, news_id)
VALUES
    -- 1 новость
    ('ivan_petrov', 'Очень интересно! Надеюсь, закон действительно поможет.', 1),
    ('alexey_kovalev', 'Закон действительно нужен в наше время!', 1),
    ('maria_sokolova', 'Интересно, как это повлияет на компании.', 1),
    ('dmitry_nikitin', 'Надеюсь, закон будет эффективно исполняться.', 1),
    ('elena_borisova', 'Это шаг в правильном направлении!', 1),
    ('sergey_petrov', 'Жду подробностей о применении закона.', 1),
    ('anastasia_ivanova', 'Буду следить за новостями по этому закону.', 1),
    ('nikita_litvinov', 'Надеюсь, это поможет защитить наши данные.', 1),
    ('vladimir_zhukov', 'Закон должен быть принят как можно скорее!', 1),
    ('irina_koroleva', 'Хорошая инициатива! Ждем результатов.', 1),

    -- 2 новость
    ('anna_sidorova', 'Кофе — это прекрасно! Буду пить больше.', 2),
    ('valentina_morozova', 'Кофе — мой лучший друг!', 2),
    ('andrey_shevchenko', 'Не знал, что кофе так полезен!', 2),
    ('natalia_ivanova', 'Кофе помогает мне сосредоточиться на работе.', 2),
    ('artem_kuzmin', 'Интересно, как кофе влияет на мозг.', 2),
    ('olga_smirnova', 'Обязательно буду пить кофе перед экзаменами!', 2),
    ('dmitry_frolov', 'Кофе действительно улучшает настроение!', 2),
    ('ekaterina_maslova', 'Согласна, кофе — это чудо!', 2),
    ('maxim_sokolov', 'Кофе — отличный способ начать утро!', 2),
    ('sergey_andreev', 'Умеренность — ключ к успеху с кофе!', 2),

    -- 3 новость
    ('maxim_ivanov', 'Климатические изменения — это серьезно. Надо что-то делать.', 3),
    ('anton_belov', 'Нужно принимать меры уже сейчас!', 3),
    ('irina_koroleva', 'Климатические изменения затрагивают всех нас.', 3),
    ('vladimir_zhukov', 'Важно информировать людей о последствиях.', 3),
    ('ekaterina_nikiforova', 'Согласна, необходимо действовать быстро!', 3),
    ('nikita_litvinov', 'Давайте поддерживать экологические инициативы!', 3),
    ('maria_nazarova', 'Каждый из нас может внести свой вклад!', 3),
    ('sergey_mikhalev', 'Надеюсь, мы сможем изменить ситуацию к лучшему.', 3),
    ('alexey_vasilev', 'Отчет очень тревожный. Нужно действовать сейчас!', 3),
    ('dmitry_pavlenko', 'Климат требует нашего внимания и заботы!', 3),

    -- 4 новость
    ('olga_kuznetsova', 'Искусственный интеллект меняет мир!', 4),
    ('mikhail_pavlov', 'Искусственный интеллект — будущее технологий!', 4),
    ('svetlana_kalashnikova', 'Интересно узнать больше о применении ИИ.', 4),
    ('evgeny_danilov', 'Как ИИ изменит нашу повседневную жизнь?', 4),
    ('anastasia_ivanova', 'Надеюсь, технологии будут использоваться во благо.', 4),
    ('sergey_kovalev', 'ИИ — это не только возможности, но и риски.', 4),
    ('dmitry_frolov', 'Какое будущее ждет нас с ИИ?', 4),
    ('irina_savina', 'Технологии развиваются стремительно. Это впечатляет!', 4),
    ('alexander_borisov', 'Согласен! Важно использовать ИИ ответственно.', 4),
    ('marina_petrova', 'Будущее уже здесь! Интересно наблюдать за развитием.', 4),

    -- 5 новость
    ('sergey_smirnov', 'Поздравляю сборную! Это настоящая победа!', 5),
    ('dmitry_frolov', 'Поздравления команде! Вы молодцы!', 5),
    ('irina_koroleva', 'Эта победа вдохновляет на новые достижения!', 5),
    ('alexander_borisov', 'Сборная показала отличный футбол!', 5),
    ('marina_petrova', 'Надеюсь, это начало новой эры в спорте!', 5),
    ('nikita_mironov', 'Смотрел матч — было очень волнительно!', 5),
    ('olga_nikolaeva', 'Это была потрясающая игра! Молодцы ребята!', 5),
    ('anton_belov', 'Сборная доказала свою силу и сплоченность. Поздравляю всех болельщиков!', 5),
    ('ekaterina_vasileva', 'Эмоции зашкаливают! Это исторический момент для страны!', 5),
    ('mikhail_pavlov', 'Футбол объединяет людей. Поздравляю всех с победой!', 5),

    -- 6 новость
    ('daria_romanova', 'Выставка звучит интересно! Обязательно схожу.', 6),
    ('olga_nikolaeva', 'Выставки современного искусства всегда вдохновляют.', 6),
    ('sergey_fedotov', 'Хочу увидеть работы современных художников.', 6),
    ('anna_belova', 'Интересно узнать о новых направлениях в искусстве.', 6),
    ('maxim_sokolov', 'Выставка — отличная возможность для самовыражения.', 6),
    ('ekaterina_maslova', 'Современное искусство всегда вызывает эмоции!', 6),
    ('nikita_litvinov', 'Выставка станет отличным событием для города!', 6),
    ('irina_koroleva', 'Надеюсь увидеть работы талантливых художников!', 6),
    ('anton_pavlov', 'Это отличная возможность познакомиться с современным искусством.', 6),
    ('elena_borisovna', 'Выставка должна привлечь много посетителей. Жду с нетерпением!', 6),

    -- 7 новость
    ('pavel_fedorov', 'Надеюсь, этот метод поможет многим людям.', 7),
    ('ivan_kuznetsov', 'Это открытие может спасти жизни многих людей.', 7),
    ('maria_nazarova', 'Надеюсь, метод пройдет все испытания успешно.', 7),
    ('alexey_vasilev', 'Научные достижения вдохновляют на надежду!', 7),
    ('dmitry_pavlenko', 'Важно продолжать исследования в этой области.', 7),
    ('irina_zhuravleva', 'Медицинские открытия всегда радуют!', 7),
    ('sergei_andreev', 'Это может стать прорывом в медицине. Ждем результатов испытаний!', 7),
    ('ekaterina_maslova', 'Научные исследования — это важно для будущего человечества.', 7),
    ('anton_belov', 'Каждое новое открытие приближает нас к лечению болезней.', 7),
    ('nikita_litvinov', 'Медицинские инновации могут изменить жизни людей к лучшему.', 7),

    -- 8 новость
    ('ivan_petrov', 'Отличные направления! Хочу посетить некоторые из них.', 8),
    ('sergey_mikhalev', 'Экзотические страны всегда привлекают внимание!', 8),
    ('anna_sharova', 'Планирую поездку в Испанию! Очень жду отпуска.', 8),
    ('maxim_titov', 'Спасибо за советы! Хочу посетить Японию и Италию.', 8),
    ('ekaterina_bondareva', 'Согласна с выбором направлений! Хочу в Грецию.', 8),
    ('dmitry_nemirovsky', 'Отличный список! Буду планировать путешествия заранее.', 8),
    ('irina_nikolaeva', 'Каждое новое место — это новые впечатления и эмоции!', 8),
    ('alexey_frolov', 'Путешествия обогащают наш опыт и знания о мире!', 8),
    ('maria_sokolova', 'Хочу посетить все эти места. Это будет незабываемо!', 8),
    ('sergei_andreev', 'Путешествия расширяют горизонты. Надо больше исследовать!', 8),

    -- 9 новость
    ('ivan_ivanovich', 'Цены на жилье растут слишком быстро. Это проблема для многих семей.', 9),
    ('olga_petrovna', 'Надеюсь, ситуация изменится к лучшему. Жилищный вопрос актуален для всех нас.', 9),
    ('sergei_andreev', 'Пора задуматься о покупке жилья. Цены растут каждый день!', 9),
    ('maria_sidorovna', 'Аренда становится все менее доступной. Нужно искать решения проблемы.', 9),
    ('alexey_mikhailovich', 'Рынок недвижимости требует внимания со стороны властей. Надо что-то менять!', 9),
    ('valentina_morozova', 'Цены на жилье просто зашкаливают!', 9),
    ('anton_belov', 'Важно следить за тенденциями на рынке недвижимости.', 9),
    ('ekaterina_vasileva', 'Покупка жилья становится настоящим вызовом.', 9),
    ('dmitry_frolov', 'Нужно искать альтернативные варианты жилья.', 9),
    ('irina_koroleva', 'Жилищный вопрос требует комплексного подхода.', 9),

    -- 10 новость
    ('daria_kovalchuk', 'Физическая активность действительно важна. Я стараюсь заниматься каждый день!', 10),
    ('pavel_smirnov', 'Регулярные тренировки помогают поддерживать здоровье и хорошее настроение.', 10),
    ('irina_koroleva', 'Спорт — это не только здоровье, но и радость жизни. Нужно больше заниматься физкультурой!', 10),
    ('anton_pavlov', 'Физическая активность помогает справляться со стрессом и улучшает общее состояние организма.',
     10),
    ('elena_borisovna', 'Здоровый образ жизни — залог долголетия. Нужно следить за собой!', 10),
    ('nikita_litvinov', 'Регулярные занятия спортом улучшают качество жизни.', 10),
    ('sergey_mikhalev', 'Физическая активность должна стать частью нашей повседневной жизни.', 10),
    ('maria_nazarova', 'Спорт помогает не только телу, но и душе!', 10),
    ('alexey_vasilev', 'Давайте делать физическую активность частью нашей культуры!', 10),
    ('irina_sidorovna', 'Спорт объединяет людей и создает дружескую атмосферу!', 10),

    -- 11 новость
    ('ivan_petrov', 'Это отличная новость! Здоровье — это главное.', 11),
    ('anna_sidorova', 'Важно заниматься спортом и следить за своим здоровьем.', 11),
    ('maxim_ivanov', 'Физическая активность действительно помогает поддерживать форму.', 11),
    ('olga_kuznetsova', 'Надеюсь, люди станут более активными!', 11),
    ('sergey_smirnov', 'Регулярные тренировки — залог здоровья!', 11),
    ('daria_romanova', 'Спорт — это не только здоровье, но и удовольствие!', 11),
    ('pavel_fedorov', 'Нужно больше говорить о важности физической активности.', 11),
    ('ekaterina_vasileva', 'Согласна, физическая активность должна быть в жизни каждого!', 11),
    ('nikolai_morozov', 'Здоровый образ жизни — это круто!', 11),
    ('irina_nikolaeva', 'Давайте вдохновлять друг друга на занятия спортом!', 11),

    -- 12 новость
    ('ivan_petrov', 'Новые технологии всегда интересны!', 12),
    ('anna_sidorova', 'Смартфоны становятся все более умными.', 12),
    ('maxim_ivanov', 'Интересно, какие функции будут следующими?', 12),
    ('olga_kuznetsova', 'Технологии развиваются так быстро!', 12),
    ('sergey_smirnov', 'Как выбрать лучший смартфон среди такого множества?', 12),
    ('daria_romanova', 'Надеюсь, новые модели будут доступны по разумным ценам.', 12),
    ('pavel_fedorov', 'Смартфоны — это не просто телефоны, а настоящие помощники!', 12),
    ('ekaterina_vasileva', 'Интересно, как они будут выглядеть через несколько лет.', 12),
    ('nikolai_morozov', 'Технологии меняют наш образ жизни.', 12),
    ('irina_nikolaeva', 'Жду с нетерпением новых анонсов!', 12),

    -- 13 новость
    ('ivan_petrov', 'Дистанционное обучение — это будущее!', 13),
    ('anna_sidorova', 'Гибридные форматы обучения очень удобны.', 13),
    ('maxim_ivanov', 'Важно, чтобы качество образования не страдало.', 13),
    ('olga_kuznetsova', 'Надеюсь, студенты смогут адаптироваться к новым условиям.', 13),
    ('sergey_smirnov', 'Дистанционное обучение открывает новые возможности.', 13),
    ('daria_romanova', 'Как это повлияет на качество образования?', 13),
    ('pavel_fedorov', 'Интересно, как будут развиваться технологии обучения.', 13),
    ('ekaterina_vasileva', 'Надеюсь, появится больше интерактивных платформ.', 13),
    ('nikolai_morozov', 'Это может быть отличным решением для многих студентов.', 13),
    ('irina_nikolaeva', 'Дистанционное обучение требует самодисциплины!', 13),

    -- 14 новость
    ('ivan_petrov', 'Отлично! Борьба с пластиковыми отходами важна для экологии.', 14),
    ('anna_sidorova', 'Надеюсь, эти инициативы действительно помогут!', 14),
    ('maxim_ivanov', 'Пластиковые отходы — серьезная проблема.', 14),
    ('olga_kuznetsova', 'Важно привлекать внимание к этой проблеме!', 14),
    ('sergey_smirnov', 'Каждый из нас может внести свой вклад!', 14),
    ('daria_romanova', 'Интересно узнать о новых методах переработки.', 14),
    ('pavel_fedorov', 'Экология требует нашего внимания и заботы!', 14),
    ('ekaterina_vasileva', 'Согласна! Мы должны заботиться о планете.', 14),
    ('nikolai_morozov', 'Пора действовать и менять свои привычки!', 14),
    ('irina_nikolaeva', 'Давайте поддерживать экологические инициативы!', 14),

    -- 15 новость
    ('ivan_petrov', 'Олимпийские игры всегда вдохновляют!', 15),
    ('anna_sidorova', 'Жду с нетерпением начала соревнований!', 15),
    ('maxim_ivanov', 'Интересно увидеть новые виды спорта на Олимпиаде!', 15),
    ('olga_kuznetsova', 'Спорт объединяет людей со всего мира!', 15),
    ('sergey_smirnov', 'Это будет незабываемое событие!', 15),
    ('daria_romanova', 'Пусть победит сильнейший!', 15),
    ('pavel_fedorov', 'Олимпийские игры — это праздник спорта!', 15),
    ('ekaterina_vasileva', 'Надеюсь, наши спортсмены покажут отличные результаты!', 15),
    ('nikolai_morozov', 'Волнение перед Олимпиадой всегда велико!', 15),
    ('irina_nikolaeva', 'Спортсмены готовятся к этому событию всю жизнь!', 15),

    -- 16 новость
    ('ivan_petrov', 'Осень — время новых вкусов и ароматов!', 16),
    ('anna_sidorova', 'Рецепты осенних блюд всегда радуют!', 16),
    ('maxim_ivanov', 'Обожаю осень за ее разнообразие продуктов!', 16),
    ('olga_kuznetsova', 'Хочу попробовать все эти рецепты!', 16),
    ('sergey_smirnov', 'Осенние блюда всегда согревают душу.', 16),
    ('daria_romanova', 'Кулинария — это искусство!', 16),
    ('pavel_fedorov', 'Осень вдохновляет на создание новых блюд.', 16),
    ('ekaterina_vasileva', 'Это время года идеально подходит для готовки.', 16),
    ('nikolai_morozov', 'Рецепты осенних блюд всегда актуальны.', 16),
    ('irina_nikolaeva', 'Давайте делиться любимыми рецептами осенней кухни!', 16),

    -- 17 новость
    ('ivan_petrov', 'Космические миссии всегда вызывают интерес!', 17),
    ('anna_sidorova', 'Исследование Марса — это шаг вперед для науки.', 17),
    ('maxim_ivanov', 'Какое захватывающее время для астрономии!', 17),
    ('olga_kuznetsova', 'Надеюсь, мы узнаем много нового о Марсе.', 17),
    ('sergey_smirnov', 'Космос полон загадок. Ждем результатов миссии.', 17),
    ('daria_romanova', 'Каждая новая миссия приближает нас к разгадкам.', 17),
    ('pavel_fedorov', 'Научные открытия в космосе вдохновляют на мечты.', 17),
    ('ekaterina_vasileva', 'Это невероятно! Как мы можем помочь в исследованиях?', 17),
    ('nikolai_morozov', 'Космос — это будущее человечества.', 17),
    ('irina_nikolaeva', 'Жду с нетерпением новостей с Марса!', 17),

    -- 18 новость
    ('ivan_petrov', 'Инвестиции — важный аспект финансового планирования.', 18),
    ('anna_sidorova', 'Как правильно инвестировать в условиях нестабильности?', 18),
    ('maxim_ivanov', 'Интересно узнать о новых инвестиционных инструментах.', 18),
    ('olga_kuznetsova', 'Финансовая грамотность нужна каждому.', 18),
    ('sergey_smirnov', 'Надеюсь, советы будут полезны новичкам.', 18),
    ('daria_romanova', 'Инвестиции могут изменить нашу жизнь к лучшему.', 18),
    ('pavel_fedorov', 'Важно понимать риски при инвестировании.', 18),
    ('ekaterina_vasileva', 'Финансовая независимость — это цель многих людей.', 18),
    ('nikolai_morozov', 'Как начать инвестировать с минимальными рисками?', 18),
    ('irina_nikolaeva', 'Каждый должен знать основы инвестирования!', 18),

    -- 19 новость
    ('ivan_petrov', 'Памятники культуры нужно защищать и сохранять для будущих поколений.', 19),
    ('anna_sidorova', 'Важно помнить о нашем наследии и истории.', 19),
    ('maxim_ivanov', 'Каждый памятник имеет свою уникальную историю.', 19),
    ('olga_kuznetsova', 'Мы должны беречь нашу культуру и традиции.', 19),
    ('sergey_smirnov', 'Проблема сохранения памятников актуальна во всем мире.', 19),
    ('daria_romanova', 'Надеюсь, будут приняты меры по их защите.', 19),
    ('pavel_fedorov', 'Культура — это то, что нас объединяет.', 19),
    ('ekaterina_vasileva', 'Каждый из нас может внести свой вклад в сохранение культуры.', 19),
    ('nikolai_morozov', 'Важно привлекать внимание к этой проблеме через образование.', 19),
    ('irina_nikolaeva', 'Памятники культуры — это часть нашей идентичности и истории.', 19),

    -- 20 новость
    ('ivan_petrov', 'Виртуальная реальность открывает новые горизонты возможностей!', 20),
    ('anna_sidorova', 'Это будущее развлечений и образования!', 20),
    ('maxim_ivanov', 'Как технологии изменят наш опыт взаимодействия с миром?', 20),
    ('olga_kuznetsova', 'Виртуальная реальность может помочь в обучении и терапии!', 20),
    ('sergey_smirnov', 'Жду новых разработок в этой области!', 20),
    ('daria_romanova', 'Это захватывающее время для технологий и науки!', 20),
    ('pavel_fedorov', 'Виртуальная реальность уже меняет нашу жизнь сегодня!', 20),
    ('ekaterina_vasileva', 'Будущее виртуальной реальности выглядит многообещающим!', 20),
    ('nikolai_morozov', 'Как мы можем использовать виртуальную реальность в повседневной жизни?', 20),
    ('irina_nikolaeva', 'Виртуальная реальность может изменить наше восприятие мира вокруг нас!', 20);