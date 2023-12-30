package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;

import static drgmod.MinerMain.makeID;

public class InsulationPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("InsulationPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private int amountToApply;

    public InsulationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        amountToApply = 0;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

//    public void atStartOfTurn() {
//        if (amountToApply > 0){
//            for (AbstractMonster m: AbstractDungeon.getMonsters().monsters){
//                addToBot(new ApplyPowerAction(m, this.owner, new BurnPower(m, amountToApply)));
//                addToBot(new ApplyPowerAction(m, this.owner, new FreezePower(m, amountToApply)));
//                addToBot(new ApplyPowerAction(m, this.owner, new ShockPower(m, amountToApply)));
//            }
//        }
//        amountToApply = 0;
//    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
            flash();
//            amountToApply++;
            addToBot(new ApplyPowerAction(info.owner, this.owner, new BurnPower(info.owner, this.amount)));
            addToBot(new ApplyPowerAction(info.owner, this.owner, new FreezePower(info.owner, this.amount)));
            addToBot(new ApplyPowerAction(info.owner, this.owner, new ShockPower(info.owner, this.amount)));
        }
        return damageAmount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new InsulationPower(this.owner, this.amount);
    }
}
