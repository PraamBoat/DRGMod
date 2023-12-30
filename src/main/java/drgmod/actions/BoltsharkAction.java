package drgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class BoltsharkAction extends AbstractGameAction {
    private int energyGain;
    private DamageInfo info;
    private AbstractPlayer p;

    public BoltsharkAction(AbstractPlayer p, AbstractCreature target, DamageInfo info, int energyGain){
        this.energyGain = energyGain;
        this.info = info;
        setValues(target, info);
        this.p = p;
        this.actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        if (target != null && !isDone){
            target.damage(info);
//            addToBot(new DamageAction(target, info));
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HORIZONTAL));
            if ((target.isDying || target.currentHealth <= 0) && !target.halfDead && !target.hasPower("Minion")) {
                addToBot(new GainEnergyAction(energyGain));
//                addToBot(new DrawCardAction(this.p, 1));
            }
            isDone = true;
        }
    }
}
