package drgmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupMoveAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import javax.smartcardio.Card;

public class ZiplineLauncherAction extends AbstractGameAction {

    private AbstractPlayer p;
    private CardGroup tmpGroup;

    public ZiplineLauncherAction(AbstractPlayer p){
        this.p = p;
        this.isDone = false;
        tmpGroup = new CardGroup(CardGroup.CardGroupType.DISCARD_PILE);
        if (p.discardPile.isEmpty() || AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            tmpGroup.addToTop(c);
        }
        AbstractDungeon.gridSelectScreen.open(tmpGroup, 1, true, "Put a card on top of your Draw Pile.");
    }



    @Override
    public void update() {
        if (!this.isDone && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    AbstractDungeon.player.discardPile.moveToDeck(c, false);
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


