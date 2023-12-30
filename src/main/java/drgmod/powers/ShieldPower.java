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

import static drgmod.MinerMain.makeID;
import static java.lang.Math.min;

public class ShieldPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("ShieldPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    //The only thing this controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if they're a buff or debuff.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.
    public ShieldPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        super.onAttacked(info, damageAmount);
        if (this.owner.hasPower(MineralPower.POWER_ID)){
            int a = this.owner.getPower(MineralPower.POWER_ID).amount;
            flash();

//            int damageTaken;
            // calculate how much damage you can mitigate
            // damageAmount - this.amount = damage that pierces through

//            if (damageAmount >= this.amount){
//                damageTaken = damageAmount - this.amount;
//                addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, this.amount));
//            }
//            else{
//                damageTaken = this.amount - damageAmount;
//            }

            if (a >= damageAmount){
                int mineralSpend = min(damageAmount, this.amount);
//                addToBot(new MineralSpendAction(damageAmount, this.owner));
                addToBot(new MineralSpendAction(mineralSpend, this.owner));
                addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, mineralSpend));
                return 0;
            }
            else{
                int mineralSpend = min(damageAmount, this.amount);
//                addToBot(new MineralSpendAction(damageAmount, this.owner));
                addToBot(new MineralSpendAction(mineralSpend, this.owner));
                addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, mineralSpend));
                damageAmount -= mineralSpend;
                return damageAmount;
            }
        }
        return damageAmount;
    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ShieldPower(owner, amount);
    }
}
