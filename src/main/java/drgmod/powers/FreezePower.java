package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static drgmod.MinerMain.makeID;

public class FreezePower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID("Freeze");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    private boolean exception;

    public FreezePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        exception = false;
        updateDescription();
    }

    public FreezePower(AbstractCreature owner, int amount, boolean exception) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.canGoNegative = false;
        this.exception = exception;
        updateDescription();
    }

    public void atStartOfTurn(){
        if (!exception){
            int half = amount / 2;
            if (half == 0){
                half = 1;
            }
            this.amount -= half;
            if (this.amount == 0){
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
        }
        else{
            exception = false;
        }
        updateDescription();
    }

//    public void reducePower(int reduceAmount){
//        this.amount -= reduceAmount;
//        if (this.amount <= 0){
//            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, makeID("Freeze")));
//        }
//    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage + this.amount;
        }
        return damage;
    }

    public void updateDescription() {
        if (this.owner == null || this.owner.isPlayer) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FreezePower(owner, amount);
    }
}
