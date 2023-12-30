package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import drgmod.actions.GainMineralsAction;

import static drgmod.MinerMain.makeID;

public class PotsOGoldPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("PotsOGold");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PotsOGoldPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
//        addToBot(new ApplyPowerAction(this.owner, this.owner, new MineralPower(this.owner, this.amount)));
        addToBot(new GainMineralsAction(this.owner, this.amount));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, PotsOGoldPower.POWER_ID));
    }

    @Override
    public AbstractPower makeCopy() {
        return new PotsOGoldPower(owner, amount);
    }
}
