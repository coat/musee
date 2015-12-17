package net.eaglegames.musee.console;

import net.eaglegames.musee.game.Game;
import net.eaglegames.musee.console.graphics.ConsoleRenderer;

public class MuseeConsole {
    public static void main(String[] args) {
        Game game = new Game(new ConsoleRenderer());

        game.getPlayer1().getMusee().getUpper().getSpaces().get(0).setPainting(game.getDeck().draw());
        game.getPlayer1().getMusee().getUpper().getSpaces().get(1).setPainting(game.getDeck().draw());
        game.getPlayer1().getMusee().getUpper().getSpaces().get(2).setPainting(game.getDeck().draw());
        game.getPlayer1().getMusee().getUpper().getSpaces().get(3).setPainting(game.getDeck().draw());
        game.getPlayer1().getMusee().getUpper().getSpaces().get(4).setPainting(game.getDeck().draw());
        game.getPlayer1().getMusee().getUpper().getSpaces().get(5).setPainting(game.getDeck().draw());

        game.getPlayer1().getMusee().getMiddle().getSpaces().get(0).setPainting(game.getDeck().draw());
        game.getPlayer1().getMusee().getMiddle().getSpaces().get(1).setPainting(game.getDeck().draw());
        game.getPlayer1().getMusee().getMiddle().getSpaces().get(2).setPainting(game.getDeck().draw());
        game.getPlayer1().getMusee().getMiddle().getSpaces().get(3).setPainting(game.getDeck().draw());
        game.getPlayer1().getMusee().getMiddle().getSpaces().get(4).setPainting(game.getDeck().draw());
        game.getPlayer1().getMusee().getMiddle().getSpaces().get(5).setPainting(game.getDeck().draw());

        game.getPlayer1().getMusee().getLower().getSpaces().get(0).setPainting(game.getDeck().draw());
        game.getPlayer1().getMusee().getLower().getSpaces().get(1).setPainting(game.getDeck().draw());
        game.getPlayer1().getMusee().getLower().getSpaces().get(2).setPainting(game.getDeck().draw());

        game.getPlayer1().getMusee().getUpper().setBonus(true);
        game.getPlayer1().getMusee().getMiddle().setBonus(true);

        game.draw();
    }
}
