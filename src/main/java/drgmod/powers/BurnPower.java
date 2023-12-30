package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import drgmod.relics.OmmeronHeartstone;

import static drgmod.MinerMain.makeID;

public class BurnPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("Burn");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    private boolean applyAfterburn;

    //The only thing this controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if they're a buff or debuff.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public BurnPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.canGoNegative = false;
        if (this.owner != AbstractDungeon.player){
            if (AbstractDungeon.player.hasRelic(OmmeronHeartstone.ID)){
                AbstractDungeon.player.getRelic(OmmeronHeartstone.ID).flash();
                this.amount++;
            }
        }
        applyAfterburn = false;
        updateDescription();
    }

//    public BurnPower(AbstractCreature owner, int amount, boolean exception) {
//        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
//        this.canGoNegative = false;
//        this.exception = exception;
//        updateDescription();
//    }


    public void atStartOfTurn() {
        if (this.owner != AbstractDungeon.player){
            flash();
            int half = this.amount / 2;
            addToTop(new DamageAction(owner,  new DamageInfo(owner, this.amount, DamageInfo.DamageType.THORNS), AttackEffect.FIRE));
            if (half < 1){
                half = 1;
            }
            applyAfterburn = true;
            reducePower(half);
            updateDescription();
        }
    }

    public void atEndOfTurn(boolean isPlayer){
        if (applyAfterburn){
            for (AbstractMonster m: (AbstractDungeon.getMonsters()).monsters){
                if (!m.isDead && !m.isDying && m != owner && this.amount != 0)
                    addToBot(new ApplyPowerAction(m, this.owner, new AfterburnPower(m, this.amount)));
            }
            applyAfterburn = false;
        }
        if (this.owner == AbstractDungeon.player){
            flash();
            int half = this.amount / 2;
            addToTop(new DamageAction(owner,  new DamageInfo(owner, this.amount, DamageInfo.DamageType.THORNS), AttackEffect.FIRE));
            if (half < 1){
                half = 1;
            }
            reducePower(half);
            updateDescription();
        }
    }

    public void reducePower(int reduceAmount){
        this.amount -= reduceAmount;
        if (this.amount <= 0){
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    public void updateDescription() {
        if (this.owner == null || this.owner.isPlayer) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[4];
        } else {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new BurnPower(owner, amount);
    }
}
