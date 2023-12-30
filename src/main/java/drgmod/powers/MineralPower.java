package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static drgmod.MinerMain.makeID;

public class MineralPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("Minerals");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public MineralPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        if (this.amount >= 80)
            this.amount = 80;
        this.canGoNegative = false;
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount >= 80)
            this.amount = 80;
    }

    public void reducePower(int reduceAmount){
        this.amount -= reduceAmount;
    }

    public void updateDescription() {
        if (this.amount == 1){
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
        else{
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new MineralPower(owner, amount);
    }
}
