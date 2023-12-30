package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import drgmod.actions.GainMineralsAction;

import static drgmod.MinerMain.makeID;

public class DeepPocketsPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("DeepPocketsPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public DeepPocketsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new GainMineralsAction(this.owner, this.amount));
    }

    @Override
    public AbstractPower makeCopy() {
        return new DeepPocketsPower(this.owner, this.amount);
    }
}
