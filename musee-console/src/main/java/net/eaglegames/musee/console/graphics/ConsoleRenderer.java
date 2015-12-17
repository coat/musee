package net.eaglegames.musee.console.graphics;

import net.eaglegames.musee.entity.Musee;
import net.eaglegames.musee.game.Game;
import net.eaglegames.musee.graphics.GraphicsRenderer;

public class ConsoleRenderer implements GraphicsRenderer {
    @Override
    public void render(Game game) {
        Musee.score(game.getCurrentPlayer().getMusee());
        StringBuilder out = new StringBuilder();

        out.append("Your Score: ").append(game.getCurrentPlayer().getMusee().getScore()).append("\n");
        out.append("Your Hand: ").append(game.getPlayer1().getHand()).append("\n");
        out.append("Your Musée\n").append(game.getPlayer1().getMusee()).append("\n");

        out.append("Opponent:\n").append(game.getPlayer2().getMusee());

        System.out.print(out.toString());
    }
}
