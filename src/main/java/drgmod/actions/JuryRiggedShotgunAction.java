package drgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class JuryRiggedShotgunAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private int draw;
    private boolean open;
    public JuryRiggedShotgunAction(AbstractPlayer p, int draw, int discard){
        this.p = p;
        this.draw = draw;
        this.isDone = false;
        this.amount = discard;
        this.open = false;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        }
    }

    @Override
    public void update() {
        while (!isDone){
            if (this.p.hand.size() == 0 && !open){
                this.isDone = true;
                addToBot(new DrawCardAction(this.p, draw));
                return;
            }
            else if (!open){
                if (this.amount > this.p.hand.size()){
                    this.amount = this.p.hand.size();
                }
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount,false, false);
                open = true;
                return;
            }
            else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
                for (AbstractCard c: AbstractDungeon.handCardSelectScreen.selectedCards.group){
                    this.p.hand.moveToDiscardPile(c);
                    GameActionManager.incrementDiscard(false);
                }
                addToBot(new DrawCardAction(p, draw));
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                isDone = true;
            }
        }
    }
}
