package drgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import drgmod.powers.BurnPower;
import drgmod.powers.MineralPower;

public class BreachCutterAction extends AbstractGameAction {

    private DamageInfo info;
    private AbstractCreature target;

    public BreachCutterAction(AbstractCreature target, DamageInfo info){
        this.info = info;
        this.target = target;
    }

    @Override
    public void update() {
        if (target != null && !isDone){
            target.damage(info);
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.FIRE));
            if ((target.isDying || target.currentHealth <= 0) && !target.halfDead && !target.hasPower("Minion")) {
                for (AbstractMonster m: AbstractDungeon.getMonsters().monsters){
                    if (m != target){
                        addToBot(new ApplyPowerAction(m, target, new BurnPower(m, target.maxHealth/2)));
                    }
                }
            }
            isDone = true;
        }
    }
}
