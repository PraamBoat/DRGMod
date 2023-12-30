package drgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ArmskoreCoilGunAction extends AbstractGameAction {
    private AbstractCreature target;
    private DamageInfo info;

    public ArmskoreCoilGunAction(AbstractCreature target, DamageInfo info){
        this.info = info;
        this.target = target;
        this.actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        if (target != null && !isDone){
            target.damage(info);
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HORIZONTAL));
            if ((target.isDying || target.currentHealth <= 0) && !target.halfDead && !target.hasPower("Minion")) {
                AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);
                while (AbstractDungeon.getRandomMonster() == target){
                    m = AbstractDungeon.getMonsters().getRandomMonster(true);
                }
                for (AbstractPower p : target.powers){
                    if (p.type == AbstractPower.PowerType.DEBUFF){
                        addToBot(new ApplyPowerAction(m, target, target.getPower(p.ID), target.getPower(p.ID).amount));
                    }
                }
            }
            isDone = true;
        }
    }
}

