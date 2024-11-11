package ru.clevertec.core.service.information.news;

import org.springframework.data.domain.Page;
import ru.clevertec.common.dto.news.NewsInfoDTO;
import ru.clevertec.core.entity.NewsEntity;

/**
 * Интерфейс для получения информации о новостях.
 * <p>
 * Этот интерфейс определяет методы для выполнения операций
 * над новостями, включая получение всех новостей, поиск
 * новостей и преобразование их в DTO.
 * </p>
 */
public interface NewsInformationService {
    /**
     * Получает все новости с пагинацией.
     *
     * @param size количество новостей на странице
     * @param page номер страницы
     * @return страница DTO новостей
     */
    Page<NewsInfoDTO> getAll(int size, int page);

    /**
     * Получает новость по её идентификатору.
     *
     * @param newsId идентификатор новости
     * @return объект NewsEntity, соответствующий заданному идентификатору
     */
    NewsEntity getNewsById(Long newsId);

    /**
     * Получает информацию о новости по её идентификатору.
     *
     * @param newsId идентификатор новости
     * @return DTO информации о новости
     */
    NewsInfoDTO getNewsInfoById(Long newsId);

    /**
     * Выполняет поиск новостей по запросу с пагинацией.
     *
     * @param query строка запроса для поиска новостей
     * @param size  количество новостей на странице
     * @param page  номер страницы
     * @return страница DTO найденных новостей
     */
    Page<NewsInfoDTO> searchNews(String query, int size, int page);
}
