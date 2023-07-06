package ru.aasmc.reviewsservice.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = LoggerFactory.getLogger(ReviewController::class.java)

@RestController
@RequestMapping("/reviews")
class ReviewController {
}