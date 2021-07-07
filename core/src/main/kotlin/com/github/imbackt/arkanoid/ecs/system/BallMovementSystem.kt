package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.*
import com.github.imbackt.arkanoid.ecs.component.*
import ktx.ashley.EngineEntity
import ktx.ashley.allOf
import ktx.math.vec2
import kotlin.math.abs

class BallMovementSystem(
    private val paddle: Entity
) : IteratingSystem(allOf(BallComponent::class, Box2DComponent::class).get()), ContactListener {
    private val contactPoint = vec2()
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val b2DCmp = entity.b2DCmp
        if (!paddle.playerCmp.isGameStarted) {
            b2DCmp.body.setTransform(paddle.b2DCmp.body.worldCenter.x, b2DCmp.body.worldCenter.y, 0f)
        }
    }

    override fun beginContact(contact: Contact) {
        contactPoint.set(contact.worldManifold.points[0])
    }

    override fun endContact(contact: Contact) {
        val ballBody = if (contact.fixtureA.userData == "ball") {
            contact.fixtureA.body
        } else {
            contact.fixtureB.body
        }
        if (contact.fixtureA.userData == "paddle" || contact.fixtureB.userData == "paddle") {
            ballBody.linearVelocity = (vec2(0f, 0f))
            val paddleCenter = paddle.b2DCmp.body.worldCenter
            val difference = paddleCenter.x - contactPoint.x
            if (contactPoint.x < paddleCenter.x) {
                ballBody.applyForce(vec2(-abs(difference * 200f), 250f), ballBody.worldCenter, true)
            } else {
                ballBody.applyForce(vec2(abs(difference * 200f), 250f), ballBody.worldCenter, true)
            }
        } else if (contact.fixtureA.userData is EngineEntity || contact.fixtureB.userData is EngineEntity) {
            val brickEngineEntity = if (contact.fixtureA.userData is EngineEntity) {
                contact.fixtureA.userData as EngineEntity
            } else {
                contact.fixtureB.userData as EngineEntity
            }
            engine.removeEntity(brickEngineEntity.entity)
        }
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold) {

    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse) {
    }

}