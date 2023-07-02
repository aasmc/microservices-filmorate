package ru.aasmc.filmservice.appevents.listener

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import ru.aasmc.filmservice.appevents.AllLikesDeletedEvent
import ru.aasmc.filmservice.service.FilmLikeService

private val log = LoggerFactory.getLogger(AllLikesDeletedEventListener::class.java)

@Component
class AllLikesDeletedEventListener(
    private val likesService: FilmLikeService
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleAllLikesDeletedEvent(event: AllLikesDeletedEvent) {
        log.info("All Likes Deleted Event received.")
        likesService.deleteAllLikes(event.filmId, event.timeOfEvent)
    }

}