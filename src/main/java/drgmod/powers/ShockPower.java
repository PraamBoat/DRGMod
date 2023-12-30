package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.DamageAllButOneEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static drgmod.MinerMain.makeID;

public class ShockPower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID("Shock");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    private boolean exception;

    public ShockPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.canGoNegative = false;
        exception = false;
        updateDescription();
    }

    public ShockPower(AbstractCreature owner, int amount, boolean exception) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.canGoNegative = false;
        this.exception = exception;
        updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount){
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            for (AbstractMonster m: (AbstractDungeon.getMonsters()).monsters){
                if (!m.isDead && !m.isDying && m != null && m != this.owner){
                    addToBot(new DamageAction(m, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS)));
                    addToBot(new VFXAction(new LightningEffect(m.drawX, m.drawY)));
                }
            }
        }
        return damageAmount;
    }

    public void atStartOfTurn(){
        if (!exception){
            int half = amount / 2;
            if (half == 0){
                half = 1;
            }
            this.amount -= half;
            if (this.amount <= 0){
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
        }
        else{
            exception = false;
        }
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ShockPower(this.owner, this.amount);
    }
}
