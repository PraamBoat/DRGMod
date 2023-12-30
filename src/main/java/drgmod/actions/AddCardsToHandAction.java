package drgmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupMoveAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import javax.smartcardio.Card;

public class AddCardsToHandAction extends AbstractGameAction {

    private AbstractPlayer p;
    private CardGroup tmpGroup;

    public AddCardsToHandAction(AbstractPlayer p, int cardsToAdd){
        this.p = p;
        this.isDone = false;
        tmpGroup = new CardGroup(CardGroup.CardGroupType.DRAW_PILE);
        if (p.drawPile.isEmpty() || AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            tmpGroup.addToTop(c);
        }
        String message = "Add a card to your hand.";
        if (cardsToAdd > 1){
            message = "Add " + cardsToAdd + " cards to your hand.";
        }
        AbstractDungeon.gridSelectScreen.open(tmpGroup, cardsToAdd, true, message);
    }



    @Override
    public void update() {
        if (!this.isDone && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    AbstractDungeon.player.drawPile.moveToHand(c);
                    this.tmpGroup.removeCard(c);
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.isDone = true;
            }
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            this.isDone = true;
        }
    }
}


