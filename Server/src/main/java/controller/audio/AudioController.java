package controller.audio;

import controller.GameManager;
import controller.audio.Audio;

public class AudioController {
    public static void addBulletShotSound() {
        try {
            new Audio(GameManager.configs.SHOT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addXPCollectingSound() {
        try {
            new Audio(GameManager.configs.COLLECTING_SOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addWinningSound() {
        try {
            new Audio(GameManager.configs.WINNING_SOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addGameOverSound() {
        try {
            new Audio(GameManager.configs.GAME_OVER_SOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addEnemyDyingSound() {
        try {
            new Audio(GameManager.configs.ENEMY_DYING_SOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addCollisionSound() {
        try {
            new Audio(GameManager.configs.COLLISION_SOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addWaveEndSound() {
        try {
            new Audio(GameManager.configs.WAVE_END);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addEnemyEnteringSound() {
        try {
            new Audio(GameManager.configs.ENEMY_ENTERING_SOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
