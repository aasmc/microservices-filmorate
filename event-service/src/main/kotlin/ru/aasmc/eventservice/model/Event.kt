package ru.aasmc.eventservice.model

import ru.aasmc.eventservice.enums.CommonEventOperation
import ru.aasmc.eventservice.enums.CommonEventType
import javax.persistence.*

@Entity
@Table(name = "EVENTS")
@SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "film_sequence", allocationSize = 1)
class Event(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
        @Column(name = "EVENT_ID")
        var id: Long?,
        var timestamp: Long,
        @Column(name = "USER_ID")
        var userId: Long,
        @Column(name = "EVENT_TYPE")
        var eventType: CommonEventType,
        var operation: CommonEventOperation,
        @Column(name = "ENTITY_ID")
        var entityId: Long
)