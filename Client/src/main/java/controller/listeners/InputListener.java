package controller.listeners;

import controller.Controller;
import controller.game_manager.GameManager;
import model.Requests;
import model.game.skills.Skill;
import view.game.Shop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class InputListener {
    private static InputMap inputMap;
    private ActionMap actionMap;
    private JPanel panel;
    private GameManager gameManager;

    public InputListener(GameManager gameManager, JPanel panel) {
        this.panel = panel;
        this.gameManager = gameManager;
        createKeyBindings();
        createKeyActions();
    }
    private void createKeyBindings() {

        inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        actionMap = panel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "moveUpReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "moveDownReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "moveLeftReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "moveRightReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "activateSkill");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "showShop");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "showHUI");

    }

    private void createKeyActions() {
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameManager.isRunning()) {
                        gameManager.getGameModel().getMyEpsilon().moveUp(true);
                }
            }
        });
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameManager.isRunning()) {
                        gameManager.getGameModel().getMyEpsilon().moveDown(true);
                }
            }
        });
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameManager.isRunning()) {
                        gameManager.getGameModel().getMyEpsilon().moveRight(true);
                }
            }
        });
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameManager.isRunning()) {
                        gameManager.getGameModel().getMyEpsilon().moveLeft(true);
                }
            }
        });
        actionMap.put("moveUpReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameManager.isRunning()) {
                        gameManager.getGameModel().getMyEpsilon().moveUp(false);
                }
            }
        });
        actionMap.put("moveDownReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameManager.isRunning()) {
                        gameManager.getGameModel().getMyEpsilon().moveDown(false);
                }
            }
        });
        actionMap.put("moveRightReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameManager.isRunning()) {
                        gameManager.getGameModel().getMyEpsilon().moveRight(false);
                }
            }
        });
        actionMap.put("moveLeftReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameManager.isRunning()) {
                        gameManager.getGameModel().getMyEpsilon().moveLeft(false);
                }
            }
        });
        actionMap.put("activateSkill", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Skill skill = gameManager.getApplicationManager().getPickedSkill();
                if (skill != null) {
                    skill.activate(gameManager);
                }
            }
        });
        actionMap.put("showShop", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameManager.isRunning() && !gameManager.isHypnos()) {
                    gameManager.setRunning(false);
                    new Shop(gameManager);
                }
            }
        });
        actionMap.put("showHUI", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameManager.isRunning()) {
                    //gameManager.getGameView().getHui().showHUI();
                }
            }
        });
    }


}