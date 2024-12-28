package ru.clevertec.core.service.information.comment;

import org.springframework.data.domain.Page;
import ru.clevertec.common.dto.comment.CommentInfoDTO;
import ru.clevertec.core.entity.CommentEntity;

/**
 * Интерфейс для получения информации о комментариях.
 * <p>
 * Этот интерфейс определяет методы для выполнения операций
 * над комментариями, включая получение комментариев по идентификатору
 * новости, поиск комментариев и преобразование их в DTO.
 * </p>
 */
public interface CommentInformationService {
    /**
     * Получает все комментарии к указанной новости с пагинацией.
     *
     * @param newsId идентификатор новости, к которой относятся комментарии
     * @param size   количество комментариев на странице
     * @param page   номер страницы
     * @return страница DTO комментариев
     */
    Page<CommentInfoDTO> getComments(Long newsId, int size, int page);

    /**
     * Получает комментарий по его идентификатору.
     *
     * @param commentId идентификатор комментария
     * @return объект CommentEntity, соответствующий заданному идентификатору
     */
    CommentEntity getCommentById(Long commentId);

    /**
     * Получает информацию о комментарии по его идентификатору и идентификатору новости.
     *
     * @param commentId идентификатор комментария
     * @param newsId    идентификатор новости, к которой относится комментарий
     * @return DTO информации о комментарии
     */
    CommentInfoDTO getCommentInfoById(Long commentId, Long newsId);

    /**
     * Выполняет поиск комментариев по запросу с пагинацией.
     *
     * @param query строка запроса для поиска комментариев
     * @param size  количество комментариев на странице
     * @param page  номер страницы
     * @return страница DTO найденных комментариев
     */
    Page<CommentInfoDTO> searchComments(String query, int size, int page);
}
