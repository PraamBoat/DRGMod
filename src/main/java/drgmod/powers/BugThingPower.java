package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import drgmod.actions.GainMineralsAction;

import static drgmod.MinerMain.makeID;

public class BugThingPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("BugThingPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public BugThingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        if (this.amount == 1){
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
        else{
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }
//
//    @Override
//    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
//        super.onAttack(info, damageAmount, target);
//        addToBot(new GainMineralsAction(this.owner, this.amount));
//    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card.type == AbstractCard.CardType.ATTACK){
            addToBot(new GainMineralsAction(this.owner, this.amount));
        }
    }


    @Override
    public AbstractPower makeCopy() {
        return new BugThingPower(owner, amount);
    }
}
