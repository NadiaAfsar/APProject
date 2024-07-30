package log;

import model.game.EpsilonModel;
import org.apache.log4j.Logger;

public class EpsilonLogger {
    public static void getInfo(Logger logger, EpsilonModel epsilon) {
        logger.info("x: "+epsilon.getX());
        logger.info("y: "+epsilon.getY());
        logger.info("velocity: ("+epsilon.getVelocity().getX()+","+epsilon.getVelocity().getY()+")");
        logger.info("acceleration: ("+epsilon.getAcceleration().getX()+","+epsilon.getAcceleration().getY()+")");
        logger.info("acceleration rate: ("+epsilon.getAccelerationRate().getX()+","+epsilon.getAccelerationRate().getY()+")");
        logger.info("angle: "+epsilon.getAngle());
        logger.info("angular velocity: "+epsilon.getAngularVelocity());
        logger.info("HP: "+epsilon.getHP());
        logger.info("XP: "+epsilon.getXP());
    }
}
