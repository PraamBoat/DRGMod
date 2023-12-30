package drgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import drgmod.powers.BlackoutPower;
import drgmod.powers.IntoxicationPower;
import drgmod.relics.ErrorCube;

import static java.lang.Math.max;

public class IntoxicationRemoveAction extends AbstractGameAction {
    private AbstractPlayer p;

    public IntoxicationRemoveAction(AbstractPlayer p){
        this.p = p;
        this.isDone = false;
    }

    @Override
    public void update() {
        if (!this.isDone){
            if (!p.hasPower(BlackoutPower.POWER_ID)){
                if (p.hasRelic(ErrorCube.ID) && p.hasPower(IntoxicationPower.POWER_ID)){
                    int amount = p.getPower(IntoxicationPower.POWER_ID).amount;
                    if (amount > 3){
                        addToBot(new ReducePowerAction(p, p, IntoxicationPower.POWER_ID, amount - 3));
                    }
                }
                else{
                    addToBot(new RemoveSpecificPowerAction(p, p, IntoxicationPower.POWER_ID));
                }
            }
            this.isDone = true;
        }
    }
}


