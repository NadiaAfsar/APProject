package log;

import model.enemies.Enemy;
import model.enemies.normal.Omenoct;
import model.interfaces.movement.Direction;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class EnemyLogger {
    public static void getInfo(Logger logger, Enemy enemy) {
        logger.info("x: "+enemy.getX());
        logger.info("y: "+enemy.getY());
        logger.info("center: ("+enemy.getCenter().getX()+","+enemy.getCenter().getY()+")");
        Direction direction = enemy.getDirection();
        logger.info("direction: ("+direction.getDy()+","+direction.getDy());
        logger.info("velocity power: "+enemy.getVelocityPower());
        logger.info("velocity: ("+enemy.getVelocity().getX()+","+enemy.getVelocity().getY()+")");
        logger.info("acceleration: ("+enemy.getAcceleration().getX()+","+enemy.getAcceleration().getY()+")");
        logger.info("acceleration rate: ("+enemy.getAccelerationRate().getX()+","+enemy.getAccelerationRate().getY()+")");
        logger.info("angle: "+enemy.getAngle());
        logger.info("angular velocity: "+enemy.getAngularVelocity());
        logger.info("HP: "+enemy.getHP());
        if (enemy instanceof Omenoct) {
            getOmenoctInfo(logger, (Omenoct) enemy);
        }
    }
    private static void getOmenoctInfo(Logger logger, Omenoct omenoct) {
        logger.info("is side chosen: "+omenoct.isSideChosen());
        if (omenoct.isSideChosen()) {
            logger.info("side: " + omenoct.getSide());
        }
        logger.info("is stuck: "+omenoct.isStuck());
    }
}
