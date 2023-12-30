package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;
import drgmod.util.CustomTags;

import static drgmod.MinerMain.makeID;

public class StrongArmPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("StrongArmPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public StrongArmPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

//    @Override
//    public void onPlayCard(AbstractCard card, AbstractMonster m) {
//        super.onPlayCard(card, m);
//        if (card.hasTag(CustomTags.PICKAXE)){
//
//        }
//    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card.hasTag(CustomTags.PICKAXE)){
            return damage + this.amount;
        }
        return damage;
    }

    @Override
    public AbstractPower makeCopy() {
        return new StrongArmPower(this.owner, this.amount);
    }
}
