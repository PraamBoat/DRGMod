package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;

import static drgmod.MinerMain.makeID;

public class SecondWindPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SecondWindPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public SecondWindPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
//        if (this.amount > 1){
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
//        }
//        else{
//            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
//        }
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        if (power.ID == IntoxicationPower.POWER_ID && target == this.owner){
            addToBot(new GainBlockAction(this.owner, this.amount));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new SecondWindPower(this.owner, this.amount);
    }
}
