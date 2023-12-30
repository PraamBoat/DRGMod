package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static drgmod.MinerMain.makeID;

public class DarkMorkitePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("DarkMorkitePower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public DarkMorkitePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (this.amount <= 1){
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.POWER_ID));
        }
        else{
            this.amount--;
            updateDescription();
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new DarkMorkitePower(owner, amount);
    }
}
