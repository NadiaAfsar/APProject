package log;

import model.game.enemies.Enemy;
import model.game.enemies.normal.Necropick;
import model.game.enemies.normal.Omenoct;
import org.apache.log4j.Logger;

public class EnemyLogger {
    public static void getInfo(Logger logger, Enemy enemy) {
        logger.info("x: "+enemy.getX());
        logger.info("y: "+enemy.getY());
        logger.info("center: "+enemy.getCenter().toString());
        if (enemy instanceof Necropick){
            getNecropickInfo(logger, (Necropick) enemy);
        }
        else {
            logger.info("velocity power: " + enemy.getVelocityPower());
            logger.info("velocity: " + enemy.getVelocity().toString());
            logger.info("acceleration: " + enemy.getAcceleration().toString());
            logger.info("acceleration rate: " + enemy.getAccelerationRate().toString());
            logger.info("angle: " + enemy.getAngle());
            logger.info("angular velocity: " + enemy.getAngularVelocity());
            logger.info("angular acceleration: "+enemy.getAngularAcceleration());
            logger.info("angular acceleration rate: "+enemy.getAngularAccelerationRate());
            logger.info("HP: " + enemy.getHP());
            if (enemy instanceof Omenoct) {
                getOmenoctInfo(logger, (Omenoct) enemy);
            }
        }
    }
    private static void getOmenoctInfo(Logger logger, Omenoct omenoct) {
        logger.info("is side chosen: "+omenoct.isSideChosen());
        if (omenoct.isSideChosen()) {
            logger.info("side: " + omenoct.getSide());
        }
        logger.info("is stuck: "+omenoct.isStuck());
    }
    private static void getNecropickInfo(Logger logger, Necropick necropick) {
        logger.info("is announced: "+necropick.isAnnounced());
        logger.info("is appeared: "+necropick.isAppeared());
        logger.info("is disappeared: "+necropick.isDisappeared());
    }
}
