package drgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ReduceHandCostAction extends AbstractGameAction {
    public static final String TEXT = "Reduce";
    private AbstractPlayer p;
    private boolean open;

    public ReduceHandCostAction(AbstractPlayer p, int amount){
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.amount = amount;
        this.p = p;
        this.isDone = false;
        this.open = false;
    }

    @Override
    public void update() {
        if (!this.isDone) {
            if (!open){
                AbstractDungeon.handCardSelectScreen.open(TEXT, this.amount, false, true, false, false, true);
                open = true;
                return;
            }
            if (open && !AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    c.setCostForTurn(c.cost - 1);
                    this.p.hand.addToTop(c);
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                this.open = false;
                this.isDone = true;
            }
        }
    }
}
