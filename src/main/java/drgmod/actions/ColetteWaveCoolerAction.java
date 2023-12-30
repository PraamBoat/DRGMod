package drgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import drgmod.cards.uncommon.attacks.ColetteWaveCooler;

public class ColetteWaveCoolerAction extends AbstractGameAction {
    private boolean isUpgraded;
    private AbstractMonster m;
    private int dmg;
    public ColetteWaveCoolerAction(boolean isUpgraded, AbstractMonster m){
        this.isUpgraded = isUpgraded;
        this.m = m;
    }

    @Override
    public void update() {
        if (m != null && !isDone){
            for (AbstractPower p: m.powers){
                if (p.type == AbstractPower.PowerType.DEBUFF){
                    dmg += p.amount;
                }
            }
            if (isUpgraded){
                dmg *= 2;
            }
            addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, dmg, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_HEAVY));
            isDone = true;
        }
    }
}
