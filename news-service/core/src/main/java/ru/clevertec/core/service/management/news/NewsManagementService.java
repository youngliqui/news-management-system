package ru.clevertec.core.service.management.news;

import ru.clevertec.common.dto.news.NewsCreateDTO;
import ru.clevertec.common.dto.news.NewsInfoDTO;
import ru.clevertec.common.dto.news.NewsPatchDTO;
import ru.clevertec.common.dto.news.NewsUpdateDTO;

/**
 * Интерфейс для управления новостями.
 * <p>
 * Этот интерфейс определяет методы для создания, обновления,
 * частичного обновления и удаления новостей.
 * </p>
 */
public interface NewsManagementService {
    /**
     * Создает новую новость.
     *
     * @param newsCreateDTO данные для создания новой новости
     * @return DTO созданной новости
     */
    NewsInfoDTO createNews(NewsCreateDTO newsCreateDTO);

    /**
     * Обновляет существующую новость.
     *
     * @param newsId        идентификатор новости, которую нужно обновить
     * @param newsUpdateDTO данные для обновления новости
     * @return DTO обновленной новости
     */
    NewsInfoDTO updateNews(Long newsId, NewsUpdateDTO newsUpdateDTO);

    /**
     * Частично обновляет существующую новость.
     *
     * @param newsId       идентификатор новости, которую нужно частично обновить
     * @param newsPatchDTO данные для частичного обновления новости
     * @return DTO обновленной новости после частичного изменения
     */
    NewsInfoDTO patchNews(Long newsId, NewsPatchDTO newsPatchDTO);

    /**
     * Удаляет новость по её идентификатору.
     *
     * @param newsId идентификатор новости, которую нужно удалить
     */
    void deleteNewsById(Long newsId);
}
