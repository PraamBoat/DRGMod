package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import drgmod.actions.MineralSpendAction;
import drgmod.cards.rare.powers.IronWill;

import static drgmod.MinerMain.makeID;

public class IronWillPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("IronWillPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private int currBlock;

//    private boolean trigger;

    public IronWillPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        currBlock = this.amount;
        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        if (power.ID == IronWillPower.POWER_ID){
            currBlock = this.amount + power.amount;
        }
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card.type == AbstractCard.CardType.ATTACK){
            addToBot(new GainBlockAction(this.owner, currBlock));
            currBlock += this.amount;
        }
        else if (card.cardID != IronWill.ID){
            currBlock = this.amount;
        }
        updateDescription();
    }

    //    @Override
//    public void onDrawOrDiscard() {
//        super.onDrawOrDiscard();
//        if (trigger){
//            addToBot(new DrawCardAction(this.owner, this.amount));
//            trigger = false;
//        }
//    }
//    @Override
//    public void atStartOfTurnPostDraw() {
//        super.atStartOfTurnPostDraw();
//        trigger = true;
//    }

//    @Override
//    public void onDrawOrDiscard() {
//        super.onDrawOrDiscard();
//    }

    //    void
//    @Override
//    public void atStartOfTurn() {
//        super.atStartOfTurn();
//        trigger = true;
//    }

//    @Override
//    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
//        super.atEndOfTurnPreEndTurnCards(isPlayer);
//        trigger = false;
//    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + this.currBlock + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new IronWillPower(owner, amount);
    }
}
