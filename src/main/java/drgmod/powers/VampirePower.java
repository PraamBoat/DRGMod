package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import drgmod.util.CustomTags;

import static drgmod.MinerMain.makeID;

public class VampirePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("VampirePower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private boolean playedPickaxe;

    public VampirePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        playedPickaxe = false;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card.hasTag(CustomTags.PICKAXE)){
            playedPickaxe = true;
        }
        else{
            playedPickaxe = false;
        }
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        if ((target.isDying || target.currentHealth <= 0) && !target.halfDead && !target.hasPower("Minion") && true) {
            addToBot(new HealAction(this.owner, this.owner, this.amount));
            playedPickaxe = false;
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new VampirePower(this.owner, this.amount);
    }
}
