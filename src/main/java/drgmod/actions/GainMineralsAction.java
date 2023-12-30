package drgmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupMoveAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import drgmod.powers.*;
import drgmod.relics.DRGBadge;

import javax.smartcardio.Card;

public class GainMineralsAction extends AbstractGameAction {

    private AbstractCreature p;
    private int amountGained;

    public GainMineralsAction(AbstractCreature p, int amountGained){
        this.p = p;
        this.isDone = false;
        this.amountGained = amountGained;
        if (p.hasPower(DarkMorkitePower.POWER_ID)){
            this.amountGained *= 2;
        }
//        if (p.hasPower(ShieldPower.POWER_ID)){
//            this.amountGained /= 2;
//        }
        if (AbstractDungeon.player.hasRelic(DRGBadge.ID)){
            if (AbstractDungeon.player.getRelic(DRGBadge.ID).counter == 1){
                this.amountGained /= 2;
            }
        }
    }

    @Override
    public void update() {
        if (!isDone){
            addToBot(new ApplyPowerAction(this.p, this.p, new MineralPower(this.p, this.amountGained)));
//            if (p.hasPower(IronWillPower.POWER_ID)){
//                addToBot(new GainBlockAction(p, p.getPower(IronWillPower.POWER_ID).amount));
//            }
            isDone = true;
        }
    }
}


